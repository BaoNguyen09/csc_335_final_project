package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Customer;
import model.Food;
import model.Group;
import model.Server;
import model.Table;
import model.Menu;
import model.OrderFood;


class TableTests {
	
	Menu menu = new Menu();

	@Test
	void testGetters() {
		Table tableOne = new Table(1, 10);
		assertTrue(tableOne.assignServer( "Jane"));
		assertEquals(1, tableOne.getTableNum());
		assertEquals(10, tableOne.getMaxCapacity());
		assertEquals("Jane", tableOne.getAssignedServerName());
		assertFalse(tableOne.isOccupied());
		
		Group group1 = new Group();
		tableOne.assignGroup(group1);
		assertTrue(tableOne.isOccupied());

	}
	
	@Test
	void testGetAssignedServerNameWhenNoServerAssigned() {
		Table tableOne = new Table(1, 10);
		assertEquals("", tableOne.getAssignedServerName());
	}
	
	@Test
	void testAssignServer() {
		Table tableOne = new Table(1, 10);
		assertTrue(tableOne.assignServer("Jane"));
		assertEquals("Jane", tableOne.getAssignedServerName());
		
		// Cannot assign server to a table with a server
		assertFalse(tableOne.assignServer("Bill"));
	}
	
	
	@Test
	void testCannotAssignGroupToTableWithGroup() {
		Table tableOne = new Table(1, 10);
		
		// Adding customer group
		Group group1 = new Group();
		Group group2 = new Group();
		assertTrue(tableOne.assignGroup(group1));
		assertFalse(tableOne.assignGroup(group2));

	}
	
	@Test
	void testCannotAssignGroupThatExceedTableCapacity() {
		// Table of size 2
		Table tableOne = new Table(1, 2);
		
		// Group of size 4
		Group group1 = new Group();
		Customer Alice = new Customer("Alice");
		Customer Bill = new Customer("Bill");
		Customer Lily = new Customer("Lily");
		group1.addPerson(Alice);
		group1.addPerson(Bill);
		group1.addPerson(Lily);
		
		assertFalse(tableOne.assignGroup(group1));

	}
	
	@Test
	void testClearTable() {
		// Table of size 2
		Table tableOne = new Table(1, 4);
		tableOne.assignServer("Jane");
		// Group of size 4
		Group group1 = new Group();
		Customer Alice = new Customer("Alice");
		Customer Bill = new Customer("Bill");
		Customer Lily = new Customer("Lily");
		group1.addPerson(Alice);
		group1.addPerson(Bill);
		group1.addPerson(Lily);
		tableOne.assignGroup(group1);
		
		assertTrue(tableOne.isOccupied());
		assertEquals("Jane", tableOne.getAssignedServerName());

		tableOne.clearTable();
		
		assertFalse(tableOne.isOccupied());
		assertEquals("", tableOne.getAssignedServerName());
	}
	

}
