package com.jpmorgan.util;

public class MessageParser {

	private static final String SPACE = " ";

	public static MessageType evaluateToMessageType(String inputSale) {
		if (isSaleOfTypeOne(inputSale)) {
			return MessageType.TYPE_1;
		} else if (isSaleOfTypeTwo(inputSale)) {
			return MessageType.TYPE_2;
		} else if (isSaleOfTypeThree(inputSale)) {
			return MessageType.TYPE_3;
		} else {
			return MessageType.INVALID_TYPE;
		}
	}

	private static boolean isSaleOfTypeThree(String inputSale) {
		return inputSale.startsWith("Add") || inputSale.startsWith("Substract") || inputSale.startsWith("Multiply");
	}

	private static boolean isSaleOfTypeOne(String inputSale) {
		return inputSale.split(SPACE)[1].equalsIgnoreCase("at");
	}

	private static boolean isSaleOfTypeTwo(String inputSale) {
		return inputSale.split(SPACE)[0].matches("\\d+");
	}

}

