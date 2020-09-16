package com.qa.ims;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.Observer;

public class ImsObserver implements Observer {
	
	private Ims ims = null;
	private Customer customer = null;
	private String role = null;
	
	public ImsObserver(Ims ims) {
		this.ims = ims;
	}

	@Override
	public void update() {
		this.customer = ims.getCustomer();
	}
	
	public void printCurrentUser() {
		System.out.println(customer.toString());
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
