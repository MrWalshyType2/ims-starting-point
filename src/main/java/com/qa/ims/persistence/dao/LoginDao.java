package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.DBConnectionPool;
import com.qa.ims.utils.Utils;

public class LoginDao implements Dao<Customer> {
	
	public static final Logger LOGGER = Logger.getLogger(LoginDao.class);
	
	public String getInput() {
		return Utils.getInput();
	}
	
	private Customer customerFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		String firstName = resultSet.getString("first_name");
		String surname = resultSet.getString("surname");
		return new Customer(id, firstName, surname);
	}
	
	private Customer readLatest() {
		try (Connection connection = DBConnectionPool.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM customers ORDER BY id DESC LIMIT 1");) {
			resultSet.next();
			return customerFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Customer read(Long id) {
		LOGGER.info("Please enter your username:");
		String username = getInput();
		LOGGER.info("Please enter your password:");
		String password = getInput();
		
		try (Connection connection = DBConnectionPool.getConnection()) {
			String query = "SELECT * FROM customers WHERE username=?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) throw new Exception("Username not found");
			rs.next();
			Customer customer = customerFromResultSet(rs);
			
			if (customer.getPassword().contentEquals(password)) {
				return customer;
			}
			return null;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}

	@Override
	public List<Customer> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer create(Customer customer) {
		try (Connection connection = DBConnectionPool.getConnection()) {
			String query = "INSERT INTO customers(first_name, surname, username, password) VALUES(?, ?, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(query);
			
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getSurname());
			ps.setString(3, customer.getUsername());
			ps.setString(4, customer.getPassword());
			ps.executeUpdate();
			
			return readLatest();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Customer update(Customer t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

}
