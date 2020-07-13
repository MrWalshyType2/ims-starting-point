package com.qa.ims.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.dao.ItemDaoMysql;
import com.qa.ims.persistence.dao.OrderDaoMysql;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.services.CrudServices;
import com.qa.ims.utils.Utils;

public class OrderController implements CrudController<Order> {

	public static final Logger LOGGER = Logger.getLogger(OrderController.class);

	private CrudServices<Order> orderService;

	public OrderController(CrudServices<Order> orderService) {
		this.orderService = orderService;
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
		orderService.create(order);
		LOGGER.info("Order created!");
		return order;
	}

	@Override
	public Order update() {
		LOGGER.info("Please enter your order ID: ");
		long orderId = Long.parseLong(getInput());
		LOGGER.info("Please enter the item ID: ");
		long itemId = Long.parseLong(getInput());
		LOGGER.info("Please enter the quantity required: ");
		int quantity = Integer.parseInt(getInput());

		//

		Order orderWanted = null;
		List<Order> orders = orderService.readAll();
		for (Order o : orders) {
			if (o.getId() == orderId) {
				orderWanted = o;
				break;
			}
		}
		//
		// I don't like these lines (53 - 55), it doesn't feel right. May better to
		// place
//		Order order = new OrderDaoMysql().readOrder(orderId); // Not getting anything back
		Item item = new ItemDaoMysql().readItem(itemId);
		orderWanted = new OrderDaoMysql().update(orderWanted, item, quantity);
		LOGGER.info("Order updated!"); // Fixed this

		return orderWanted;
	}

	@Override
	public void delete() {
		LOGGER.info("Please enter the id of the order to be deleted");
		long orderId = Long.parseLong(getInput());
		orderService.delete(orderId);
		LOGGER.info("Item deleted");
	}

}
