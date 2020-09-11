package com.qa.ims.utils;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBConnectionPool {

	private static BasicDataSource ds = new BasicDataSource();
	
	private DBConnectionPool() {}
	
	// Static block initialisation
	static {
		ds.setUrl(Utils.getProperty("jdbcUrl"));
		ds.setUsername(Utils.getProperty("username"));
		ds.setPassword(Utils.getProperty("password"));
		ds.setMinIdle(5);
		ds.setMaxIdle(10);
		ds.setMaxOpenPreparedStatements(100);
	}
	
	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
}
