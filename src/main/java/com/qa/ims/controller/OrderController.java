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
			orderService.update(order);
		}

		return order;
	}

	@Override
	public Order update() {
//		LOGGER.info("Please enter your order ID: ");
//		long orderId = Long.parseLong(getInput());
//		LOGGER.info("Please enter the item ID: ");
//		long itemId = Long.parseLong(getInput());
//		LOGGER.info("Please enter the quantity required: ");
//		int quantity = Integer.parseInt(getInput());
//
//		//
//
//		Order order = null;
//		List<Order> orders = orderService.readAll();
//		for (Order o : orders) {
//			if (o.getId() == orderId) {
//				order = o;
//				break;
//			}
//		}
//		//
//		// I don't like these lines (53 - 55), it doesn't feel right. May better to
//		// place
////		Order order = new OrderDaoMysql().readOrder(orderId); // Not getting anything back
//		Item item = ((ItemDaoMysql) itemService).readItem(itemId);
////		order = new OrderDaoMysql().update(order, item, quantity);
//		order = ((OrderDaoMysql) orderService).update(order, item, quantity);
//		LOGGER.info("Order updated!");
//
//		return order;
		return null;
	}

	@Override
	public void delete() {
		LOGGER.info("Please enter the id of the order to be deleted");
		long orderId = Long.parseLong(getInput());
		orderService.delete(orderId);
		LOGGER.info("Item deleted");
	}

}
