package test;

import static org.junit.jupiter.api.Assertions.*;


import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import model.Food;
import model.FoodData;
import model.FoodType;

public class FoodDataTests {

	@Test
	void testGetQuantity() {
		FoodData foodData = new FoodData("Burger", FoodType.ENTREE, 8.49, 2, "None");
		assertEquals(foodData.getQuantity(), 2);
	}

	@Test
	void testGetModifications() {
		FoodData foodData = new FoodData("Burger", FoodType.ENTREE, 8.49, 2, "None");
		assertEquals(foodData.getModifications(), "None");
	}

	@Test
	void testsetQuantity() {
		FoodData foodData = new FoodData("Burger", FoodType.ENTREE, 8.49, 2, "None");
		foodData.setQuantity(1);
		assertEquals(foodData.getQuantity(), 1);
	}

	@Test
	void testSetModifications() {
		FoodData foodData = new FoodData("Burger", FoodType.ENTREE, 8.49, 2, "None");
		foodData.setModifications("Add Cheese");
		assertEquals(foodData.getModifications(), "Add Cheese");
	}

	@Test
	void testGetPrice() {
		FoodData foodData = new FoodData("Burger", FoodType.ENTREE, 8.49, 2, "None");
		assertEquals(foodData.getPrice(), 8.49);
		assertEquals(foodData.getTotalPrice(), (8.49 * 2));
	}

	@Test
	void testToString() {
		FoodData foodData = new FoodData("Burger", FoodType.ENTREE, 8.49, 2, "None");
		assertEquals(foodData.toString(), "FoodData [food=Burger, quantity=2, price: $16.98\nmodifications=None]");
	}

	@Test
	void testHashCode() {
		FoodData foodData = new FoodData("Burger", FoodType.ENTREE, 8.49, 2, "None");
		FoodData foodData1 = new FoodData("Burger", FoodType.ENTREE, 8.49, 2, "None");

		assertEquals(foodData.hashCode(), foodData1.hashCode());
	}

	@Test
	void testEqualsSame() {
		FoodData foodData = new FoodData("Bread", FoodType.APPETIZER, 4.35, 2, "None");
		assertTrue(foodData.equals(foodData));
	}

	@Test
	void testEqualsClass() {
		Food food = new Food("Bread", FoodType.APPETIZER, 4.35);
		FoodData foodData = new FoodData("Bread", FoodType.APPETIZER, 4.35, 2, "None");
		assertFalse(foodData.equals(food));
	}

	@Test
	void testEqualsFood() {
		FoodData foodData = new FoodData("Bread", FoodType.APPETIZER, 4.35, 2, "add butter");
		FoodData foodData2 = new FoodData("Burger", FoodType.ENTREE, 8.49, 2, "None");
		assertFalse(foodData.equals(foodData2));
	}

	@Test
	void testEqualsAll() {
		FoodData foodData = new FoodData("Bread", FoodType.APPETIZER, 4.35, 2, "None");
		FoodData foodData2 = new FoodData("Bread", FoodType.APPETIZER, 4.35, 2, "None");
		assertTrue(foodData.equals(foodData2));
	}
	
	@Test
	void testCopyConstructor() {
		FoodData foodData = new FoodData("Bread", FoodType.APPETIZER, 4.35, 2, "None");
		FoodData copyFood = new FoodData(foodData);
		
		assertFalse(copyFood == foodData);
		assertTrue(foodData.equals(copyFood));
	}

}
