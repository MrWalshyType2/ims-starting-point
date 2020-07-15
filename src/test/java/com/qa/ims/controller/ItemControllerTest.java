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
import com.qa.ims.services.ItemServices;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {
	@Mock
	private ItemServices itemService;

	@Spy
	@InjectMocks
	private ItemController itemController;

	@Test
	public void getInputTest() {
		// Using an input stream to fake input
		String input = "HelloWorld";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		assertEquals("HelloWorld", itemController.getInput());
	}

	@Test
	public void readAllTest() {
		ItemController itemController = new ItemController(itemService);
		List<Item> items = new ArrayList<>();
		items.add(new Item("Freddo", 3, 55));
		items.add(new Item("Gumdrops", 6, 102));
		items.add(new Item("Bobbos", 34, 3324));
		Mockito.when(itemService.readAll()).thenReturn(items);
		assertEquals(items, itemController.readAll());
	}

	@Test
	public void createTest() {
		String itemName = "Freddo";
		String value = "3";
		String amount = "40";
		Mockito.doReturn(itemName, value, amount).when(itemController).getInput();
		// Mockito.when(itemController.getInput()).thenReturn(itemName, value, amount);
		Item item = new Item(itemName, Integer.parseInt(value), Integer.parseInt(amount));
		Item savedItem = new Item("Freddo", 3, 40);

		Mockito.when(itemService.create(item)).thenReturn(savedItem);

		// Assert.assertEquals(expected, actual);
		assertEquals(savedItem, itemController.create());
	}

	@Test
	public void updateTest() {
		Mockito.doReturn("1", "Freddo", "50", "30").when(itemController).getInput();
		Item item = new Item(1L, "Freddo", 50, 30);
		Mockito.when(itemService.update(item)).thenReturn(item);
		assertEquals(item, itemController.update());
	}

	@Test
	public void deleteTest() {
		Mockito.doReturn("3").when(itemController).getInput();
		itemController.delete();
		Mockito.verify(itemService, Mockito.times(1)).delete(3L);
	}
}
