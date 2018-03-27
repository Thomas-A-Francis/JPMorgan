package com.jpmorgan.service;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import com.jpmorgan.domain.SaleDetail;
import com.jpmorgan.util.MessageType;

public class SalesProcessorServiceImpl extends SalesProcessorService {

	private static final String WHITE_SPACE = " ";

	@Override
	public void processMessage(MessageType messageType, String line) {
		if (messageType == MessageType.TYPE_1) {
			processMessageTypeOne(line);
		} else if (messageType == MessageType.TYPE_2) {
			processMessageTypeTwo(line);
		} else if (messageType == MessageType.TYPE_3) {
			processMessageTypeThree(line);
		}
	}

	private void processMessageTypeOne(String line) {
		String[] productInfo = line.split(WHITE_SPACE);
		SaleDetail saleDetail = getSaleDetailInstance(productInfo[0]);
		saleDetail.setName(getProductType(productInfo[0]));
		saleDetail.setPrice(((saleDetail.getPrice() * saleDetail.getQuantity()) + (getPrice(productInfo[2]) * 1))
				/ (saleDetail.getQuantity() + 1));
		saleDetail.setTotalCost(saleDetail.getTotalCost() + getPrice(productInfo[2]));
		saleDetail.setQuantity(saleDetail.getQuantity() + 1);
		// super.messageCounter++;
		super.saleDetailsMap.put(saleDetail.getName(), saleDetail);
	}

	private SaleDetail getSaleDetailInstance(String key) {
		return super.saleDetailsMap.containsKey(key) ? super.saleDetailsMap.get(key) : new SaleDetail();
	}

	private double getPrice(String value) {
		return Double.valueOf(value.substring(0, value.indexOf('p')));
	}

	private void processMessageTypeTwo(String line) {
		String[] productInfo = line.split(WHITE_SPACE);
		SaleDetail saleDetail = getSaleDetailInstance(getProductType(productInfo[3]));
		saleDetail.setPrice(((saleDetail.getPrice() * saleDetail.getQuantity())
				+ (getPrice(productInfo[5]) * parseDouble(productInfo[0])))
				/ (saleDetail.getQuantity() + parseDouble(productInfo[0])));
		saleDetail.setQuantity(saleDetail.getQuantity() + parseInt(productInfo[0]));
		saleDetail.setName(getProductType(productInfo[3]));
		saleDetail.setTotalCost(saleDetail.getTotalCost() + (parseInt(productInfo[0]) * getPrice(productInfo[5])));
		// super.messageCounter++;
		super.saleDetailsMap.put(saleDetail.getName(), saleDetail);
	}

	private void processMessageTypeThree(String line) {
		String[] productInfo = line.split(WHITE_SPACE);
		SaleDetail saleDetail;
		double oldPrice;
		switch (productInfo[0]) {
		case "Add":
			saleDetail = getSaleDetailInstance(getProductType(productInfo[2]));
			saleDetail.setName(getProductType(productInfo[2]));
			saleDetail.setTotalCost(saleDetail.getTotalCost() + (saleDetail.getQuantity() * getPrice(productInfo[1])));
			oldPrice = saleDetail.getPrice();
			saleDetail.setPrice(((saleDetail.getPrice() * saleDetail.getQuantity())
					+ (saleDetail.getQuantity() * getPrice(productInfo[1]))) / saleDetail.getQuantity());
			// super.messageCounter++;
			super.saleDetailsMap.put(saleDetail.getName(), saleDetail);
			super.salesAdjustments.add(saleDetail.getName() + " with base value " + oldPrice + " has been adjusted to "
					+ saleDetail.getPrice());
			break;
		case "Substract":
			saleDetail = getSaleDetailInstance(getProductType(productInfo[2]));
			saleDetail.setName(getProductType(productInfo[2]));
			saleDetail.setTotalCost(saleDetail.getTotalCost() - (saleDetail.getQuantity() * getPrice(productInfo[1])));
			oldPrice = saleDetail.getPrice();
			saleDetail.setPrice(((saleDetail.getPrice() * saleDetail.getQuantity())
					- (saleDetail.getQuantity() * getPrice(productInfo[1]))) / saleDetail.getQuantity());
			// super.messageCounter++;
			super.saleDetailsMap.put(saleDetail.getName(), saleDetail);
			super.salesAdjustments.add(saleDetail.getName() + " with base value " + oldPrice + " has been adjusted to "
					+ saleDetail.getPrice());
			break;
		case "Multiply":
			saleDetail = getSaleDetailInstance(getProductType(productInfo[2]));
			saleDetail.setName(getProductType(productInfo[2]));
			saleDetail.setTotalCost(saleDetail.getTotalCost() * getPrice(productInfo[1]));
			oldPrice = saleDetail.getPrice();
			saleDetail.setPrice(saleDetail.getPrice() * getPrice(productInfo[1]));
			// super.messageCounter++;
			super.saleDetailsMap.put(saleDetail.getName(), saleDetail);
			super.salesAdjustments.add(saleDetail.getName() + " with base value " + oldPrice + " has been adjusted to "
					+ saleDetail.getPrice());
			break;

		default:
			break;
		}
	}

	/**
	 * Method to generalize the key for map
	 * 
	 * @param product
	 *            type
	 * @return product type
	 */
	private String getProductType(String type) {
		String productType = "Invalid_Product";
		if (type.contains("apple")) {
			productType = "apples";
		} else if (type.contains("banana")) {
			productType = "bananas";
		} else if (type.contains("pineapple")) {
			productType = "pineapples";
		} else if (type.contains("avocado")) {
			productType = "avocados";
		}
		return productType;
	}
}
