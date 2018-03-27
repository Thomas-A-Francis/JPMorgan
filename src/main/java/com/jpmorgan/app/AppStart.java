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

public class AppStart {

	public static void main(String[] args) {
		SalesProcessorService processorService = new SalesProcessorServiceImpl();
		SalesReportService salesReportService = new SaleReportServiceImpl();
		Path path = Paths.get("src/main/resources/salesDetails.txt");
		try {
			List<String> lines = Files.readAllLines(path);
			for (String line : lines) {
				processSales(processorService, salesReportService, line);
			}
		} catch (IOException | InterruptedException e) {
			System.out.println("Something went wrong in application processing -> " + e.getMessage());
		}
	}

	private static void processSales(SalesProcessorService processorService, SalesReportService salesReportService, String line) throws InterruptedException {

		if (processorService.messageCounter != 0 && processorService.messageCounter % 10 == 0) {
			salesReportService.publishSalesReport(processorService.saleDetailsMap.values());
		}

		if (processorService.messageCounter != 0 && processorService.messageCounter == 50) {

			System.out.println("            PAUSING           ");
			Thread.sleep(2000); // Pausing the application for 2 seconds
			salesReportService.publishAdjustmentReport(processorService.salesAdjustments);
			System.out.println("-- Exiting application as 50 messages processed successfully --");
			System.exit(1);
		}

		processorService.processMessage(MessageParser.evaluateToMessageType(line), line);
		processorService.messageCounter++;
	}

}
