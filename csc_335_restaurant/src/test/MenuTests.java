package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Food;
import model.FoodType;
import model.Menu;

class MenuTests {

	Menu menu = new Menu();
	
	@Test
	void testGettingSingleFoodIten() {
		Food burger = new Food ("Bacon Cheeseburger", FoodType.ENTREE, 13.99);
		Food burgerFromMenu = menu.getItemFromMenu("bacon cheeseburger");
		assertEquals(burger, burgerFromMenu);
	}
	
	@Test
	void testGettingSingleDrinkItem() {
		Food tea = new Food ("Iced Tea", FoodType.DRINK, 1.99);
		Food teaFromMenu = menu.getItemFromMenu("iced tea");
		assertEquals(tea, teaFromMenu);
	}
	
	@Test
	void testGettingAllMenuItems() {
		ArrayList<Food> allMenuItems = menu.getMenuItems();
		assertEquals(47, allMenuItems.size());
	}
	
	@Test
	void testNullWhenNoFoodItemOfThatName() {
		assertEquals(null, menu.getItemFromMenu("doesn't exist"));
	}

}
