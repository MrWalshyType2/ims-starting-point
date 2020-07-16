package com.qa.ims.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;

public class ActionTest {

	@Test
	public void actionTest() {
		assertTrue(Action.CREATE.toString().toLowerCase().contains("create"));
		assertTrue(Action.READ.toString().toLowerCase().contains("read"));
		assertTrue(Action.UPDATE.toString().toLowerCase().contains("update"));
		assertTrue(Action.DELETE.toString().toLowerCase().contains("delete"));
		assertTrue(Action.RETURN.toString().toLowerCase().contains("return"));
	}

	@Test
	public void getDescriptionTest() {
		assertTrue(Action.CREATE.getDescription().toLowerCase()
				.contentEquals("create: to save a new item into the database"));
	}

	@Test
	public void printActionsTest() {
		Action.printActions();
	}

	@Test
	public void getActionTest() {
		String input = "create";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Action action = Action.getAction();
		assertEquals(Action.CREATE, action);
	}

}
