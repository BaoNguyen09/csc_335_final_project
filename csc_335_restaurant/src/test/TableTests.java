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
	void testCopyTable() {
		Group group1 = new Group();
		Customer Alice = new Customer("Alice");
		Customer Bill = new Customer("Bill");
		Customer Lily = new Customer("Lily");
		group1.addPerson(Alice);
		group1.addPerson(Bill);
		group1.addPerson(Lily);
		
		Table tableOne = new Table(1, 10);
		assertTrue(tableOne.assignServer( "Jane"));
		
		tableOne.assignGroup(group1);
		
		Table sameTable = new Table(tableOne);
		
		assertEquals(tableOne.isOrderTaken(), sameTable.isOrderTaken());
	
		
		
		assertEquals(tableOne.getGroupId(), sameTable.getGroupId() );
	}
	
//	@Test
//	void testRemoveServer() {
//		Table tableOne = new Table(1, 10);
//
//		assertTrue(tableOne.assignServer("Jane"));
//		tableOne.removeServer();
//		assertEquals("No Server", tableOne.getAssignedServerName());
//		
//		assertTrue(jane.getTables().isEmpty());
//	}
	
//	@Test
//	void testTakeOrderWhenNoGroup() {
//	    Table table = new Table(2, 4);  // no group set
//	    assertTrue(table.takeOrder().isEmpty());
//	    
//	}
//	
//	
//	@Test
//	void testTakeOrder() {
//		Table tableOne = new Table(1, 6);
//		Server Jane =  "Jane");
//		tableOne.assignServer(Jane);
//		
//		Customer Alice = new Customer("Alice");
//		Customer Bill = new Customer("Billy");
//
//		// Adding customer group
//		Group group1 = new Group();
//		group1.addPerson(Alice);
//		group1.addPerson(Bill);
//		
//		tableOne.assignGroup(group1);
//		
//		// Taking group order
//		Food cheeseBurger = menu.getItemFromMenu("All-American Cheeseburger");
//		Food teaFromMenu = menu.getItemFromMenu("iced tea");
//		
//		List<OrderFood> sessions = tableOne.takeOrder();
//		for (OrderFood personSession: sessions) {
//			
//			// Food, quantity, modifications
//			personSession.orderFood(cheeseBurger, 1, "");
//			personSession.orderFood(teaFromMenu, 2, "No Sugar");
//
//		}
//
//		// Cheeseburger (12.99), Iced tea (1.99)
//		assertEquals(33.94, group1.getTotalBill());	
//	
//	}
//	
//	@Test
//	void testCannotTakeOrderIfAlreadyTakenOrder() {
//		Table tableOne = new Table(1, 10);
//		
//		// Adding customer group
//		Group group1 = new Group();
//		Customer Alice = new Customer("Alice");
//		Customer Bill = new Customer("Billy");
//		group1.addPerson(Alice);
//		group1.addPerson(Bill);
//		
//		tableOne.assignGroup(group1);
//		// Successfully taken order
//		assertFalse(tableOne.takeOrder().isEmpty());
//		// Failed to take order second time
//	    assertTrue(tableOne.takeOrder().isEmpty());
//
//		
//	}
//	
//	@Test
//	void testCannotCloseOrderIfNoGroup() {
//		Table tableOne = new Table(1, 10);
//		assertEquals("No Order", tableOne.closeOrder());
//
//	}
//	
//	@Test
//	void testCannotCloseOrderIfNotTakenOrder() {
//		Table tableOne = new Table(1, 10);
//		
//		// Adding customer group
//		Group group1 = new Group();
//		Customer Alice = new Customer("Alice");
//		Customer Bill = new Customer("Billy");
//		group1.addPerson(Alice);
//		group1.addPerson(Bill);
//		assertEquals("No Order", tableOne.closeOrder());
//
//
//	}
//	
//	@Test
//	void testCloseOrder() {
//		Table tableOne = new Table(1, 10);
//		Server lily =  "Lily");
//		// Adding customer group
//		Group group2 = new Group();
//		Customer Alice = new Customer("Alice");
//		group2.addPerson(Alice);
//		tableOne.assignGroup(group2);
//		tableOne.assignServer(lily);
//		
//		// Taking group order
//		Food cheeseBurger = menu.getItemFromMenu("All-American Cheeseburger");
//		Food teaFromMenu = menu.getItemFromMenu("iced tea");
//		
//		List<OrderFood> sessions = tableOne.takeOrder();
//		for (OrderFood personSession: sessions) {
//			
//			// Food, quantity, modifications
//			personSession.orderFood(cheeseBurger, 1, "");
//			personSession.orderFood(teaFromMenu, 2, "No Sugar");
//
//		}
//		
//		// Cheeseburger (12.99), Iced tea (1.99)
//		assertTrue(tableOne.hastakenOrder());
//		assertEquals("Earnings: $16.97 Tips: $0.00", tableOne.closeOrder());
//
//
//	}
	
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
