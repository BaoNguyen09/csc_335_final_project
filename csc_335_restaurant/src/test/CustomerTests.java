package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Bill;
import model.Customer;
import model.Food;
import model.FoodType;

class CustomerTests {

	@Test
	void testCost() {
		Customer person = new Customer("Bob");
		Food food = new Food("Cheese Burger", FoodType.ENTREE, 6.59);
		person.orderFood(food, 1, "None");
		person.tip(2.60);
		assertEquals(person.getBillCost(), 9.19);
	}
	
	@Test
	void testPayBill() {
		Customer person = new Customer("Bob");
		person.payBill();
		assertTrue(person.isBillPaid());
	}
	
	@Test
	void testPayBillTwo() {
		Customer person = new Customer("Bob");
		assertFalse(person.isBillPaid());
	}
	
	@Test
	void testGetBill() {
		Customer person = new Customer("Bob");
		assertTrue(person.getBill() instanceof Bill);
	}
	
	@Test
	void testGetName() {
		Customer person = new Customer("Bob");
		assertEquals(person.getName(), "Bob");
	}
	
	@Test
	void testToString() {
		Customer person = new Customer("Bob");
		Food food = new Food("Cheese Burger", FoodType.ENTREE, 6.59);
		person.orderFood(food, 1, "None");
		assertEquals(person.toString(), "Name: Bob, Order: FoodData [food=Cheese Burger, quantity=1, total price: $6.59\nmodifications=None]"
				+ "\nTip: $0.0\nTotal Cost: $6.59\n");
	}
}
