package com.qa.ims.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.persistence.dao.Dao;
import com.qa.ims.persistence.domain.Order;

@RunWith(MockitoJUnitRunner.class)
public class OrderServicesTest {
	@Mock
	private Dao<Order> orderDao;

	@InjectMocks
	private OrderServices orderServices;

	@Test
	public void orderServicesCreateTest() {
		Order order = new Order(5L);
		orderServices.create(order);
		Mockito.verify(orderDao, Mockito.times(1)).create(order);
	}

	@Test
	public void orderServicesReadTest() {
		orderServices.readAll();
		Mockito.verify(orderDao, Mockito.times(1)).readAll();
	}

	@Test
	public void orderServicesUpdateTest() {
		Order order = new Order(3L, 5L);
		orderServices.update(order);
		Mockito.verify(orderDao, Mockito.times(1)).update(order);
	}

	@Test
	public void orderServicesDeleteTest() {
		orderServices.delete(1L);
		Mockito.verify(orderDao, Mockito.times(1)).delete(1L);
	}
}
