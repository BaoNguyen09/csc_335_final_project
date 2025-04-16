package test;

import model.*;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BillTests {

	@Test
	void testBill1() {
		Bill newBill = new Bill();
		FoodData hamburger = new FoodData("Hamburger", FoodType.ENTREE, 9.99, 1, "No Tomatoes");
		
		FoodData milkshake = new FoodData("Milkshake", FoodType.DRINK, 2.99, 1, "");
		
		FoodData fries = new FoodData("Fries", FoodType.SIDE, 4.99, 1, "No Salt");
				
		newBill.addFoodItem(hamburger);
		newBill.addFoodItem(milkshake);
		newBill.addFoodItem(fries);
		
	
		
		assertEquals(newBill.toString(), "Order: FoodData [food=Hamburger, quantity=1, price: $9.99\n"
				+ "modifications=No Tomatoes]\n"
				+ "FoodData [food=Milkshake, quantity=1, price: $2.99\n"
				+ "modifications=]\n"
				+ "FoodData [food=Fries, quantity=1, price: $4.99\n"
				+ "modifications=No Salt]\n"
				+ "Tip: $0.0\n"
				+ "Total Cost: $17.97\n" );
		
		assertEquals(newBill.calculateBill(), 17.97);

	}
	
	@Test 
	void testBill2() {
		Bill newBill = new Bill();
		
		FoodData hamburger = new FoodData("Hamburger", FoodType.ENTREE, 9.99, 2, "No Tomatoes");
		
		FoodData milkshake = new FoodData("Milkshake", FoodType.DRINK, 2.99, 2, "");
		
		FoodData fries = new FoodData("Fries", FoodType.SIDE, 4.99, 2, "No Salt");
		
		newBill.addFoodItem(hamburger);
		newBill.addFoodItem(milkshake);
		newBill.addFoodItem(fries);
		
		
		assertEquals(newBill.toString(), "Order: FoodData [food=Hamburger, quantity=2, price: $19.98\n"
				+ "modifications=No Tomatoes]\n"
				+ "FoodData [food=Milkshake, quantity=2, price: $5.98\n"
				+ "modifications=]\n"
				+ "FoodData [food=Fries, quantity=2, price: $9.98\n"
				+ "modifications=No Salt]\n"
				+ "Tip: $0.0\n"
				+ "Total Cost: $35.94\n" );
		
		assertEquals(newBill.calculateBill(), 35.94);
	}
	
	@Test 
	void testBill3() {
		Bill newBill = new Bill();
		FoodData hamburger = new FoodData("Hamburger", FoodType.ENTREE, 9.99, 2, "No Tomatoes");
		
		FoodData milkshake = new FoodData("Milkshake", FoodType.DRINK, 2.99, 2, "");
		
		FoodData fries = new FoodData("Fries", FoodType.SIDE, 4.99, 2, "No Salt");
		
		newBill.addFoodItem(hamburger);
		newBill.addFoodItem(milkshake);
		newBill.addFoodItem(fries);
		
		newBill.setTip(15.00);
		
		assertEquals(newBill.toString(), "Order: FoodData [food=Hamburger, quantity=2, price: $19.98\n"
				+ "modifications=No Tomatoes]\n"
				+ "FoodData [food=Milkshake, quantity=2, price: $5.98\n"
				+ "modifications=]\n"
				+ "FoodData [food=Fries, quantity=2, price: $9.98\n"
				+ "modifications=No Salt]\n"
				+ "Tip: $15.0\n"
				+ "Total Cost: $50.94\n" );
		
		assertEquals(newBill.calculateBill(), 50.94);
	}
	
	@Test 
	void testCopyConstructor() {
		Bill newBill = new Bill();
		FoodData hamburger = new FoodData("Hamburger", FoodType.ENTREE, 9.99, 2, "No Tomatoes");
		
		FoodData milkshake = new FoodData("Milkshake", FoodType.DRINK, 2.99, 2, "");
		
		FoodData fries = new FoodData("Fries", FoodType.SIDE, 4.99, 2, "No Salt");
		
		newBill.addFoodItem(hamburger);
		newBill.addFoodItem(milkshake);
		newBill.addFoodItem(fries);
		
		newBill.setTip(15.00);
		
		Bill copyBill = new Bill(newBill);
		assertEquals(newBill.toString(), copyBill.toString());
	}
}

