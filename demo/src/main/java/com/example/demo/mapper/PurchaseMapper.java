package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.Purchase;

@Mapper
public interface PurchaseMapper {
	
	//구매추가
	public int insertPurchase(Purchase purchase);
	
	//구매상세 
	public Purchase getPurchase(int purchaseNo);
	
	//구매 imp_uid 추가 
	public int updatePurchase(Purchase purchase);


}

