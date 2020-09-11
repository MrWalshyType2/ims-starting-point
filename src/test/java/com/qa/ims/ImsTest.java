package com.qa.ims;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

public class ImsTest {
	private static String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/ims_test1?serverTimezone=UTC";
	private static String username = "root";
	private static String password = "root";
	private static String schema = "src/test/resources/sql-schema.sql";
	private static String schemaBroke = "sr/test/resources/sql-schema.sql";

	public static final Logger LOGGER = Logger.getLogger(ImsTest.class);
	
	@Test
	public void initTest() {
		Ims ims = new Ims();
		ims.init(schema);
	}

	@Test
	@Ignore
	public void doActionCreateTest() {
//		Ims ims = new Ims();
//		ims.init(jdbcUrl, username, password, schema);
//		CustomerController customerController = new CustomerController(
//				new CustomerServices(new CustomerDaoMysql(jdbcUrl, username, password)));
//		ims.doAction(customerController, Action.CREATE);
	}

	@Test
	public void readFileTest() {
		Ims ims = new Ims();
		String schemaStr = ims.readFile(schema);
		assertEquals(schemaStr, ims.readFile(schema));
	}

	@Test
	public void readFileFailTest() {
		Ims ims = new Ims();
		String schemaStr = ims.readFile(schemaBroke);
		assertEquals(schemaStr, "");
	}
}
