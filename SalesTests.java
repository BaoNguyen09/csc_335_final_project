package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Bill;
import model.FoodData;
import model.FoodType;
import model.Sales;

class SalesTests {

	
	@Test
	void addMultipleBills() {
		Bill newBill = new Bill();
		
		FoodData hamburger = new FoodData("Hamburger", FoodType.ENTREE, 9.99, 1, "No Tomatoes");
		
		FoodData milkshake = new FoodData("Milkshake", FoodType.DRINK, 2.99, 1, "");
		
		FoodData fries = new FoodData("Fries", FoodType.SIDE, 4.99, 1, "No Salt");
		
		FoodData twoHamburger = new FoodData("Hamburger", FoodType.ENTREE, 9.99, 4, "No Tomatoes");
		
		newBill.addFoodItem(hamburger);
		newBill.addFoodItem(milkshake);
		newBill.addFoodItem(fries);
		newBill.addFoodItem(twoHamburger);
		
		Bill newBill2 = new Bill();
		
		FoodData hamburger2 = new FoodData("Hamburger", FoodType.ENTREE, 9.99, 1, "No Tomatoes");
		
		FoodData milkshake2 = new FoodData("Milkshake", FoodType.DRINK, 2.99, 1, "");
		
		FoodData fries2 = new FoodData("Fries", FoodType.SIDE, 4.99, 1, "No Salt");
		
		FoodData twoHamburger2 = new FoodData("Hamburger", FoodType.ENTREE, 9.99, 4, "No Tomatoes");
		
		newBill2.addFoodItem(hamburger2);
		newBill2.addFoodItem(milkshake2);
		newBill2.addFoodItem(fries2);
		newBill2.addFoodItem(twoHamburger2);
		
		Sales trackSales = new Sales();
		
		trackSales.addCompletedOrder(newBill);
		trackSales.addCompletedOrder(newBill2);
		
		assertEquals(trackSales.toString(), "Item Sales:\n"
				+ "Fries: 2\n"
				+ "Hamburger: 10\n"
				+ "Milkshake: 2\n");
	}
	@Test
	void testAddingToOrder() {
		Bill newBill = new Bill();
		
		FoodData hamburger = new FoodData("Hamburger", FoodType.ENTREE, 9.99, 1, "No Tomatoes");
		
		FoodData milkshake = new FoodData("Milkshake", FoodType.DRINK, 2.99, 1, "");
		
		FoodData fries = new FoodData("Fries", FoodType.SIDE, 4.99, 1, "No Salt");
		
		FoodData twoHamburger = new FoodData("Hamburger", FoodType.ENTREE, 9.99, 4, "No Tomatoes");
		
		newBill.addFoodItem(hamburger);
		newBill.addFoodItem(milkshake);
		newBill.addFoodItem(fries);
		newBill.addFoodItem(twoHamburger);
		
		
		Sales trackSales = new Sales();
		
		trackSales.addCompletedOrder(newBill);
		
		assertEquals(trackSales.toString(), "Item Sales:\n"
				+ "Fries: 1\n"
				+ "Hamburger: 5\n"
				+ "Milkshake: 1\n");
	}
	
	@Test
	void testSales() {
		Bill newBill = new Bill();
		
		FoodData hamburger = new FoodData("Hamburger", FoodType.ENTREE, 9.99, 2, "No Tomatoes");
		
		FoodData milkshake = new FoodData("Milkshake", FoodType.DRINK, 2.99, 2, "");
		
		FoodData fries = new FoodData("Fries", FoodType.SIDE, 4.99, 2, "No Salt");
		
		newBill.addFoodItem(hamburger);
		newBill.addFoodItem(milkshake);
		newBill.addFoodItem(fries);
		
		
		Sales trackSales = new Sales();
		
		trackSales.addCompletedOrder(newBill);
		
		assertEquals(trackSales.toString(), "Item Sales:\n"
				+ "Fries: 2\n"
				+ "Hamburger: 2\n"
				+ "Milkshake: 2\n");
		
	}
}