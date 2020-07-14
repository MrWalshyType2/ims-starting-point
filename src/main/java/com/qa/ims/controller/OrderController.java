package com.qa.ims.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.services.CrudServices;
import com.qa.ims.utils.Utils;

public class OrderController implements CrudController<Order> {

	public static final Logger LOGGER = Logger.getLogger(OrderController.class);

	private CrudServices<Order> orderService;
	private CrudServices<Item> itemService;

	public OrderController(CrudServices<Order> orderService, CrudServices<Item> itemService) {
		this.orderService = orderService;
		this.itemService = itemService;
	}

	String getInput() {
		return Utils.getInput();
	}

	@Override
	public List<Order> readAll() {
		List<Order> orders = orderService.readAll();
		for (Order order : orders) {
			LOGGER.info(order.toString());
		}
		return orders;
	}

	@Override
	public Order create() {
		String tmp = "";
		LOGGER.info("Please enter your customer id: ");
		long customerId = Long.parseLong(getInput());

		Order order = new Order(customerId);
		order = orderService.create(order); // get the id from here
		LOGGER.info("Order created!");

		boolean exit = false;
		List<Item> items = itemService.readAll();
		Item item = null;

		while (!exit) {
			LOGGER.info("Please enter the ID of the item to be added to the order:");
			long itemId = Long.parseLong(getInput());
			LOGGER.info("Please enter the quantity required:");
			int quantity = Integer.parseInt(getInput());

			for (Item i : items) {
				if (i.getId() == itemId) {
					item = i;
					break;
				}
			}
			order.setItem(item);
			order.setItemQuantity(quantity);
			// order.addItemToOrder(item);
			order = orderService.update(order);

			LOGGER.info("Are you done with your order? N or Y");
			tmp = getInput().toLowerCase();
			if (tmp.contentEquals("y")) {
				exit = true;
			}
		}

		return order;
	}

	@Override
	public Order update() {
		LOGGER.info("Please enter your order ID: ");
		long orderId = Long.parseLong(getInput());
		Order order = null;

		List<Order> orders = orderService.readAll();
		for (Order o : orders) {
			if (o.getId() == orderId) {
				order = o;
				order.setUpdateMode(true);
			}
		}

		List<Item> items = itemService.readAll();

		LOGGER.info("Would you like to update? |'Y' to update | 'N' to delete|");
		String toUpdate = getInput().toLowerCase();

		if (toUpdate.contentEquals("y")) {
			order.setUpdate(true);
			LOGGER.info("Please enter item ID to be added to your order:");
			long itemId = Long.parseLong(getInput());

			for (Item i : items) {
				if (i.getId() == itemId) {
					order.setItem(i);
					break;
				}
			}

			LOGGER.info("How many " + order.getItem().getItemName() + " would you like?");
			order.setItemQuantity(Integer.parseInt(getInput()));
			orderService.update(order);
			LOGGER.info("Order updated!");
		} else {
			order.setUpdate(false);
			LOGGER.info("Enter the item ID to be deleted from your order:");
			long itemId = Long.parseLong(getInput());

			for (Item i : items) {
				if (i.getId() == itemId) {
					order.setItem(i);
					break;
				}
			}
			orderService.update(order);
			LOGGER.info("Item deleted!");
		}

		order.setUpdateMode(false);
		return order;
	}

	@Override
	public void delete() {
		LOGGER.info("Please enter the id of the order to be deleted");
		long orderId = Long.parseLong(getInput());
		orderService.delete(orderId);
		LOGGER.info("Item deleted");
	}

}
