package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class ProductServiceTest {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	// @Test
	public void testAddProduct() throws Exception {

		Product product = new Product();

		product.setProdName("TestProduct");
		product.setProdDetail("TestProduct");
		product.setManuDate("20220401");
		product.setPrice(1);
		product.setFileName("TestProduct.jpg");
		System.out.println(product);

		productService.insertProduct(product);

		product = productService.findProduct(10029);
		// System.out.println(product);

		Assert.assertEquals("TestProduct", product.getProdName());
	}

	// @Test
	public void testGetProduct() throws Exception {

		Product product = new Product();

		product = productService.findProduct(10029);

		Assert.assertEquals("TestProduct", product.getProdName());
		Assert.assertEquals("TestProduct", product.getProdDetail());
		Assert.assertEquals("20220401", product.getManuDate());
		Assert.assertEquals(1, product.getPrice());
		Assert.assertEquals("TestProduct.jpg", product.getFileName());
	}

	// @Test
	public void testUpdateProduct() throws Exception {

		Product product = new Product();

		product = productService.findProduct(10029);

		if (product.getProdName().equals("TestProduct")) {
			product.setProdName("TestProductChanege");
			product.setProdDetail("TestProductChanege");

			productService.updateProduct(product);
		}

		product = productService.findProduct(10029);
		Assert.assertEquals("TestProductChanege", product.getProdName());
		Assert.assertEquals("TestProductChanege", product.getProdDetail());

	}

	//@Test
	public void testGetProductListAll() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(5);
		Map<String, Object> map = productService.getProductList(search);

		List<Product> list = (List<Product>) map.get("list");

		// Search와 Page 정보로 db에서 가져온 data 개수
		// pageSize를 게시물 5개로 설정했으므로, db에서 5개만 가져온다.
		Assert.assertEquals(5, list.size());

		// totalCount : Search 정보로 겁색한 db에 있는 product 총 개수
		// int 타입이지만, map에 저장될때 Object 타입으로 저장되므로, 꺼낼때도 형변환 필요
		Assert.assertEquals(23, (int) map.get("totalCount"));
	}

	//@Test
	public void testGetProductListByProdNo() throws Exception {

		Search search = new Search();

		search.setCurrentPage(1);
		search.setPageSize(3);

		search.setSearchCondition("0");
		search.setSearchKeyword("10010"); // 노트북

		Map<String, Object> map = productService.getProductList(search);

		List<Product> list = (List<Product>) map.get("list");

		// Page 정보로 db에서 가져온 데이터 개수
		Assert.assertEquals(1, list.size());

		// Search 정보로 db에 저장돼있는 데이터 총 개수
		Assert.assertEquals(1, (int) map.get("totalCount"));

		// 경우 1. totalCount > list.size
		// 만약 Search 정보로 db에서 찾은 데이터의 totalCount가 10개라도
		// pageSize = 3 이라면, 즉 한 페이지에 3개의 데이터만 display 한다면
		// 3개의 list 만 db에서 가져온다?

		// 경우 2. totalCount < list.size
		// 만약 Search 정보로 db에서 찾은 데이터의 totalCount가 2개이고
		// pageSize = 3 이라면, 즉 한 페이지에 3개의 데이터만 display 한다면
		// 2개의 list 만 db에서 가져온다?

	}

	@Test
	public void testGetProductListByProdName() throws Exception {

		Search search = new Search();

		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("1");
		search.setSearchKeyword("마"); // 마우스, 마우스, 마스크, 마우스22 : 총 4개

		System.out.println(search);
		Map<String, Object> map = productService.getProductList(search);

		List<Product> list = (List<Product>) map.get("list");
		System.out.println(list);

		// db에서 실제로 가져온 데이터 개수 : 3개, 검색조건에 따른 데이터 총 개수 : 4개 
		Assert.assertEquals(3, list.size());
		Assert.assertEquals(4, (int) map.get("totalCount"));

	}

	//@Test
	public void testGetProductListByPrice() throws Exception {

		Search search = new Search();

		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("2");
		search.setSearchKeyword("1000"); // 가격 1000원인 상품 : 2개  

		Map<String, Object> map = productService.getProductList(search);

		List<Product> list = (List<Product>) map.get("list");

		Assert.assertEquals(2, list.size());
		Assert.assertEquals(2, (int) map.get("totalCount"));

	}

}
