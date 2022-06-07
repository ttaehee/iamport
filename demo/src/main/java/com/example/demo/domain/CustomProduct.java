package com.example.demo.domain;

import java.sql.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class CustomProduct {
	private int customProductNo;
	private User user;
	private Product product;
	private int purchaseNo;
	private int count;
	private int price;
	private String cartStatus;
	private Date regDate;
	
	// customParts를 가지고 있다
	private List<CustomParts> minusParts; //제외재료 
	private List<CustomParts> plusParts; //추가재료 
}
