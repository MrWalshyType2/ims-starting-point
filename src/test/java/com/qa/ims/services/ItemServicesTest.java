package com.qa.ims.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.persistence.dao.Dao;
import com.qa.ims.persistence.domain.Item;

@RunWith(MockitoJUnitRunner.class)
public class ItemServicesTest {

	@Mock
	private Dao<Item> itemDao;

	@InjectMocks
	private ItemServices itemServices;

	@Test
	public void itemServicesCreateTest() {
		Item item = new Item("Freddo", 3, 50);
		itemServices.create(item);
		Mockito.verify(itemDao, Mockito.times(1)).create(item);
	}

	@Test
	public void itemServicesReadTest() {
		itemServices.readAll();
		Mockito.verify(itemDao, Mockito.times(1)).readAll();
	}

	@Test
	public void itemServicesUpdateTest() {
		Item item = new Item(1L, "Freddo", 3, 50);
		itemServices.update(item);
		Mockito.verify(itemDao, Mockito.times(1)).update(item);
	}

	@Test
	public void itemServicesDeleteTest() {
		itemServices.delete(1L);
		Mockito.verify(itemDao, Mockito.times(1)).delete(1L);
	}
}
