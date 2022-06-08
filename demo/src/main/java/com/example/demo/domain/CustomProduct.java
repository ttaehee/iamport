package com.example.demo.domain;

import java.sql.Date;
import java.util.List;

public class CustomProduct {
	private int customProductNo;
	private User user;
	private int purchaseNo;
	private int count;
	private int price;
	private String cartStatus;
	public int getCustomProductNo() {
		return customProductNo;
	}
	public void setCustomProductNo(int customProductNo) {
		this.customProductNo = customProductNo;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getPurchaseNo() {
		return purchaseNo;
	}
	public void setPurchaseNo(int purchaseNo) {
		this.purchaseNo = purchaseNo;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getCartStatus() {
		return cartStatus;
	}
	public void setCartStatus(String cartStatus) {
		this.cartStatus = cartStatus;
	}
	
	
}
