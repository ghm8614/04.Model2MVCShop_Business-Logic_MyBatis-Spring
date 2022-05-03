package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDAO;
import com.model2.mvc.service.purchase.PurchaseService;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	@Qualifier("purchaseDAOImpl")
	PurchaseDAO purchaseDAO;
	
	//C
	public PurchaseServiceImpl() {
	}
	
	public void setPurchaseDAO(PurchaseDAO purchaseDAO) {
		this.purchaseDAO = purchaseDAO;
	}
	
	//M
	//add
	@Override
	public void addPurchase(Purchase purchase) throws Exception {
		purchaseDAO.insertPurchase(purchase);
	}
	
	//get
	@Override
	public Purchase getPurchase(int tranNo) throws Exception{
		return purchaseDAO.findPurchase(tranNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String userId) throws Exception{
		
		// Search search, String userId
		List<Purchase> list = purchaseDAO.getPurchaseList(search,userId);
		int totalCount = purchaseDAO.getTotalCount(userId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list );
		//map.put("userId",userId);
		map.put("totalCount", new Integer(totalCount));
		
		return map;
	}

	//update
	@Override
	public void updatePurchase(Purchase purchase) throws Exception{
		purchaseDAO.updatePurchase(purchase);
	}
	
//	@Override
//	public void updateTranCode(Purchase purchase) throws Exception{
//		purchaseDAO.updateTranCode(purchase);
//	}

}
