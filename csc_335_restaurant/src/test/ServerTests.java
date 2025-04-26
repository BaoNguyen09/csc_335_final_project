package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import model.Server;

class ServerTests {

	@Test
	void testTips() {
		Server s = new Server("Christopher");
		s.addTips(10.49);
		Assertions.assertEquals(s.getTips(), 10.49);
	}
	
	@Test
	void testName() {
		Server s = new Server("Christopher");
		Assertions.assertEquals(s.getName(), "Christopher");
	}
	
	@Test
	void testToString() {
		Server s = new Server("Christopher");
		Assertions.assertEquals(s.toString(), "Server: Christopher has made $0.0 in tips.\n");
	}
	
	@Test
	void testGetAddRemoveTables() {
		Server s = new Server("Christopher"); 
		s.addTable(0);
		s.addTable(1);
		s.addTable(4);
		ArrayList<Integer> expectedTables = new ArrayList<>();
		expectedTables.add(0);
		expectedTables.add(1);
		expectedTables.add(4);
		Assertions.assertEquals(expectedTables, s.getTables());
		s.removeTable(4);
		expectedTables.remove(Integer.valueOf(4));
		Assertions.assertEquals(s.getTables().size(), 2);
		Assertions.assertEquals(expectedTables, s.getTables());
	}
	
	@Test
	void testCopyConstructor() {
		Server s = new Server("Christopher");
		s.addTips(10.49);
		
		Server copyServer = new Server(s);
		
		assertEquals(s.getTips(), copyServer.getTips());
		assertEquals(s.getName(), copyServer.getName());
	}
}
