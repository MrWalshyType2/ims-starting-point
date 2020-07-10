package com.qa.ims.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.services.CrudServices;
import com.qa.ims.utils.Utils;

public class ItemController implements CrudController<Item> {

	public static final Logger LOGGER = Logger.getLogger(ItemController.class);

	private CrudServices<Item> itemService;

	public ItemController(CrudServices<Item> itemService) {
		this.itemService = itemService;
	}

	String getInput() {
		return Utils.getInput();
	}

	@Override
	public List<Item> readAll() {
		List<Item> items = itemService.readAll();
		for (Item item : items) {
			LOGGER.info(item.toString());
		}
		return items;
	}

	@Override
	public Item create() {
		LOGGER.info("Please enter an item name: ");
		String itemName = getInput();
		LOGGER.info("Please enter the value of the item: ");
		int value = Integer.getInteger(getInput());
		LOGGER.info("Please enter the item quantity: ");
		int amount = Integer.getInteger(getInput());

		Item item = new Item(itemName, value, amount);
		LOGGER.info("Item created!");
		return item;
	}

	@Override
	public Item update() {
		LOGGER.info("Please enter the id of the item to be updated: ");
		Long id = Long.valueOf(getInput());
		LOGGER.info("Please enter a new item name: ");
		String newName = getInput();
		LOGGER.info("Please enter the new item value: ");
		int newValue = Integer.getInteger(getInput());
		LOGGER.info("Please enter the new item quantity: ");
		int newAmount = Integer.getInteger(getInput());

		Item item = itemService.update(new Item(id, newName, newValue, newAmount));
		LOGGER.info("Item updated!");
		return item;
	}

	@Override
	public void delete() {
		LOGGER.info("Please enter the id of the item to be deleted: ");
		Long id = Long.valueOf(getInput());
		itemService.delete(id);
	}

}
