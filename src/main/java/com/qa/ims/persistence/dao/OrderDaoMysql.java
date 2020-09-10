package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;

public class OrderDaoMysql implements Dao<Order> {

	public static final Logger LOGGER = Logger.getLogger(OrderDaoMysql.class);

	private String jdbcConnectionUrl;
	private String username;
	private String password;

	public OrderDaoMysql() {

	}

	public OrderDaoMysql(String username, String password) {
		this.jdbcConnectionUrl = "jdbc:mysql://" + Utils.MYSQL_URL + "/ims?serverTimezone=UTC";
		this.username = username;
		this.password = password;
	}

	public OrderDaoMysql(String jdbcConnectionUrl, String username, String password) {
		this.jdbcConnectionUrl = jdbcConnectionUrl;
		this.username = username;
		this.password = password;
	}

	Order orderFromResultSet(ResultSet resultSet) throws SQLException {
		long id = resultSet.getLong("id");
		long fkCustomerId = resultSet.getLong("fk_customer_id");

		return new Order(id, fkCustomerId);
	}

	Order orderFromResultSet(ResultSet itemsRs, ResultSet order) throws SQLException {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();) {
			order.next();
			Long id = order.getLong("id");
			Long fkCustomerId = order.getLong("fk_customer_id");
			Long itemId = 0L;
			ArrayList<Item> items = new ArrayList<>(); // Items in order
			ResultSet itemSet = null; // Holds items db info

			itemSet = statement.executeQuery("SELECT * FROM items");
			while (itemsRs.next()) {
				itemId = itemsRs.getLong("fk_item_id");
				while (itemSet.next()) {
					if (itemSet.getLong("id") == itemId) {
						items.add(new Item(itemId, itemSet.getString("item_name"), itemSet.getInt("value"),
								itemSet.getInt("amount")));
					}
				}
			}
//			int orderCost = 0;
//			for (Item item : items) {
//				orderCost += item.getValue();
//			}
			int orderCost = calculateCost(id);
			Order returnable = new Order(id, fkCustomerId, items);
			returnable.setTotalCost(orderCost);
			returnable.setItemsInOrder(items);
			return returnable;
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	Item itemFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("fk_item_id");
//		

		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();) {
			String query = "SELECT * FROM items WHERE id=?";
			PreparedStatement ps = connection.prepareStatement(query);
			
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			String itemName = rs.getString("item_name");
			int value = rs.getInt("value");
			int amount = rs.getInt("amount");
			return new Item(id, itemName, value, amount);
		} catch (SQLException e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Order> readAll() {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");) {
			ArrayList<Order> orders = new ArrayList<>();
			ResultSet items = null;
			Item item = null;
			
			while (resultSet.next()) {
				Order oFromRs = orderFromResultSet(resultSet);
				Long id = oFromRs.getId();
				oFromRs.setTotalCost(calculateCost(id));
				
				String query = "SELECT * FROM order_items WHERE fk_order_id=?";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setLong(1, id);
				items = ps.executeQuery();
				
				while (items.next()) {
					item = itemFromResultSet(items);
					oFromRs.addItemToOrder(item);
				}
				orders.add(oFromRs);
			}
			return orders;
		} catch (SQLException e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	public Order readLatest() {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orders ORDER BY id DESC LIMIT 1");) {
			resultSet.next();
			return orderFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public Order readOrder(Long id) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);) {

			String queryOrderItems = "SELECT * FROM order_items WHERE fk_order_id=?";
			String queryOrders = "SELECT * FROM orders WHERE id=?";
			
			PreparedStatement psOrderItems = connection.prepareStatement(queryOrderItems);
			PreparedStatement psOrder = connection.prepareStatement(queryOrders);
			
			psOrderItems.setLong(1, id);
			psOrder.setLong(1, id);
			
			ResultSet orderItems = psOrderItems.executeQuery();
			ResultSet order = psOrder.executeQuery();
			
			return orderFromResultSet(orderItems, order);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Order create(Order order) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);) {
			String query = "INSERT INTO orders(fk_customer_id) VALUES(?)";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, order.getFkCustomerId());
			ps.executeUpdate();
			
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Order update(Order order) {
		Item item = order.getItem();
		int quantity = order.getItemQuantity();
		
		LOGGER.info(order.toString());

		if (order.isUpdateMode() == false || order.isUpdateMode() == true && order.isUpdate() == true) {
			try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
					Statement statement = connection.createStatement();) {
				statement.executeUpdate("INSERT INTO order_items(fk_order_id, fk_item_id, quantity)" + "VALUES("
						+ order.getId() + ", " + item.getId() + ", " + quantity + ")");
				return readOrder(order.getId());
			} catch (Exception e) {
				LOGGER.debug(e.getStackTrace());
				LOGGER.error(e.getMessage());
			}
		} else if (order.isUpdateMode() == true && order.isUpdate() == false) {
			delete(order.getId(), order.getItem().getId());
		}
		return null;
	}

	@Override
	public void delete(long id) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();
				Statement statement2 = connection.createStatement();) {
			statement.executeUpdate("DELETE FROM orders WHERE id=" + id);
			statement.executeUpdate("DELETE FROM order_items WHERE fk_order_id=" + id);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
	}

	public void delete(Long fk_order_id, Long fk_item_id) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();) {
			statement.executeUpdate(
					"DELETE FROM order_items WHERE fk_order_id =" + fk_order_id + " AND fk_item_id =" + fk_item_id);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
	}

	public int calculateCost(Long id) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();) {
			ResultSet cost = statement.executeQuery("SELECT SUM(order_items.quantity*items.value) AS Cost"
					+ " FROM customers" + " JOIN orders ON customers.id=orders.fk_customer_id"
					+ " JOIN order_items ON orders.id=order_items.fk_order_id"
					+ " JOIN items ON order_items.fk_item_id=items.id" + " WHERE orders.id=" + id);
			String label = cost.getMetaData().getColumnLabel(1);
//			ResultSetMetaData rsmd = cost.getMetaData();
			cost.next();
			int oCost = cost.getInt(label);
			cost.close();
			return oCost;
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

}
