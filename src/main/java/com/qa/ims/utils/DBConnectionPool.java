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
		// When .close() is called on the connection obj, it is returned to the pool instead
		// of the connection being closed
		return ds.getConnection();
	}
}
