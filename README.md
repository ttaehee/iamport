# Iamport API(๊ฒฐ์ API)
>๐ณIamport API test for seulmeal project  

1. ์์ํฌํธ ๋ผ์ด๋ธ๋ฌ๋ฆฌ ์ถ๊ฐํ๊ธฐ  
์์ํฌํธ ๋ผ์ด๋ธ๋ฌ๋ฆฌ๋ jQuery ๊ธฐ๋ฐ์ผ๋ก ๋์ํจ

```javascript
<!-- jQuery -->
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
<!-- iamport.payment.js -->
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
```

2. ๊ฐ๋งน์  ์๋ณ์ฝ๋ ์ฝ์ - mock_user_code  
let IMP = window.IMP;   //์๋ต๊ฐ๋ฅ  
IMP.init("mock_user_code");  //init function

3. ๊ฒฐ์ ์ฐฝ ํธ์ถ์ฝ๋ ์ถ๊ฐ : IMP.request_pay(param, callback)์ ํธ์ถํ๋ ์ฝ๋ ์์ฑ

- param : ๊ฒฐ์  ์์ฒญ์ ํ์ํ ์์ฑ๊ณผ ๊ฐ -> ํจ์  ํธ์ถ์ ์๋ ฅํ ์์ฑ๊ณผ ๊ฐ์ ๋ฐ๋ผ ๊ฒฐ์ ์ฐฝ ๋ณด์ฌ์ค  
- callback : ๊ณ ๊ฐ์ด ๊ฒฐ์ ์๋ฃ ํ ์คํ๋๋ ํจ์

4. callback ํจ์์์ ์ฟผ๋ฆฌ ํ๋ผ๋ฏธํฐ ์ ๋ฌ : ์ค์  ๊ฒฐ์  ์๋ฃ๋ ๊ฑด์ ๋ํด์ ๋ฐ์ดํฐ ๋๊ธฐํ๋ฅผ ํ๊ธฐ์ํด ์น์๋ฒ๋ก API์์ฒญ  
callback์์ ๊ฒฐ์ ๊ฐ ์ฑ๊ณตํ๋ฉด rsp์ imp_uid์ merchant_uid๋ฅผ ๊ฐ๋งน์  ์๋ฒ์ ์ธ์๋ก ์ ๋ฌ   
-> ๊ฐ๋งน์  ์๋ฒ์์ ํด๋น ๊ฑฐ๋์ ๊ฒฐ์ ๊ธ์ก ์๋ณ์กฐ ๊ฒ์ฆ ์ ์ฐจ ์งํ์ ํ์  
์ถ๊ฐ๋ก ์ ๋ฌํ  ๋ฐ์ดํฐ ์์ผ๋ฉด data: ๋ถ๋ถ์ ๋ฃ๊ธฐ

5. ๊ฐ๋งน์  ์๋ฒ์์ ๊ฑฐ๋๊ฒ์ฆ & ๋ฐ์ดํฐ๋๊ธฐํ(๋ฐ์ดํฐ๋ฒ ์ด์ค์ ์ ์ฅ)  
์ก์ธ์ค ํ ํฐ ๋ฐ๊ธ -> ์์ํฌํธ ์๋ฒ์์ ๊ฒฐ์ ์ ๋ณด ์กฐํ   
-> ๊ฐ๋งน์ DB์์ ๊ฒฐ์ ๋์ด์ผ ํ๋ ๊ธ์ก ์กฐํ -> ๊ฒฐ์ ๊ฒ์ฆ   
-> ์๋ณ์กฐ ํ๋จ ์ **๊ฒฐ์ ์ทจ์** ์งํ



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
          name: "๋ฐํคํธ",
          amount: data.price,
          buyer_email: data.email,
          buyer_name: data.name,
          buyer_tel: data.phone,
          buyer_addr: data.address
        }, function(rsp) {
          if(rsp.success){
            let msg = '๊ฒฐ์ ๊ฐ ์๋ฃ๋์์ต๋๋ค.';

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
                let msg = '๊ฒฐ์ ์ ์คํจํ์์ต๋๋ค.';
                 msg += '์๋ฌ๋ด์ฉ : ' + rsp.error_msg;
          }
          toastr.error(msg,"",{timeOut:2000});
      })
    }
})
```
```java
//์์ํฌํธ ์ธ์ฆ(ํ ํฐ)๋ฐ์์ฃผ๋ ํจ์
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
// ๊ฒฐ์ ์ทจ์
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
      System.err.println("ํ๋ถ์คํจ");
      return -1;
    } else {
      System.err.println("ํ๋ถ์ฑ๊ณต");
      return 1;
    }
}
```
```java
// ์์ํฌํธ ๊ฒฐ์ ์ ๋ณด์์ amount ์กฐํ
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
// Map์ ์ฌ์ฉํด์ Http์์ฒญ ํ๋ผ๋ฏธํฐ๋ฅผ ๋ง๋ค์ด ์ฃผ๋ ํจ์
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
//์์ํฌํธ ๊ฒ์ฆ
@PostMapping("verifyIamport")
public JSONObject verifyIamport(@RequestBody Purchase purchase, Point point, HttpSession session) throws Exception {

    System.out.println("/purchase/api/verifyIamport : "+purchase);

    //๊ฒฐ์ ์๋ฃ ์ ๊ตฌ๋งค์ํ ์ํ์ค๋น์ค์ผ๋ก ๋ณ๊ฒฝ
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

        //์ปค์คํฐ๋ง์ด์ง์ํ์ ์ฅ๋ฐ๊ตฌ๋๋ฆฌ์คํธ์์ ์ญ์  
        List<CustomProduct> cpList=purchase.getCustomProduct();
        for(CustomProduct cp : cpList) {
          purchaseService.updateCustomProductStatus(cp);
        }

        if(purchase.getUsePoint()!=0) {
          //์ฌ์ฉํฌ์ธํธ
          point.setUserId(user.getUserId());
          point.setPurchaseNo(purchase.getPurchaseNo());
          point.setPointStatus("0");
          point.setPoint(purchase.getUsePoint());
          userService.insertPoint(point);
          //์ดํฌ์ธํธ์์ ์ฌ์ฉํฌ์ธํธ ๋นผ๊ธฐ
          user.setTotalPoint(user.getTotalPoint()-purchase.getUsePoint());
          userService.updateUserTotalPoint(user);
        }

        json.put("purchase", purchase);
        json.put("sucess", "true");
        json.put("message", "์ฑ๊ณต!!!!!!");
      }else {
        json.put("success", "false");
        int cancel=purchaseService.cancelPayment(token, Integer.toString(purchase.getPurchaseNo()));
        if(cancel==1) {
          json.put("message", "์ฑ๊ณต!!!!!");
        }else {
          json.put("message", "์คํจ");
        }
      }
    }else {
      purchaseService.cancelPayment(token, Integer.toString(purchase.getPurchaseNo()));
      json.put("message", "์ทจ์์คํจใ ใ ");
    }
    return json;
}
```

