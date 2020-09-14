package com.qa.ims.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.dao.Dao;
import com.qa.ims.persistence.domain.Customer;

public class LoginServices implements CrudServices<Customer>, ReadUsername<Customer> {
	
	public static final Logger LOGGER = Logger.getLogger(LoginServices.class);

	private Dao<Customer> loginDao;
	
	public LoginServices(Dao<Customer> loginDao) {
		this.loginDao = loginDao;
	}
	
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
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}
}
