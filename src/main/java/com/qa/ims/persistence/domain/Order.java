package com.qa.ims.persistence.domain;

import java.util.ArrayList;

public class Order {
	private long id;
	private long fkCustomerId;
	private Item item = null;
	private String name;
	private int totalCost;
	private int itemQuantity = 0;
	private ArrayList<Item> itemsInOrder = new ArrayList<>();
	private boolean update = false;
	private boolean updateMode = false;

	public Order(long fkCustomerId) {
		this.fkCustomerId = fkCustomerId;
	}

	public Order(long id, long fkCustomerId) {
		this.id = id;
		this.fkCustomerId = fkCustomerId;
	}

	public Order(long id, long fkCustomerId, ArrayList<Item> items) {
		this.id = id;
		this.fkCustomerId = id;
		this.itemsInOrder = items;
	}

	public Order(long id, long fkCustomerId, ArrayList<Item> items, int cost) {
		this.id = id;
		this.fkCustomerId = id;
		this.itemsInOrder = items;
		this.totalCost = cost;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getFkCustomerId() {
		return fkCustomerId;
	}

	public void setFkCustomerId(long fkCustomerId) {
		this.fkCustomerId = fkCustomerId;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}

	public ArrayList<Item> getItemsInOrder() {
		return itemsInOrder;
	}

	public void setItemsInOrder(ArrayList<Item> itemsInOrder) {
		this.itemsInOrder = itemsInOrder;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public boolean isUpdateMode() {
		return updateMode;
	}

	public void setUpdateMode(boolean updateMode) {
		this.updateMode = updateMode;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", fkCustomerId=" + fkCustomerId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (fkCustomerId ^ (fkCustomerId >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (fkCustomerId != other.fkCustomerId)
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}
