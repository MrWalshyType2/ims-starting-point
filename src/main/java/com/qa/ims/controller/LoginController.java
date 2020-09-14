package com.qa.ims.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.services.CrudServices;
import com.qa.ims.services.ReadUsername;

public class LoginController implements CrudController<Customer>, ReadUsername<Customer> {

	public static final Logger LOGGER = Logger.getLogger(LoginController.class);

	private CrudServices<Customer> loginService;
	
	public LoginController(CrudServices<Customer> loginService) {
		this.loginService = loginService;
	}
	
	@Override
	public Customer readByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer create() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
}
