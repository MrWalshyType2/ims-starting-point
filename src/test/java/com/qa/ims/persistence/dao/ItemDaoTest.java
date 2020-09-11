package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
import com.qa.ims.persistence.domain.Item;

public class ItemDaoTest {
	private static String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/ims_test1?serverTimezone=UTC";
	private static String username = "root";
	private static String password = "root";

	public static final Logger LOGGER = Logger.getLogger(ItemDaoTest.class);

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
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("delete from items");
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
	}

	@Test
	public void constructorTest() {
		ItemDaoMysql idao = new ItemDaoMysql();
		ItemDaoMysql idao2 = new ItemDaoMysql();
	}

	@Test
	public void createTest() {
		ItemDaoMysql itemDao = new ItemDaoMysql();
		String itemName = "Freddo";
		int value = 5;
		int quantity = 100;
		Item item = new Item(itemName, value, quantity);
		Item savedItem = new Item(itemName, value, quantity);
		item = itemDao.create(item);
		savedItem.setId(item.getId());
		assertEquals(savedItem.getAmount(), item.getAmount());
	}

	@Test
	public void createFailTest() {
		ItemDaoMysql itemDao = new ItemDaoMysql();
		String itemName = "Freddo";
		int value = 5;
		int quantity = 100;
		Item item = null;
		Item savedItem = new Item(itemName, value, quantity);
		item = itemDao.create(item);
		assertNull(item);
	}

	@Test
	public void readAllTest() {
		ItemDaoMysql itemDao = new ItemDaoMysql();
		ArrayList<Item> items = new ArrayList<>();
		items.add(new Item("Freddo", 5, 500));
		itemDao.create(new Item("Freddo", 5, 500));
		ArrayList<Item> savedItems = itemDao.readAll();
		savedItems.get(0).setId(null);
		assertEquals(items.get(0).getAmount(), savedItems.get(0).getAmount());
	}

	@Test
	public void readItemTest() {
		ItemDaoMysql itemDao = new ItemDaoMysql();
		Item savedItem = itemDao.create(new Item("Freddo", 5, 523));
		Long id = savedItem.getId();
		Item item = new Item(id, "Freddo", 5, 523);
		assertEquals(item.getId(), itemDao.readItem(id).getId());
	}

	@Test
	public void readItemFailTest() {
		ItemDaoMysql itemDao = new ItemDaoMysql();
		Item savedItem = itemDao.create(new Item("Freddo", 5, 523));
		Long id = 567L;
		assertNull(itemDao.readItem(id));
	}

	@Test
	public void updateTest() {
		ItemDaoMysql itemDao = new ItemDaoMysql();
		Item savedItem = itemDao.create(new Item("Freddo", 55, 3));
		savedItem.setItemName("Bob");
		Item updated = itemDao.update(savedItem);
		assertEquals(savedItem.toString(), updated.toString());
	}

	@Test
	public void updateFailTest() {
		ItemDaoMysql itemDao = new ItemDaoMysql();
		Item savedItem = itemDao.create(new Item("Freddo", 55, 3));
		savedItem = null;
		Item updated = itemDao.update(savedItem);
		assertNull(updated);
	}

	@Test
	public void deleteTest() {
		ItemDaoMysql itemDao = new ItemDaoMysql();
		Item savedItem = itemDao.create(new Item("Freddo", 55, 34));
		itemDao.delete(savedItem.getId());
		ArrayList<Item> empty = new ArrayList<>();
		assertEquals(empty, itemDao.readAll());
	}

	@Test
	public void deleteFailTest() {
		ItemDaoMysql itemDao = new ItemDaoMysql();
		Item savedItem = itemDao.create(new Item("Freddo", 55, 34));
		itemDao.delete(-5);
		ArrayList<Item> empty = new ArrayList<>();
		assertNotEquals(empty, itemDao.readAll());
	}
}
