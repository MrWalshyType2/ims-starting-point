package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class OrderTest {
	private Order order;
	private Order other;
	private Order noId;
	private Order orderWithItems;
	private Order orderWithCost;

	@Before
	public void setUp() {
		order = new Order(1L, 3L);
		other = new Order(1L, 3L);
		noId = new Order(5L);

		ArrayList<Item> items = new ArrayList<>();
		items.add(new Item("Freddo", 3, 3));
		orderWithItems = new Order(3L, 3L, items);
		orderWithCost = new Order(3L, 3L, items, 37);
	}

	@Test
	public void settersGettersTest() {
		order.setFkCustomerId(null);
		order.setId(null);
		order.setItem(null);
		order.setItemQuantity(0);
		order.setItemsInOrder(null);
		order.setName(null);
		order.setTotalCost(0);
		order.setUpdate(false);
		order.setUpdateMode(false);

		assertNull(order.getFkCustomerId());
		assertNull(order.getId());
		assertNull(order.getItem());
		assertNull(order.getItemsInOrder());
		assertNull(order.getName());
		assertEquals(0, order.getItemQuantity());
		assertEquals(0, order.getTotalCost());
		assertFalse(order.isUpdate());
		assertFalse(order.isUpdateMode());
	}

	@Test
	public void toStringTest() {
		String toString = "Order [id=1, fkCustomerId=3, Cost=0]";
		assertEquals(toString, order.toString());
	}

	@Test
	public void hashCodeTest() {
		assertEquals(order.hashCode(), other.hashCode());
	}

	@Test
	public void equalsTest() {
		assertTrue(order.equals(other));
		assertTrue(order.equals(order));
		assertFalse(order.equals(orderWithCost));
		assertFalse(order.equals(noId));
		assertFalse(order.equals(null));
		assertFalse(order.equals(noId.getClass()));
	}
}
