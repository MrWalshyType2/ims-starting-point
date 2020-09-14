package com.qa.ims.persistence.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.services.ReadUsername;

public class LoginDao implements Dao<Customer>, ReadUsername<Customer> {
	
	public static final Logger LOGGER = Logger.getLogger(LoginDao.class);

	@Override
	public Customer readByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer read(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer create(Customer t) {
		// TODO Auto-generated method stub
		return null;
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
