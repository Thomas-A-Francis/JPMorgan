package com.jpmorgan.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jpmorgan.domain.SaleDetail;
import com.jpmorgan.util.MessageType;

public abstract class SalesProcessorService {

	public int messageCounter;

	public Map<String, SaleDetail> saleDetailsMap = new HashMap<>();

	public List<String> salesAdjustments = new ArrayList<>();
	
	public abstract void processMessage(MessageType messageType, String line);
	
}
