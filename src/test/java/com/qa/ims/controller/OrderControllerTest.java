package com.qa.ims.controller;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.Ims;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.services.ItemServices;
import com.qa.ims.services.OrderServices;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {
	private static String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/ims_test1?serverTimezone=UTC";
	private static String username = "root";
	private static String password = "root";

	public static final Logger LOGGER = Logger.getLogger(OrderControllerTest.class);

	@BeforeClass
	public static void init() {
		Ims ims = new Ims();
		ims.init(jdbcUrl, username, password, "src/test/resources/sql-schema.sql");
		// customerDao = new
		// CustomerDaoMysql("jdbc:mysql://127.0.0.1:3306/ims_test2?serverTimezone=UTC",
		// "root", "root");
	}

	@Before
	public void setUp() {
		try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
				Statement statement = connection.createStatement();
				Statement statement2 = connection.createStatement();
				Statement statement3 = connection.createStatement();
				Statement statement4 = connection.createStatement();) {
			statement.executeUpdate("delete from orders");
			statement2.executeUpdate("delete from order_items");
			statement3.executeUpdate("delete from customers");
			statement4.executeUpdate("delete from items");
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
	}

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
		orders.add(new Order(4L));
		orders.add(new Order(6L));
		orders.add(new Order(53L));
		Mockito.when(orderServices.readAll()).thenReturn(orders);
		assertEquals(orders, orderController.readAll());
	}

	@Test
	@Ignore
	public void createTest() {
		Mockito.doReturn("3", "3", "30", "y").when(orderController).getInput();
		Order order = new Order(3L);

		Mockito.when(orderServices.create(order)).thenReturn(order);

		List<Item> items = new ArrayList<>();
		items.add(new Item(3L, "Freddo", 3, 30));
		Item item = items.get(0);
		Mockito.when(itemServices.readAll()).thenReturn(items);
		Order newOrder = new Order(3L);
		newOrder.setItem(item);
		newOrder.setItemQuantity(3);

		Mockito.when(orderServices.update(order)).thenReturn(newOrder);
		assertEquals(newOrder, orderController.create());
	}

	@Test
	public void updateTest() {
		// OrderController oc = new OrderController(new OrderServices(new
		// OrderDaoMysql(jdbcUrl, username, password)),
		// new ItemServices(new ItemDaoMysql(jdbcUrl, username, password)));
		// Mockito.when(oc.getInput()).thenReturn("1");
	}

	@Test
	@Ignore
	public void deleteTest() {
		Mockito.doReturn("1").when(orderController).getInput();
		orderController.delete();
		Mockito.verify(orderServices, Mockito.times(1)).delete(1L);
	}
}
