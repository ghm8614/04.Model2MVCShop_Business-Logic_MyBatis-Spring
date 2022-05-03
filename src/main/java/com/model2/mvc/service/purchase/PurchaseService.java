package com.model2.mvc.service.purchase;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseService {
	
	// add
	public void addPurchase(Purchase purchase) throws Exception; 
	
	// get
	public Purchase getPurchase(int tranNo) throws Exception;
	
	// 구매목록 
	public Map<String, Object> getPurchaseList(Search search, String userId) throws Exception;
	
	// update
	public void updatePurchase(Purchase purchase) throws Exception;
	
//	public void updateTranCode(Purchase purchase) throws Exception;
}
