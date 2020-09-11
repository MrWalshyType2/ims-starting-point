package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.qa.ims.Ims;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;

public class OrderDaoTest {
	private static String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/ims_test1?serverTimezone=UTC";
	private static String username = "root";
	private static String password = "root";

	public static final Logger LOGGER = Logger.getLogger(CustomerDaoTest.class);

	@BeforeClass
	public static void init() {
		Ims ims = new Ims();
		ims.init("src/test/resources/sql-schema.sql");
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

	@Test
	public void constructorTest() {
		OrderDaoMysql noParam = new OrderDaoMysql();
		assertNotNull(noParam);
	}

	@Test
	public void createTest() {
		OrderDaoMysql orderDao = new OrderDaoMysql();
		CustomerDaoMysql customerDao = new CustomerDaoMysql();
		Customer customer = new Customer("Bob", "Perry");
		customer = customerDao.create(customer);
		Long fkCustomerId = customer.getId();
		Order order = new Order(fkCustomerId);
		Order savedOrder = orderDao.create(order);
		order.setId(savedOrder.getId());
		assertEquals(savedOrder.getFkCustomerId(), order.getFkCustomerId());
	}

	@Test
	public void createFailTest() {
		OrderDaoMysql orderDao = new OrderDaoMysql();
		CustomerDaoMysql customerDao = new CustomerDaoMysql();
		Customer customer = new Customer("Bob", "Perry");
		customer = customerDao.create(customer);
		Long fkCustomerId = customer.getId();
		Order order = null;
		assertNull(orderDao.create(order));
	}

	@Test
	public void readAllTest() {
		OrderDaoMysql orderDao = new OrderDaoMysql();
		CustomerDaoMysql customerDao = new CustomerDaoMysql();
		ArrayList<Order> orders = new ArrayList<>();
		ArrayList<Order> savedOrders = new ArrayList<>();
		Customer c1 = new Customer("Bob", "Perry");
		c1 = customerDao.create(c1);
		Long fkCustomerId = c1.getId();
		Order o1 = new Order(fkCustomerId);
		Order o2 = new Order(fkCustomerId);
		orders.add(o1);
		orders.add(o2);
		for (Order o : orders) {
			savedOrders.add(orderDao.create(o));
		}
		assertEquals(savedOrders.toString(), orderDao.readAll().toString());
	}

	@Test
	public void readOrderTest() {
		OrderDaoMysql orderDao = new OrderDaoMysql();
		CustomerDaoMysql customerDao = new CustomerDaoMysql();
		ItemDaoMysql itemDao = new ItemDaoMysql();
		Customer c1 = new Customer("Bob", "Perry");
		c1 = customerDao.create(c1);
		Long fkCustomerId = c1.getId();
		Order o1 = new Order(fkCustomerId);
		o1 = orderDao.create(o1);
		o1.setUpdateMode(false);
		Item item = new Item("Freddo", 3, 30);
		item = itemDao.create(item);
		o1.setItem(item);
		o1.setItemQuantity(5);
		Order o2 = orderDao.update(o1); // .readOrder(o1.getId());
		// Order o2 = orderDao.readOrder(o1.getId());
		assertEquals(o2.getTotalCost(), orderDao.readOrder(o2.getId()).getTotalCost());
	}

	@Test
	public void readOrderFailTest() {
		OrderDaoMysql orderDao = new OrderDaoMysql();
		CustomerDaoMysql customerDao = new CustomerDaoMysql();
		ItemDaoMysql itemDao = new ItemDaoMysql();
		Customer c1 = new Customer("Bob", "Perry");
		c1 = customerDao.create(c1);
		Long fkCustomerId = c1.getId();
		Order o1 = new Order(fkCustomerId);
		o1 = orderDao.create(o1);
		o1.setUpdateMode(false);
		Item item = new Item("Freddo", 3, 30);
		item = itemDao.create(item);
		o1.setItem(item);
		o1.setItemQuantity(5);
		Order o2 = orderDao.update(o1); // .readOrder(o1.getId());
		// Order o2 = orderDao.readOrder(o1.getId());
		assertNull(orderDao.readOrder(null));
	}

	@Test
	public void updateTest() {
		OrderDaoMysql orderDao = new OrderDaoMysql();
		CustomerDaoMysql customerDao = new CustomerDaoMysql();
		ItemDaoMysql itemDao = new ItemDaoMysql();
		Customer c1 = new Customer("Bob", "Perry");
		c1 = customerDao.create(c1);
		Long fkCustomerId = c1.getId();
		Order o1 = new Order(fkCustomerId);
		o1 = orderDao.create(o1);
		o1.setUpdateMode(false);
		Item item = new Item("Freddo", 3, 30);
		item = itemDao.create(item);
		o1.setItem(item);
		o1.setItemQuantity(5);
		Order o2 = orderDao.update(o1); // .readOrder(o1.getId());
		assertEquals(o2.getFkCustomerId(), orderDao.readOrder(o2.getId()).getFkCustomerId());
	}

	@Test
	public void updateFailTest() {
		OrderDaoMysql orderDao = new OrderDaoMysql();
		CustomerDaoMysql customerDao = new CustomerDaoMysql();
		ItemDaoMysql itemDao = new ItemDaoMysql();
		Customer c1 = new Customer("Bob", "Perry");
		c1 = customerDao.create(c1);
		Long fkCustomerId = c1.getId();
		Order o1 = new Order(fkCustomerId);
		o1 = orderDao.create(o1);
		o1.setUpdateMode(false);
		Item item = new Item("Freddo", 3, 30);
		item = itemDao.create(item);
//		o1.setItem(item);
//		o1.setItemQuantity(5);
		Order o2 = orderDao.update(o1); // .readOrder(o1.getId());
		assertNull(orderDao.readOrder(fkCustomerId));
	}

	@Test
	public void updateDeleteTest() {
		OrderDaoMysql orderDao = new OrderDaoMysql();
		CustomerDaoMysql customerDao = new CustomerDaoMysql();
		ItemDaoMysql itemDao = new ItemDaoMysql();
		Customer c1 = new Customer("Bob", "Perry");
		c1 = customerDao.create(c1);
		Long fkCustomerId = c1.getId();
		Order o1 = new Order(fkCustomerId);
		o1 = orderDao.create(o1);
		o1.setUpdateMode(true);
		o1.setUpdate(false);
		Item item = new Item("Freddo", 3, 30);
		item = itemDao.create(item);
		o1.setItem(item);
		o1.setItemQuantity(5);
		Order o2 = orderDao.update(o1); // .readOrder(o1.getId());
		assertNull(orderDao.readOrder(fkCustomerId));
	}

	@Test
	public void deleteTest() {
		OrderDaoMysql orderDao = new OrderDaoMysql();
		CustomerDaoMysql customerDao = new CustomerDaoMysql();
		ItemDaoMysql itemDao = new ItemDaoMysql();
		Customer c1 = new Customer("Bob", "Perry");
		c1 = customerDao.create(c1);
		Long fkCustomerId = c1.getId();
		Order o1 = new Order(fkCustomerId);
		o1 = orderDao.create(o1);
		orderDao.delete(o1.getId());
		assertEquals(new ArrayList<>(), orderDao.readAll());
	}

	@Test
	public void deleteFailTest() {
		OrderDaoMysql orderDao = new OrderDaoMysql();
		CustomerDaoMysql customerDao = new CustomerDaoMysql();
		ItemDaoMysql itemDao = new ItemDaoMysql();
		Customer c1 = new Customer("Bob", "Perry");
		c1 = customerDao.create(c1);
		Long fkCustomerId = c1.getId();
		Order o1 = new Order(fkCustomerId);
		orderDao.delete(22);
		assertEquals(new ArrayList<>(), orderDao.readAll());
	}

	@Test
	public void deleteItemTest() {
		OrderDaoMysql orderDao = new OrderDaoMysql();
		CustomerDaoMysql customerDao = new CustomerDaoMysql();
		ItemDaoMysql itemDao = new ItemDaoMysql();
		Customer c1 = new Customer("Bob", "Perry");
		c1 = customerDao.create(c1);
		Long fkCustomerId = c1.getId();
		Order o1 = new Order(fkCustomerId);
		o1 = orderDao.create(o1);
		o1.setUpdateMode(false);
		Item item = new Item("Freddo", 3, 30);
		item = itemDao.create(item);
		o1.setItem(item);
		o1.setItemQuantity(5);
		Order o2 = orderDao.update(o1); // .readOrder(o1.getId());
		orderDao.delete(o2.getId(), o2.getFkCustomerId());
		o2 = orderDao.readLatest();
		assertEquals(new ArrayList<>(), o2.getItemsInOrder());
	}
}
