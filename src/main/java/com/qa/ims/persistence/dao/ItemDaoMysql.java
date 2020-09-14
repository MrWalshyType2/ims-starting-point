package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.DBConnectionPool;

public class ItemDaoMysql implements Dao<Item> {

	public static final Logger LOGGER = Logger.getLogger(ItemDaoMysql.class);

	public ItemDaoMysql() {

	}

	Item itemFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		String itemName = resultSet.getString("item_name");
		int value = resultSet.getInt("value");
		int amount = resultSet.getInt("amount");
		return new Item(id, itemName, value, amount);
	}

	@Override
	public Item read(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Item> readAll() {
		try (Connection connection = DBConnectionPool.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("select * from items");) {
			ArrayList<Item> items = new ArrayList<>();
			while (resultSet.next()) {
				items.add(itemFromResultSet(resultSet));
			}
			return items;
		} catch (SQLException e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	public Item readLatest() {
		try (Connection connection = DBConnectionPool.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM items ORDER BY id DESC LIMIT 1");) {
			resultSet.next();
			return itemFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public Item readItem(long id) {
		try (Connection connection = DBConnectionPool.getConnection();
				Statement statement = connection.createStatement();) {
			String query = "SELECT * FROM items WHERE id=?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return itemFromResultSet(rs);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Item create(Item item) {
		try (Connection connection = DBConnectionPool.getConnection();
				Statement statement = connection.createStatement();) {
			String query = "INSERT INTO items(item_name, value, amount) VALUES(?, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(query);
			
			ps.setString(1, item.getItemName());
			ps.setInt(2, item.getValue());
			ps.setInt(3, item.getAmount());
			ps.executeUpdate();
			
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Item update(Item item) {
		try (Connection connection = DBConnectionPool.getConnection();
				Statement statement = connection.createStatement();) {
			String query = "UPDATE items SET item_name=?, value=?, amount=? WHERE id=?";
			PreparedStatement ps = connection.prepareStatement(query);
			
			ps.setString(1, item.getItemName());
			ps.setInt(2, item.getValue());
			ps.setInt(3, item.getAmount());
			ps.setLong(4, item.getId());
			ps.executeUpdate();
			
			return readItem(item.getId());
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public void delete(long id) {
		try (Connection connection = DBConnectionPool.getConnection();
				Statement statement = connection.createStatement();) {
			String query = "DELETE FROM items WHERE id=?";
			PreparedStatement ps = connection.prepareStatement(query);
			
			ps.setLong(1, id);
			ps.executeUpdate();
			LOGGER.info("Deleted item with ID " + id);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
	}

}
