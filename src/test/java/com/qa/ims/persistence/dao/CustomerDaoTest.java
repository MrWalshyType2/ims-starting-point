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
import com.qa.ims.persistence.domain.Customer;

public class CustomerDaoTest {
	private static String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/ims_test1?serverTimezone=UTC";
	private static String username = "root";
	private static String password = "root";

	public static final Logger LOGGER = Logger.getLogger(CustomerDaoTest.class);

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
			statement.executeUpdate("delete from customers");
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
	}

	@Test
	public void constructorTest() {
		CustomerDaoMysql cdm = new CustomerDaoMysql(username, password);
	}

	@Test
	public void createTest() {
		CustomerDaoMysql customerDao = new CustomerDaoMysql(jdbcUrl, username, password);
		String firstName = "Fred";
		String surname = "Perry";
		Customer customer = new Customer(firstName, surname);
		Customer savedCustomer = new Customer(firstName, surname);
		customer = customerDao.create(customer);
		customer.setId(null);
		assertEquals(savedCustomer, customer);
	}

	@Test
	public void createFailTest() {
		CustomerDaoMysql customerDao = new CustomerDaoMysql(jdbcUrl, username, password);
		Customer customer = null;
		customer = customerDao.create(customer);
		assertNull(customer);
	}

	@Test
	public void readAllTest() {
		CustomerDaoMysql customerDao = new CustomerDaoMysql(jdbcUrl, username, password);
		ArrayList<Customer> customers = new ArrayList<>();
		customers.add(new Customer("Bob", "Perry"));
		customerDao.create(new Customer("Bob", "Perry"));
		ArrayList<Customer> savedCustomers = customerDao.readAll();
		savedCustomers.get(0).setId(null);
		// assertEquals(customers, savedCustomers);
	}

//	@Test
//	public void readAllFailTest() {
//		CustomerDaoMysql customerDao = new CustomerDaoMysql(jdbcUrl, username, password);
//		ArrayList<Customer> customers = new ArrayList<>();
//		customers.add(new Customer("Bob", "Perry"));
//		Customer customer = customerDao.create(new Customer("Bob", "Perry"));
//		customerDao.delete(customer.getId());
//		ArrayList<Customer> savedCustomers = customerDao.readAll();
//		savedCustomers.get(0).setId(null);
//		assertEquals(customers, savedCustomers);
//	}

	@Test
	public void readCustomerTest() {
		CustomerDaoMysql customerDao = new CustomerDaoMysql(jdbcUrl, username, password);
		Customer savedCustomer = customerDao.create(new Customer("Fred", "Perry"));
		Long id = savedCustomer.getId();
		Customer customer = new Customer(id, "Fred", "Perry");
		assertEquals(customer, customerDao.readCustomer(id));
	}

	@Test
	public void readCustomerFailTest() {
		CustomerDaoMysql customerDao = new CustomerDaoMysql(jdbcUrl, username, password);
		Customer savedCustomer = customerDao.create(new Customer("Fred", "Perry"));
		Long id = 567L;
		assertNull(customerDao.readCustomer(id));
	}

	@Test
	public void updateTest() {
		CustomerDaoMysql customerDao = new CustomerDaoMysql(jdbcUrl, username, password);
		Customer savedCustomer = customerDao.create(new Customer("Fred", "Perry"));
		savedCustomer.setFirstName("Bob");
		Customer updated = customerDao.update(savedCustomer);
		assertEquals(savedCustomer, updated);
	}

	@Test
	public void updateFailTest() {
		CustomerDaoMysql customerDao = new CustomerDaoMysql(jdbcUrl, username, password);
		Customer savedCustomer = customerDao.create(new Customer("Fred", "Perry"));
		savedCustomer = null;
		Customer updated = customerDao.update(savedCustomer);
		assertEquals(savedCustomer, updated);
	}

	@Test
	public void deleteTest() {
		CustomerDaoMysql customerDao = new CustomerDaoMysql(jdbcUrl, username, password);
		Customer savedCustomer = customerDao.create(new Customer("Fred", "Perry"));
		customerDao.delete(savedCustomer.getId());
		ArrayList<Customer> empty = new ArrayList<>();
		// assertEquals(empty, customerDao.readAll());
	}

	@Test
	public void deleteFailTest() {
		CustomerDaoMysql customerDao = new CustomerDaoMysql(jdbcUrl, username, password);
		Customer savedCustomer = customerDao.create(new Customer("Fred", "Perry"));
		customerDao.delete(57L);
		ArrayList<Customer> empty = new ArrayList<>();
		assertNotEquals(empty, customerDao.readAll());
	}
}
