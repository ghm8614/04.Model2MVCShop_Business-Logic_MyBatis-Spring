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
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */

// ==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class PurchaseServiceTest {

	@Autowired
	@Qualifier("purchaseServiceImpl")
	PurchaseService purchaseService;

	// @Test
	public void testAddPurchase() throws Exception {

		// 전제
		Purchase purchase = new Purchase();

		Product product = new Product();
		User user = new User();

		// users, product Table에 존재하는 prodNo, userId 를 넣어줘야 한다. 왜???
		// users, product Table에 select 안하는데.. 왜?
		// 다른 값 넣어줄 시, 아래 에러발생
		// ORA-02291: 무결성 제약조건 위배. 부모 키가 없습니다.

		// -> 상식적으로 가입한 회원이 등록된 상품을 구매하는게 맞긴해
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

		// 실행
		purchaseService.addPurchase(purchase);

		// 검증 : db확인..

	}

	//@Test
	public void testGetPurchase() throws Exception {
		// 전제
		// 위 insert한 데이터 사용
		// userId : testUserId, prodNo : 10048 => tranNo : 10028

		// 실행
		Purchase purchase = purchaseService.getPurchase(10028);

		// 검증
		Assert.assertEquals("testUserId", purchase.getBuyer().getUserId());
		Assert.assertEquals(10048, purchase.getPurchaseProd().getProdNo());

		// purchase.getDivyDate() => 2022-04-01 00:00:00
		System.out.println(purchase.getDivyDate());
		Assert.assertEquals("2022-04-01", purchase.getDivyDate().split(" ")[0]);
	}

	// @Test
	public void testUpdatePurchase() throws Exception {

		// 전제
		Purchase purchase = purchaseService.getPurchase(10028);
		purchase.setPaymentOption("1");
		purchase.setReceiverName("updateName");
		purchase.setReceiverPhone("1111");
		purchase.setDivyAddr("updateAddr");
		purchase.setDivyRequest("updateRequest");
		purchase.setDivyDate("2022-04-01");

		// 실행
		purchaseService.updatePurchase(purchase);

		// 검증
		purchase = purchaseService.getPurchase(10028);
		Assert.assertEquals("1", purchase.getPaymentOption().trim());
		Assert.assertEquals("updateName", purchase.getReceiverName());
		Assert.assertEquals("1111", purchase.getReceiverPhone());
		Assert.assertEquals("updateAddr", purchase.getDivyAddr());
		Assert.assertEquals("updateRequest", purchase.getDivyRequest());
		Assert.assertEquals("2022-04-01", purchase.getDivyDate().split(" ")[0]);
	}

	// ==> 주석을 풀고 실행하면....
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
//		// ==> console 확인 //System.out.println(list);
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
//		// ==> console 확인 //System.out.println(list);
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
//		// ==> console 확인 System.out.println(list);
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
//		// ==> console 확인 System.out.println(list);
//
//		totalCount = (Integer) map.get("totalCount");
//		System.out.println(totalCount);

	}
}