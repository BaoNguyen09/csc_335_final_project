package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import model.Bill;
import model.Food;
import model.FoodData;
import model.FoodType;
import model.Sales;
import model.Menu;

class SalesTests {

	Menu menu = new Menu();
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
		
		assertTrue(hamburger2.equals(hamburger));
		
		assertEquals(trackSales.toString(), "Item Sales:\n"
				+ "Fries: 2\n"
				+ "Hamburger: 10\n"
				+ "Milkshake: 2\n");
		
		HashMap<Food, Integer> copySales = trackSales.getSales();
		assertEquals(10, copySales.get(new Food("Hamburger", FoodType.ENTREE, 9.99)));
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
	
	@Test
	void testSortMostSales() {
		Bill newBill = new Bill();
		
		FoodData hamburger = new FoodData("Hamburger", FoodType.ENTREE, 9.99, 1, "No Tomatoes");
		
		FoodData milkshake = new FoodData("Milkshake", FoodType.DRINK, 2.99, 1, "");
		
		FoodData fries = new FoodData("Fries", FoodType.SIDE, 4.99, 2, "No Salt");
		
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
		
		ArrayList<FoodData> mostSales = new ArrayList<>();
		FoodData copyHamburger = new FoodData(hamburger);
		copyHamburger.setQuantity(10);
		copyHamburger.setModifications("");

		FoodData copyMilkshake = new FoodData(milkshake);
		copyMilkshake.setQuantity(2);
		copyMilkshake.setModifications("");
		
		FoodData copyFries = new FoodData(fries);
		copyFries.setQuantity(3);
		copyFries.setModifications("");
		
		mostSales.add(copyMilkshake);
		mostSales.add(copyFries);
		mostSales.add(copyHamburger);
		
		
		assertEquals(trackSales.sortMostSales(), mostSales);
		
		
	}
	
	@Test
	void testSortOffRevenue() {
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
		FoodData milkshake2 = new FoodData("Milkshake", FoodType.DRINK, 2.99, 50, "");
		FoodData fries2 = new FoodData("Fries", FoodType.SIDE, 4.99, 1, "No Salt");
		
		newBill2.addFoodItem(hamburger2);
		newBill2.addFoodItem(milkshake2);
		newBill2.addFoodItem(fries2);
		
		
		Sales trackSales = new Sales();
		
		trackSales.addCompletedOrder(newBill);
		trackSales.addCompletedOrder(newBill2);
		
		ArrayList<FoodData> mostRev = new ArrayList<>();
		FoodData copyHamburger = new FoodData(hamburger);
		copyHamburger.setQuantity(6);
		copyHamburger.setModifications("");

		FoodData copyMilkshake = new FoodData(milkshake);
		copyMilkshake.setQuantity(51);
		copyMilkshake.setModifications("");
		
		FoodData copyFries = new FoodData(fries);
		copyFries.setQuantity(2);
		copyFries.setModifications("");
		
		mostRev.add(copyFries);
		mostRev.add(copyHamburger);
		mostRev.add(copyMilkshake);
		
		assertEquals(trackSales.sortOffRevenue(), mostRev);
	}
	
	@Test
	void testSalesMenu() {
		Bill menuBill = new Bill();
		
		Food burgerMenu = menu.getItemFromMenu("bacon cheeseburger");
		FoodData burger = new FoodData(burgerMenu.getName(), burgerMenu.getType(), 
				burgerMenu.getPrice(), 3, "");
		
		Food teaMenu = menu.getItemFromMenu("iced tea");
		FoodData tea = new FoodData (teaMenu.getName(), teaMenu.getType(), teaMenu.getPrice(), 100, "");
		
		menuBill.addFoodItem(tea);
		menuBill.addFoodItem(burger);
		
		Sales trackSales = new Sales();
		
		trackSales.addCompletedOrder(menuBill);
		
		ArrayList<FoodData> mostRev = new ArrayList<>();
		mostRev.add(new FoodData(burger));
		mostRev.add(new FoodData(tea));
		
		
		assertEquals(mostRev, trackSales.sortOffRevenue());
		
		ArrayList<FoodData> mostSold = new ArrayList<>();
		mostSold.add(new FoodData(burger));
		mostSold.add(new FoodData(tea));
		
		assertEquals(mostSold, trackSales.sortMostSales());
	}
	

}
