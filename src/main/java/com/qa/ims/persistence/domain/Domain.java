package com.qa.ims.persistence.domain;

import org.apache.log4j.Logger;

import com.qa.ims.utils.Utils;

public enum Domain {

	CUSTOMER("Information about customers"),
	ITEM("Individual Items"),
	ORDER("Purchases of items"),
	LOGIN("To login to a user account"),
	SIGNUP("To create an account"), 
	STOP("To close the application");
	
	public static final Logger LOGGER = Logger.getLogger(Domain.class);

	private String description;
	
	private Domain(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.name() + ": " +this.description;
	}
	
	public static void printDomains() {
		for (Domain domain : Domain.values()) {
			if (domain.equals(Domain.LOGIN) || domain.equals(Domain.SIGNUP)) continue;
			LOGGER.info(domain.getDescription());
		}
	}
	
	public static void printAuthDomains() {
		for (Domain domain : Domain.values()) {
			if (domain.equals(Domain.LOGIN) || domain.equals(Domain.SIGNUP) || domain.equals(Domain.STOP)) {
				LOGGER.info(domain.getDescription());
			}
		}
	}
	
	public static Domain getDomain() {
		Domain domain;
		while (true) {
			try {
				domain = Domain.valueOf(Utils.getInput().toUpperCase());
				break;
			} catch (IllegalArgumentException e) {
				LOGGER.error("Invalid selection please try again");
			}
		}
		return domain;
	}
	
}
