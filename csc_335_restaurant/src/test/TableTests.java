package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Server;
import model.Table;

class TableTests {

	@Test
	void testGetters() {
		Table tableOne = new Table(1, 10);
		assertTrue(tableOne.assignServer(new Server("Jane")));
		assertEquals(1, tableOne.getTableNum());
		assertEquals(10, tableOne.getMaxCapacity());
		assertEquals("Jane", tableOne.getAssignedServerName());
	}
	
	@Test
	void testAssignServer() {
		Table tableOne = new Table(1, 10);
		assertTrue(tableOne.assignServer(new Server("Jane")));
		assertEquals("Jane", tableOne.getAssignedServerName());
		// Cannot assign server to a table with a server
		assertFalse(tableOne.assignServer(new Server("Bill")));
	}
	
	@Test
	void testRemoveServer() {
		Table tableOne = new Table(1, 10);
		assertTrue(tableOne.assignServer(new Server("Jane")));
		tableOne.removeServer();
		assertEquals("", tableOne.getAssignedServerName());
	}
	

}
