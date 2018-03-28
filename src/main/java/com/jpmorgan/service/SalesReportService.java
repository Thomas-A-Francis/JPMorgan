package com.jpmorgan.service;

import java.util.Collection;
import java.util.List;

import com.jpmorgan.domain.SaleDetail;

/**
 * The Interface SalesReportService.
 */
public interface SalesReportService {
	
	/**
	 * Publish sales report.
	 *
	 * @param salesDetails the sales details
	 */
	public void publishSalesReport(Collection<SaleDetail> salesDetails);

	/**
	 * Publish adjustment report.
	 *
	 * @param salesAdjustments the sales adjustments
	 */
	public void publishAdjustmentReport(List<String> salesAdjustments);
}
