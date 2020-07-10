package com.qa.ims.persistence.domain;

public class Order {
	private long id;
	private long fkCustomerId;

	public Order(long fkCustomerId) {
		this.fkCustomerId = fkCustomerId;
	}

	public Order(long id, long fkCustomerId) {
		this.id = id;
		this.fkCustomerId = fkCustomerId;
	}

	public long getId() {
		return id;
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
