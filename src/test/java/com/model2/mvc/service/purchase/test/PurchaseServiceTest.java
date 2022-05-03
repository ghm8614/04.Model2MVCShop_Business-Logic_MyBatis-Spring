package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

/*
 *	FileName :  UserServiceTest.java
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */

// ==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class PurchaseServiceTest {

	@Autowired
	@Qualifier("purchaseServiceImpl")
	PurchaseService purchaseService;

	// @Test
	public void testAddPurchase() throws Exception {

		// ����
		Purchase purchase = new Purchase();

		Product product = new Product();
		User user = new User();

		// users, product Table�� �����ϴ� prodNo, userId �� �־���� �Ѵ�. ��???
		// users, product Table�� select ���ϴµ�.. ��?
		// �ٸ� �� �־��� ��, �Ʒ� �����߻�
		// ORA-02291: ���Ἲ �������� ����. �θ� Ű�� �����ϴ�.

		// -> ��������� ������ ȸ���� ��ϵ� ��ǰ�� �����ϴ°� �±���
		product.setProdNo(10048);
		user.setUserId("testUserId");

		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);

		purchase.setPaymentOption("2");
		purchase.setReceiverName("testName");
		purchase.setReceiverPhone("0000");
		purchase.setDivyAddr("testAddr");
		purchase.setDivyRequest("testRequest");
		purchase.setTranCode("1");
		purchase.setDivyDate("2022-03-30");

		System.out.println("purchase : " + purchase);
		System.out.println(purchase.getPurchaseProd().getProdNo());
		System.out.println(purchase.getBuyer().getUserId());

		// ����
		purchaseService.addPurchase(purchase);

		// ���� : dbȮ��..

	}

	//@Test
	public void testGetPurchase() throws Exception {
		// ����
		// �� insert�� ������ ���
		// userId : testUserId, prodNo : 10048 => tranNo : 10028

		// ����
		Purchase purchase = purchaseService.getPurchase(10028);

		// ����
		Assert.assertEquals("testUserId", purchase.getBuyer().getUserId());
		Assert.assertEquals(10048, purchase.getPurchaseProd().getProdNo());

		// purchase.getDivyDate() => 2022-04-01 00:00:00
		System.out.println(purchase.getDivyDate());
		Assert.assertEquals("2022-04-01", purchase.getDivyDate().split(" ")[0]);
	}

	// @Test
	public void testUpdatePurchase() throws Exception {

		// ����
		Purchase purchase = purchaseService.getPurchase(10028);
		purchase.setPaymentOption("1");
		purchase.setReceiverName("updateName");
		purchase.setReceiverPhone("1111");
		purchase.setDivyAddr("updateAddr");
		purchase.setDivyRequest("updateRequest");
		purchase.setDivyDate("2022-04-01");

		// ����
		purchaseService.updatePurchase(purchase);

		// ����
		purchase = purchaseService.getPurchase(10028);
		Assert.assertEquals("1", purchase.getPaymentOption().trim());
		Assert.assertEquals("updateName", purchase.getReceiverName());
		Assert.assertEquals("1111", purchase.getReceiverPhone());
		Assert.assertEquals("updateAddr", purchase.getDivyAddr());
		Assert.assertEquals("updateRequest", purchase.getDivyRequest());
		Assert.assertEquals("2022-04-01", purchase.getDivyDate().split(" ")[0]);
	}

	// ==> �ּ��� Ǯ�� �����ϸ�....
	//@Test
	public void testGetPurchaseListAll() throws Exception {
		
		
		
		
//		Search search = new Search();
//		search.setCurrentPage(1);
//		search.setPageSize(3);
//		Map<String, Object> map = userService.getUserList(search);
//
//		List<Object> list = (List<Object>) map.get("list");
//		Assert.assertEquals(3, list.size());
//
//		// ==> console Ȯ�� //System.out.println(list);
//
//		Integer totalCount = (Integer) map.get("totalCount");
//		System.out.println(totalCount);
//
//		System.out.println("=======================================");
//
//		search.setCurrentPage(1);
//		search.setPageSize(3);
//		search.setSearchCondition("0");
//		search.setSearchKeyword("");
//		map = userService.getUserList(search);
//
//		list = (List<Object>) map.get("list");
//		Assert.assertEquals(3, list.size());
//
//		// ==> console Ȯ�� //System.out.println(list);
//
//		totalCount = (Integer) map.get("totalCount");
//		System.out.println(totalCount);
	}



	@Test
	public void testGetPurchaseListByName() throws Exception {

		
		Search search = new Search();
		
		search.setCurrentPage(1);
		search.setPageSize(2);
		
		Map<String, Object> map = purchaseService.getPurchaseList(search, "user14");
		Assert.assertEquals(5, map.get("totalCount"));
		
		
		
//		List<Object> list = (List<Object>) map.get("list");
//		Assert.assertEquals(3, list.size());
//
//		// ==> console Ȯ�� System.out.println(list);
//
//		Integer totalCount = (Integer) map.get("totalCount");
//		System.out.println(totalCount);
//
//		System.out.println("=======================================");
//
//		search.setSearchCondition("1");
//		search.setSearchKeyword("" + System.currentTimeMillis());
//		map = userService.getUserList(search);
//
//		list = (List<Object>) map.get("list");
//		Assert.assertEquals(0, list.size());
//
//		// ==> console Ȯ�� System.out.println(list);
//
//		totalCount = (Integer) map.get("totalCount");
//		System.out.println(totalCount);

	}
}