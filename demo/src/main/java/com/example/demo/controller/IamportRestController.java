package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.domain.Purchase;
import com.example.demo.service.IamportService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

@RestController
@RequestMapping("/purchase/*")
public class IamportRestController {

	//private IamportClient api;
	
	@Autowired
	private IamportService service;
	
	
	//public IamportRestController() {
	//	this.api=new IamportClient("3479616353644323", 
	//	"cbe3de64fe45c7f463b3635d100f3b778c1bb755d258dec8c9d4f22f77fb9e7093be3b1c11843648");
	//}
	
	/*
	@ResponseBody
	@RequestMapping(value="/verifyIamport/{imp_uid}")
	public IamportResponse<Payment> paymentByImpUid(
			Model model
			, Locale locale
			, HttpSession session
			, @PathVariable(value= "imp_uid") String imp_uid) throws IamportResponseException, IOException
	{	
			return api.paymentByImpUid(imp_uid);
	}
	*/
	
	@PostMapping("api/insertPurchase")
	public Purchase insertPurchase(Purchase purchase, Model model) {
		
		System.out.println("/insertPurchase :Post");
		service.insertPurchase(purchase);
		
		purchase=service.getPurchase(purchase.getPurchaseNo());

		return purchase;	
		
	}	
	
	@PostMapping("api/verifyIamport")
	public JSONObject verifyIamport(@RequestBody Purchase purchase) {
		
		System.out.println("====================verify : "+purchase);
		int success=service.updatePurchase(purchase);
		System.out.println("=============update : "+success);
		purchase=service.getPurchase(purchase.getPurchaseNo());
		
		String token=service.getImportToken();
		System.out.println("=====================token : "+ token);
		JSONObject json=new JSONObject();
		
		if(success ==1) {
			String portAmount=service.getAmount(token, purchase.getPurchaseNo());
			
			if(purchase.getPrice() == Integer.parseInt(portAmount)) {
				json.put("purchase", purchase);
				json.put("sucess", "true");
				json.put("message", "성공!!!!!!");
			}else {
				json.put("success", "false");
				int cancel=service.cancelPayment(token, Integer.toString(purchase.getPurchaseNo()));
				if(cancel==1) {
					json.put("message", "성공!!!!!");
				}else {
					json.put("message", "실패");
				}
			}
		}else {
			service.cancelPayment(token, Integer.toString(purchase.getPurchaseNo()));
			json.put("message", "결제실패ㅠㅠ");
		}
		return json;
	}
	

}

