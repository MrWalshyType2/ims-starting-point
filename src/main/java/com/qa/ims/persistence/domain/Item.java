package com.qa.ims.persistence.domain;

public class Item {
	private Long id;
	private String itemName;
	private int value;
	private int amount;

	public Item(String itemName, int value, int amount) {
		this.itemName = itemName;
		this.value = value;
		this.amount = amount;
	}

	public Item(Long id, String itemName, int value, int amount) {
		this.id = id;
		this.itemName = itemName;
		this.value = value;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "ID: " + id + ", Item Name: " + itemName + ", Value: £" + value + ", Amount: " + amount;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else {
			return false;
		}
	}
}
