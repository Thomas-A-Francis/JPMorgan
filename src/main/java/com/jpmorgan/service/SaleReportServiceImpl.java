package com.jpmorgan.service;

import java.util.Collection;
import java.util.List;

import com.jpmorgan.domain.SaleDetail;

public class SaleReportServiceImpl implements SalesReportService {

	@Override
	public void publishSalesReport(Collection<SaleDetail> salesDetails) {
		System.out.println("*********************SALES TRANSIT REPORT*****************************");
		System.out.println("****************10 MESSAGES PROCESSED*************************");
		for (SaleDetail saleDetail : salesDetails) {
			double price = saleDetail.getPrice() / 100; 
			double total = saleDetail.getTotalCost() / 100;
			System.out.printf("%d quantities of %s were sold at a base price of %.2f. Total sale is %.2f", saleDetail.getQuantity(), saleDetail.getName(), price, total);
			System.out.println("\n----------------------------------------------------------");
		}		
	}

	@Override
	public void publishAdjustmentReport(List<String> salesAdjustments) {
		System.out.println("*********************ADJUSTMENT IN SALES REPORT*****************************");
		for (String message : salesAdjustments) {
			System.out.println(message);
		}		
	}
}
