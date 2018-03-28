package com.jpmorgan.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.jpmorgan.service.SalesReportService;
import com.jpmorgan.service.SaleReportServiceImpl;
import com.jpmorgan.service.SalesProcessorService;
import com.jpmorgan.service.SalesProcessorServiceImpl;
import com.jpmorgan.util.MessageParser;

/**
 * The Class SalesProcessingApp.
 * 
 * This is entry point to SalesProcessing Application
 */
public class SalesProcessingApp {

	/**
	 * The main method.
	 *
	 * @param String[] the string array as arguments
	 */
	public static void main(String[] args) {
		SalesProcessorService processorService = new SalesProcessorServiceImpl();
		SalesReportService salesReportService = new SaleReportServiceImpl();
		Path path = Paths.get("src/main/resources/salesDetails.txt");
		try {
			List<String> sales = Files.readAllLines(path);
			for (String sale : sales) {
				processSales(processorService, salesReportService, sale);
			}
		} catch (IOException | InterruptedException e) {
			System.out.println("Something went wrong in application processing -> " + e.getMessage());
		}
	}

	/**
	 * Process sales.
	 *
	 * @param SalesProcessorService the processor service
	 * @param SalesReportService the sales report service
	 * @param String the sale
	 * @throws InterruptedException the interrupted exception
	 */
	private static void processSales(SalesProcessorService processorService, SalesReportService salesReportService, String sale) throws InterruptedException {

		if (processorService.messageCounter != 0 && processorService.messageCounter % 10 == 0) {
			salesReportService.publishSalesReport(processorService.salesDetailsMap.values());
		}

		if (processorService.messageCounter != 0 && processorService.messageCounter == 50) {
			System.out.println("            PAUSING           ");
			Thread.sleep(2000); // Let us pause application 2 seconds
			salesReportService.publishAdjustmentReport(processorService.salesAdjustments);
			System.out.println("-- Exiting application as 50 messages processed successfully --");
			System.exit(1);
		}

		processorService.processMessage(MessageParser.evaluateToMessageType(sale), sale);
		processorService.messageCounter++;
	}

}
