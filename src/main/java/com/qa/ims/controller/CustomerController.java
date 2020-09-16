package com.qa.ims.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.ImsObserver;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.services.CrudServices;
import com.qa.ims.utils.Observer;
import com.qa.ims.utils.Utils;

/**
 * Takes in customer details for CRUD functionality
 *
 */
public class CustomerController implements CrudController<Customer>{

	public static final Logger LOGGER = Logger.getLogger(CustomerController.class);
	
	private CrudServices<Customer> customerService;
	private ImsObserver imsObserver;
	
	public CustomerController(CrudServices<Customer> customerService, ImsObserver o) {
		this.customerService = customerService;
		this.imsObserver = o;
	}
	

	String getInput() {
		return Utils.getInput();
	}
	
	private boolean checkAdminRole() {
		if (imsObserver.getRole() == null) return false;
		if (imsObserver.getRole().equalsIgnoreCase("ADMIN")) {
			return true;
		}
		return false;
	}
	
	private boolean checkCustomerRole() {
		if (imsObserver.getRole() == null) return false;
		if (imsObserver.getRole().equalsIgnoreCase("CUSTOMER")) {
			return true;
		}
		return false;
	}
	
	@Override
	public Customer read() {
		if (checkAdminRole()) {
			// Something here for admins
		} else if (checkCustomerRole()) {
			// for customers
			return imsObserver.getCustomer();
		}
		return null;
	}


	/**
	 * Reads all customers to the logger
	 */
	@Override
	public List<Customer> readAll() {
		if (checkAdminRole()) {
			List<Customer> customers = customerService.readAll();
			for(Customer customer: customers) {
				LOGGER.info(customer.toString());
			}
			return customers;
		} else if (checkCustomerRole()) {
			// for customers
			ArrayList<Customer> customers = new ArrayList<>();
			Customer customer = imsObserver.getCustomer();

			LOGGER.info(customer);
			return customers;
		}
		return null;
		
	}

	/**
	 * Creates a customer by taking in user input
	 */
	@Override
	public Customer create() {
		LOGGER.info("Please enter a first name");
		String firstName = getInput();
		LOGGER.info("Please enter a surname");
		String surname = getInput();
		Customer customer = customerService.create(new Customer(firstName, surname));
		LOGGER.info("Customer created");
		return customer;
	}

	/**
	 * Updates an existing customer by taking in user input
	 */
	@Override
	public Customer update() {
		LOGGER.info("Please enter the id of the customer you would like to update");
		Long id = Long.valueOf(getInput());
		LOGGER.info("Please enter a first name");
		String firstName = getInput();
		LOGGER.info("Please enter a surname");
		String surname = getInput();
		Customer customer = customerService.update(new Customer(id, firstName, surname));
		LOGGER.info("Customer Updated");
		return customer;
	}

	/**
	 * Deletes an existing customer by the id of the customer
	 */
	@Override
	public void delete() {
		LOGGER.info("Please enter the id of the customer you would like to delete");
		Long id = Long.valueOf(getInput());
		customerService.delete(id);
	}
	
}
