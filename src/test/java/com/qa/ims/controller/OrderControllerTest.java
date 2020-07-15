package com.qa.ims.controller;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
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
		OrderController orderController = new OrderController(orderServices, itemServices);
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(4));
		orders.add(new Order(6));
		orders.add(new Order(53));
		Mockito.when(orderServices.readAll()).thenReturn(orders);
		assertEquals(orders, orderController.readAll());
	}

	@Test
	public void createTest() {
		Mockito.doReturn("3", "3", "30", "n").when(orderController).getInput();
//		Long orderId = Long.valueOf(3);
//		Long itemId = Long.valueOf(3);
//		int quantity = 30;
		Order order = new Order(3);

		Mockito.when(orderServices.create(order)).thenReturn(order);

		List<Item> items = new ArrayList<>();
		items.add(new Item(3L, "Freddo", 3, 30));
		Item item = items.get(0);
		Mockito.when(itemServices.readAll()).thenReturn(items);

		order.setItem(item);
		order.setItemQuantity(3);

		Mockito.when(orderServices.update(order)).thenReturn(order);
		assertEquals(order, orderController.create()); // this causes an infinite
		// loop?
	}

	@Test
	public void updateTest() {
		Mockito.doReturn("1", "n").when(orderController).getInput();

		List<Order> orders = new ArrayList<>();
		orders.add(new Order(3));
		orders.add(new Order(2));

		Order order = new Order(1);
		Order o = new Order(1);
		Mockito.when(orderServices.readAll()).thenReturn(orders);
		// Mockito.when(o.getId()).thenReturn(1L);

		List<Item> items = new ArrayList<>();
		items.add(new Item(1L, "Freddo", 3, 5));
		Mockito.when(itemServices.readAll()).thenReturn(items);
	}

	@Test
	public void deleteTest() {
		Mockito.doReturn("1").when(orderController).getInput();
		orderController.delete();
		Mockito.verify(orderServices, Mockito.times(1)).delete(1L);
	}
}
