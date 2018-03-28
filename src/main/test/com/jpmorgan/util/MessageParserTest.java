package com.jpmorgan.util;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.jpmorgan.domain.MessageType;

/**
 * @author Thomas Francis
 * 
 * The Class MessageParserTest.
 */
class MessageParserTest {

	/**
	 * Test evaluate to message type for message type one.
	 */
	@Test
	void testEvaluateToMessageTypeForMessageTypeOne() {
		assertThat(MessageParser.evaluateToMessageType("banana at 10p"), equalTo(MessageType.TYPE_1));
	}

	/**
	 * Test evaluate to message type for message type two.
	 */
	@Test
	void testEvaluateToMessageTypeForMessageTypeTwo() {
		assertThat(MessageParser.evaluateToMessageType("20 sales of apples at 10p"), equalTo(MessageType.TYPE_2));
	}

	/**
	 * Test evaluate to message type for message type three.
	 */
	@Test
	void testEvaluateToMessageTypeForMessageTypeThree() {
		assertThat(MessageParser.evaluateToMessageType("Add 10p apples"), equalTo(MessageType.TYPE_3));
	}

	/**
	 * Test evaluate to message type for message type invalid.
	 */
	@Test
	void testEvaluateToMessageTypeForMessageTypeInvalid() {
		assertThat(MessageParser.evaluateToMessageType("This is invalid sale"), equalTo(MessageType.INVALID_TYPE));
	}
}
