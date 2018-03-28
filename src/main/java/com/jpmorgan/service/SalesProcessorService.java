package com.jpmorgan.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jpmorgan.domain.MessageType;
import com.jpmorgan.domain.SaleDetail;

/**
 * @author Thomas Francis 
 * 
 * The Class SalesProcessorService.
 */
public abstract class SalesProcessorService {

	public int messageCounter;

	public Map<String, SaleDetail> salesDetailsMap = new HashMap<>();

	public List<String> salesAdjustments = new ArrayList<>();

	/**
	 * Process message.
	 *
	 * @param MessageType the message type
	 * @param String  the sale detail
	 */
	public abstract void processMessage(MessageType messageType, String saleDetail);

}
