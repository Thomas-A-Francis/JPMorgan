package com.jpmorgan.service;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.jpmorgan.domain.MessageType;
import com.jpmorgan.domain.SaleDetail;

/**
 * The Class SaleProcessorServiceImplTest.
 */
public class SaleProcessorServiceImplTest {

	/**
	 * Test process message for type one.
	 */
	@Test
	public void testProcessMessageForTypeOne() {
		SalesProcessorService salesProcessorService = new SalesProcessorServiceImpl();
		salesProcessorService.processMessage(MessageType.TYPE_1, "banana at 10p");
		SaleDetail saleDetail = salesProcessorService.salesDetailsMap.get("bananas");
		assertThat(saleDetail.getName(), equalTo("banana"));
		assertThat(saleDetail.getPrice(), equalTo(0.1));
		assertThat(saleDetail.getQuantity(), equalTo(1));
		assertThat(saleDetail.getTotalCost(), equalTo(0.1));
	}
	
	/**
	 * Test process message for type two.
	 */
	@Test
	public void testProcessMessageForTypeTwo() {
		SalesProcessorService salesProcessorService = new SalesProcessorServiceImpl();
		salesProcessorService.processMessage(MessageType.TYPE_2, "20 sales of apples at 10p");
		SaleDetail saleDetail = salesProcessorService.salesDetailsMap.get("apples");
		assertThat(saleDetail.getName(), equalTo("apples"));
		assertThat(saleDetail.getPrice(), equalTo(0.1));
		assertThat(saleDetail.getQuantity(), equalTo(20));
		assertThat(saleDetail.getTotalCost(), equalTo(2));
	}
	
	/**
	 * Test process message for type three.
	 */
	@Test
	public void testProcessMessageForTypeThree() {
		SalesProcessorService salesProcessorService = new SalesProcessorServiceImpl();
		salesProcessorService.processMessage(MessageType.TYPE_2, "20 sales of apples at 10p");
		salesProcessorService.processMessage(MessageType.TYPE_2, "Add 10p apples");
		SaleDetail saleDetail = salesProcessorService.salesDetailsMap.get("apples");
		assertThat(saleDetail.getName(), equalTo("apples"));
		assertThat(saleDetail.getPrice(), equalTo(0.2));
		assertThat(saleDetail.getQuantity(), equalTo(20));
		assertThat(saleDetail.getTotalCost(), equalTo(2.2));
	}

}
