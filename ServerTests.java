package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import model.Server;

class ServerTests {

	@Test
	void testTips() {
		Server s = new Server("Christopher");
		s.addTips(10.67);
		Assertions.assertEquals(s.getTotalTips(), 10.67);
	}
	
	@Test
	void testName() {
		Server s = new Server("Christopher");
		Assertions.assertEquals(s.getName(), "Christopher");
	}
	
	@Test
	void testNumSeated() {
		Server s = new Server("Christopher");
		Assertions.assertEquals(s.getSection(), 0);
	}
	
	@Test
	void testToString() {
		Server s = new Server("Christopher");
		Assertions.assertEquals(s.toString(), "Server: Christopher has: 0 people sat in their section, and has made $0.0 in tips.\n");
	}
	
}