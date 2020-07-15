package com.qa.ims.controller;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.services.ItemServices;
import com.qa.ims.services.OrderServices;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {
	@Mock
	private OrderServices orderServices;

	@Mock
	private ItemServices itemServices;

	@Spy
	@InjectMocks
	private OrderController orderController;

	@Test
	public void getInputTest() {
		String input = "TheOrderController";
		InputStream in = new ByteArrayInputStream(input.getBytes()); // Gets an array of bytes representing input
		System.setIn(in); // Sets the system input stream to the Byte array stream
		assertEquals("TheOrderController", orderController.getInput());
	}

	@Test
	public void readAllTest() {

	}
}
