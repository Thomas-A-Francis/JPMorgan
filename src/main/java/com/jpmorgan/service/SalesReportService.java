package com.jpmorgan.service;

import java.util.Collection;
import java.util.List;

import com.jpmorgan.domain.SaleDetail;

public interface SalesReportService {
	
	public void publishSalesReport(Collection<SaleDetail> salesDetails);

	public void publishAdjustmentReport(List<String> salesAdjustments);
}
