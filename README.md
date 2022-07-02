# Iamport API(결제API)
>💳Iamport API test for seulmeal project  

1. 아임포트 라이브러리 추가하기  
아임포트 라이브러리는 jQuery 기반으로 동작함

```javascript
<!-- jQuery -->
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
<!-- iamport.payment.js -->
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
```

2. 가맹점 식별코드 삽입 - mock_user_code  
let IMP = window.IMP;   //생략가능  
IMP.init("mock_user_code");  //init function

3. 결제창 호출코드 추가 : IMP.request_pay(param, callback)을 호출하는 코드 작성

- param : 결제 요청에 필요한 속성과 값 -> 함수  호출시 입력한 속성과 값에 따라 결제창 보여줌  
- callback : 고객이 결제완료 후 실행되는 함수

4. callback 함수에서 쿼리 파라미터 전달 : 실제 결제 완료된 건에 대해서 데이터 동기화를 하기위해 웹서버로 API요청  
callback에서 결제가 성공하면 rsp의 imp_uid와 merchant_uid를 가맹점 서버에 인자로 전달   
-> 가맹점 서버에서 해당 거래의 결제금액 위변조 검증 절차 진행시 필요  
추가로 전달할 데이터 있으면 data: 부분에 넣기

5. 가맹점 서버에서 거래검증 & 데이터동기화(데이터베이스에 저장)  
액세스 토큰 발급 -> 아임포트 서버에서 결제정보 조회   
-> 가맹점DB에서 결제되어야 하는 금액 조회 -> 결제검증   
-> 위변조 판단 시 **결제취소** 진행



### Apply the code to the SeulMeal Project  
```javascript
$.ajax({
    url:"/purchase/api/insertPurchase",
    method:"POST",
    data:JSON.stringify({
      name : name,
      address : address,
      phone : phone,
      email : email,
      message : message,
      price : price,
      paymentCondition : paymentCondition,
      usePoint : usePoint,
      customProductNo : customNo
    }),
    headers : {
      "Accept" : "application/json",
      "Content-Type" : "application/json"
    },
    dataType : "json",
    success : function(data){
      console.log(data);
      IMP.init('imp83644059'); 
      IMP.request_pay(
          { 
          pay_method: data.paymentCondition,
          merchant_uid: data.purchaseNo,
          name: "밀키트",
          amount: data.price,
          buyer_email: data.email,
          buyer_name: data.name,
          buyer_tel: data.phone,
          buyer_addr: data.address
        }, function(rsp) {
          if(rsp.success){
            let msg = '결제가 완료되었습니다.';

              $.ajax({
                  url:"api/verifyIamport",
                  method:"POST",
                  data:JSON.stringify({
                      imp_uid : rsp.imp_uid,
                      purchaseNo : rsp.merchant_uid,
                      amount : rsp.paid_amount
               }),
               headers : {
                    "Accept" : "application/json",
                    "Content-Type" : "application/json"
                  },
                  dataType : "json",
                  success : function(data){
                    window.location.href='/purchase/getPurchase/' + data.purchase.purchaseNo;
                  }
                   })
          }else{
                let msg = '결제에 실패하였습니다.';
                 msg += '에러내용 : ' + rsp.error_msg;
          }
          toastr.error(msg,"",{timeOut:2000});
      })
    }
})
```
```java
//아임포트 인증(토큰)받아주는 함수
public String getImportToken() {
    String result = "";
    HttpClient client = HttpClientBuilder.create().build();
    HttpPost post = new HttpPost(IMPORT_TOKEN_URL);
    Map<String,String> m  =new HashMap<String,String>();
    m.put("imp_key", KEY);
    m.put("imp_secret", SECRET);
    try {
      post.setEntity(new UrlEncodedFormEntity(convertParameter(m)));
      HttpResponse res = client.execute(post);

      ObjectMapper mapper = new ObjectMapper();
      String body = EntityUtils.toString(res.getEntity());

      JsonNode rootNode = mapper.readTree(body);
      JsonNode resNode = rootNode.get("response");
      result = resNode.get("access_token").asText();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
}
```
```java
// 결제취소
public int cancelPayment(String token, String mid) {
    HttpClient client = HttpClientBuilder.create().build();
    HttpPost post = new HttpPost(IMPORT_CANCEL_URL);
    Map<String, String> map = new HashMap<String, String>();
    post.setHeader("Authorization", token);
    map.put("merchant_uid", mid);
    String asd = "";
    try {
      post.setEntity(new UrlEncodedFormEntity(convertParameter(map)));
      HttpResponse res = client.execute(post);
      ObjectMapper mapper = new ObjectMapper();
      String enty = EntityUtils.toString(res.getEntity());
      JsonNode rootNode = mapper.readTree(enty);
      asd = rootNode.get("response").asText();
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (asd.equals("null")) {
      System.err.println("환불실패");
      return -1;
    } else {
      System.err.println("환불성공");
      return 1;
    }
}
```
```java
// 아임포트 결제정보에서 amount 조회
public String getAmount(String token, String mId) {
    String amount = "";
    HttpClient client = HttpClientBuilder.create().build();
    HttpGet get = new HttpGet(IMPORT_PAYMENTINFO_URL + mId + "/paid");
    get.setHeader("Authorization", token);

    try {
      HttpResponse res = client.execute(get);
      ObjectMapper mapper = new ObjectMapper();
      String body = EntityUtils.toString(res.getEntity());
      JsonNode rootNode = mapper.readTree(body);
      JsonNode resNode = rootNode.get("response");
      amount = resNode.get("amount").asText();
      System.out.println("======================amount : "+amount);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return amount;
}
```
```java
// Map을 사용해서 Http요청 파라미터를 만들어 주는 함수
public List<NameValuePair> convertParameter(Map<String,String> paramMap){
    List<NameValuePair> paramList = new ArrayList<NameValuePair>();

    Set<Entry<String,String>> entries = paramMap.entrySet();

    for(Entry<String,String> entry : entries) {
      paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
    }
    return paramList;
}	
```
```java
//아임포트 검증
@PostMapping("verifyIamport")
public JSONObject verifyIamport(@RequestBody Purchase purchase, Point point, HttpSession session) throws Exception {

    System.out.println("/purchase/api/verifyIamport : "+purchase);

    //결제완료 시 구매상태 상품준비중으로 변경
    int success=purchaseService.updatePurchase(purchase);
    System.out.println("/purchase/api/verifyIamport update : "+success);

    purchase=purchaseService.getPurchase(purchase.getPurchaseNo());
    System.out.println("/purchase/api/verifyIamport purchaseNo : "+ purchase.getPurchaseNo());
    User user=(User)(session.getAttribute("user"));
    purchase.setUser(user);		

    String token=purchaseService.getImportToken();
    System.out.println("/purchase/api/verifyIamport token : "+ token);

    JSONObject json=new JSONObject();
    if(success ==1) {
      String portAmount=purchaseService.getAmount(token, Integer.toString(purchase.getPurchaseNo()));

      if(purchase.getPrice() == Integer.parseInt(portAmount)) {

        //커스터마이징상품은 장바구니리스트에서 삭제 
        List<CustomProduct> cpList=purchase.getCustomProduct();
        for(CustomProduct cp : cpList) {
          purchaseService.updateCustomProductStatus(cp);
        }

        if(purchase.getUsePoint()!=0) {
          //사용포인트
          point.setUserId(user.getUserId());
          point.setPurchaseNo(purchase.getPurchaseNo());
          point.setPointStatus("0");
          point.setPoint(purchase.getUsePoint());
          userService.insertPoint(point);
          //총포인트에서 사용포인트 빼기
          user.setTotalPoint(user.getTotalPoint()-purchase.getUsePoint());
          userService.updateUserTotalPoint(user);
        }

        json.put("purchase", purchase);
        json.put("sucess", "true");
        json.put("message", "성공!!!!!!");
      }else {
        json.put("success", "false");
        int cancel=purchaseService.cancelPayment(token, Integer.toString(purchase.getPurchaseNo()));
        if(cancel==1) {
          json.put("message", "성공!!!!!");
        }else {
          json.put("message", "실패");
        }
      }
    }else {
      purchaseService.cancelPayment(token, Integer.toString(purchase.getPurchaseNo()));
      json.put("message", "취소실패ㅠㅠ");
    }
    return json;
}
```

