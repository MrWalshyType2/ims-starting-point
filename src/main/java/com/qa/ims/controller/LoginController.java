package com.qa.ims.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.dao.Dao;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.services.CrudServices;
import com.qa.ims.utils.Utils;

public class LoginController implements CrudController<Customer> {

	public static final Logger LOGGER = Logger.getLogger(LoginController.class);

	private Dao<Customer> loginDao;
	
	public LoginController(Dao<Customer> loginDao) {
		this.loginDao = loginDao;
	}
	
	public String getInput() {
		return Utils.getInput();
	}

	@Override
	public Customer read() {
		Customer stored = loginDao.read(0l);
		return stored;
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
