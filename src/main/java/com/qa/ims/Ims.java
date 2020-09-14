package com.qa.ims;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.qa.ims.controller.Action;
import com.qa.ims.controller.CrudController;
import com.qa.ims.controller.CustomerController;
import com.qa.ims.controller.ItemController;
import com.qa.ims.controller.OrderController;
import com.qa.ims.persistence.dao.CustomerDaoMysql;
import com.qa.ims.persistence.dao.ItemDaoMysql;
import com.qa.ims.persistence.dao.OrderDaoMysql;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Domain;
import com.qa.ims.services.CustomerServices;
import com.qa.ims.services.ItemServices;
import com.qa.ims.services.OrderServices;
import com.qa.ims.utils.DBConnectionPool;
import com.qa.ims.utils.Utils;

public class Ims {
	
	public Customer customer = null;
	public String role = null;

	public static final Logger LOGGER = Logger.getLogger(Ims.class);

	public void imsSystem() {
		init("src/main/resources/sql-schema.sql");

		while (true) {
			while (customer == null) {
				LOGGER.info("Please signup and/or login...");
				Domain.printAuthDomains();
				
				Domain domain = Domain.getDomain();
				
				switch (domain) {
				case STOP:
					System.exit(0);
					break;
				case LOGIN:

					break;
				case SIGNUP:
					
					break;
				default:
					break;
				}
				
				if (customer != null) {
					break;
				}
			}
			
			LOGGER.info("Which entity would you like to use?");
			Domain.printDomains();

			Domain domain = Domain.getDomain();
			if (domain.equals(Domain.STOP)) {
				LOGGER.info("Shutting down...");
				System.exit(0);
			}

			LOGGER.info("What would you like to do with " + domain.name().toLowerCase() + ":");
			Action.printActions();
			Action action = Action.getAction();

			switch (domain) {
			case CUSTOMER:
				CustomerController customerController = new CustomerController(
						new CustomerServices(new CustomerDaoMysql()));
				doAction(customerController, action);
				break;
			case ITEM:
				ItemController itemController = new ItemController(
						new ItemServices(new ItemDaoMysql()));
				doAction(itemController, action);
				break;
			case ORDER:
				OrderController orderController = new OrderController(
						new OrderServices(new OrderDaoMysql()),
						new ItemServices(new ItemDaoMysql()));
				doAction(orderController, action);
				break;
			default:
				break;
			}
		}
	}

	public void doAction(CrudController<?> crudController, Action action) {
		switch (action) {
		case CREATE:
			crudController.create();
			break;
		case READ:
			crudController.readAll();
			break;
		case UPDATE:
			crudController.update();
			break;
		case DELETE:
			crudController.delete();
			break;
		case RETURN:
			break;
		default:
			break;
		}
	}

	public String readFile(String fileLocation) {
		StringBuilder stringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(fileLocation));) {
			String string;
			while ((string = br.readLine()) != null) {
				stringBuilder.append(string);
				stringBuilder.append("\r\n");
			}
		} catch (IOException e) {
			for (StackTraceElement ele : e.getStackTrace()) {
				LOGGER.debug(ele);
			}
			LOGGER.error(e.getMessage());
		}
		return stringBuilder.toString();
	}

	/**
	 * To initialise the database with the schema needed to run the application
	 */
	public void init(String fileLocation) {
		LOGGER.info("Initialising schema");
		try (Connection connection = DBConnectionPool.getConnection();
				BufferedReader br = new BufferedReader(new FileReader(fileLocation));) {
			String string;
			while ((string = br.readLine()) != null) {
				try (Statement statement = connection.createStatement();) {
					statement.executeUpdate(string);
				}
			}
		} catch (SQLException | IOException e) {
			for (StackTraceElement ele : e.getStackTrace()) {
				LOGGER.debug(ele);
			}
			LOGGER.error(e.getMessage());
		}
	}

}
