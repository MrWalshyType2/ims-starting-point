package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ItemTest {

	private Item item;
	private Item other;
	private Item noId;

	@Before
	public void setUp() {
		item = new Item(1L, "Freddo", 6, 3);
		other = new Item(1L, "Freddo", 6, 3);
		noId = new Item("Freddo", 6, 3);
	}

	@Test
	public void settersTest() {
		item.setId(null);
		item.setItemName(null);
		item.setValue(0);
		item.setAmount(0);
		assertNull(item.getId());
	}

	@Test
	public void gettersTest() {
		assertNotNull(item.getId());
		assertNotNull(item.getItemName());
		assertNotNull(item.getValue());
		assertNotNull(item.getAmount());
	}

	@Test
	public void constructorWithoutId() {
		Item item = new Item("Freddo", 3, 6);
		assertNull(item.getId());
		assertNotNull(item.getItemName());
		assertNotNull(item.getValue());
		assertNotNull(item.getAmount());
	}

	@Test
	public void toStringTest() {
		String toString = "ID: 1, Item Name: Freddo, Value: £6, Amount: 3";
		assertEquals(toString, item.toString());
	}

	@Test
	public void equalsTest() {
		assertTrue(item.equals(item));
		assertFalse(item.equals(noId));
	}
}
