package test;

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
	void testGetTables() {
		Server s = new Server("Christopher"); 
		s.addTable(0);
		s.addTable(1);
		s.addTable(4);
		s.removeTable(0);
		Assertions.assertEquals(s.getTables().size(), 2);
	}
	
}