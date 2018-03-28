package com.jpmorgan.service;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import com.jpmorgan.domain.MessageType;
import com.jpmorgan.domain.SaleDetail;

/**
 * The Class SalesProcessorServiceImpl.
 * 
 * Service is responsible to process sales messages
 */
public class SalesProcessorServiceImpl extends SalesProcessorService {

	private static final String WHITE_SPACE = " ";

	/* (non-Javadoc)
	 * @see com.jpmorgan.service.SalesProcessorService#processMessage(com.jpmorgan.util.MessageType, java.lang.String)
	 */
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

	/**
	 * Process message type one.
	 *
	 * @param String the sale detail in string format
	 */
	private void processMessageTypeOne(String line) {
		String[] productInfo = line.split(WHITE_SPACE);
		SaleDetail saleDetail = getSaleDetailInstance(productInfo[0]);
		saleDetail.setName(getProductType(productInfo[0]));
		saleDetail.setPrice(((saleDetail.getPrice() * saleDetail.getQuantity()) + (getPrice(productInfo[2]) * 1))
				/ (saleDetail.getQuantity() + 1));
		saleDetail.setTotalCost(saleDetail.getTotalCost() + getPrice(productInfo[2]));
		saleDetail.setQuantity(saleDetail.getQuantity() + 1);
		super.salesDetailsMap.put(saleDetail.getName(), saleDetail);
	}

	/**
	 * Gets the sale detail instance.
	 *
	 * @param String the key
	 * @return SaleDetail the saleDetail object
	 */
	private SaleDetail getSaleDetailInstance(String key) {
		return super.salesDetailsMap.containsKey(key) ? super.salesDetailsMap.get(key) : new SaleDetail();
	}

	/**
	 * Gets the price.
	 *
	 * @param String the value
	 * @return double price
	 */
	private double getPrice(String value) {
		return Double.valueOf(value.substring(0, value.indexOf('p')));
	}

	/**
	 * Process message type two.
	 *
	 * @param String the sale detail in string format
	 */
	private void processMessageTypeTwo(String line) {
		String[] productInfo = line.split(WHITE_SPACE);
		SaleDetail saleDetail = getSaleDetailInstance(getProductType(productInfo[3]));
		saleDetail.setPrice(((saleDetail.getPrice() * saleDetail.getQuantity())
				+ (getPrice(productInfo[5]) * parseDouble(productInfo[0])))
				/ (saleDetail.getQuantity() + parseDouble(productInfo[0])));
		saleDetail.setQuantity(saleDetail.getQuantity() + parseInt(productInfo[0]));
		saleDetail.setName(getProductType(productInfo[3]));
		saleDetail.setTotalCost(saleDetail.getTotalCost() + (parseInt(productInfo[0]) * getPrice(productInfo[5])));
		super.salesDetailsMap.put(saleDetail.getName(), saleDetail);
	}

	/**
	 * Process message type three.
	 *
	 * @param String the sale detail in string format
	 */
	private void processMessageTypeThree(String line) {
		String[] productInfo = line.split(WHITE_SPACE);
		SaleDetail saleDetail;
		switch (productInfo[0]) {
		case "Add":
			saleDetail = getSaleDetailInstance(getProductType(productInfo[2]));
			if (isNameAvailable(saleDetail)) {
				procesForAddOperation(productInfo, saleDetail);
			}
			break;
		case "Substract":
			saleDetail = getSaleDetailInstance(getProductType(productInfo[2]));
			if (isNameAvailable(saleDetail)) {
				processForSubstractOperation(productInfo, saleDetail);
			}
			break;
		case "Multiply":
			saleDetail = getSaleDetailInstance(getProductType(productInfo[2]));
			if (isNameAvailable(saleDetail)) {
				processForMultiplyOperation(productInfo, saleDetail);
			}
			break;

		default:
			break;
		}
	}

	private boolean isNameAvailable(SaleDetail saleDetail) {
		return saleDetail.getName() != null && !saleDetail.getName().isEmpty();
	}

	private void processForMultiplyOperation(String[] productInfo, SaleDetail saleDetail) {
		double oldPrice;
		saleDetail.setName(getProductType(productInfo[2]));
		saleDetail.setTotalCost(saleDetail.getTotalCost() * getPrice(productInfo[1]));
		oldPrice = saleDetail.getPrice();
		saleDetail.setPrice(saleDetail.getPrice() * getPrice(productInfo[1]));
		super.salesDetailsMap.put(saleDetail.getName(), saleDetail);
		super.salesAdjustments.add(saleDetail.getName() + " with base value " + oldPrice + " has been adjusted to "
				+ saleDetail.getPrice());
	}

	private void processForSubstractOperation(String[] productInfo, SaleDetail saleDetail) {
		double oldPrice;
		saleDetail.setName(getProductType(productInfo[2]));
		saleDetail.setTotalCost(saleDetail.getTotalCost() - (saleDetail.getQuantity() * getPrice(productInfo[1])));
		oldPrice = saleDetail.getPrice();
		saleDetail.setPrice(((saleDetail.getPrice() * saleDetail.getQuantity())
				- (saleDetail.getQuantity() * getPrice(productInfo[1]))) / saleDetail.getQuantity());
		super.salesDetailsMap.put(saleDetail.getName(), saleDetail);
		super.salesAdjustments.add(saleDetail.getName() + " with base value " + oldPrice + " has been adjusted to "
				+ saleDetail.getPrice());
	}

	private void procesForAddOperation(String[] productInfo, SaleDetail saleDetail) {
		double oldPrice;
		saleDetail.setName(getProductType(productInfo[2]));
		saleDetail.setTotalCost(saleDetail.getTotalCost() + (saleDetail.getQuantity() * getPrice(productInfo[1])));
		oldPrice = saleDetail.getPrice();
		saleDetail.setPrice(((saleDetail.getPrice() * saleDetail.getQuantity())
				+ (saleDetail.getQuantity() * getPrice(productInfo[1]))) / saleDetail.getQuantity());
		super.salesDetailsMap.put(saleDetail.getName(), saleDetail);
		super.salesAdjustments.add(saleDetail.getName() + " with base value " + oldPrice + " has been adjusted to "
				+ saleDetail.getPrice());
	}

	/**
	 * Method to generalize the key for map.
	 *
	 * @param String the type
	 * @return String generalized product type
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
