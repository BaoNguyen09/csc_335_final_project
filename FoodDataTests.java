package test;

import static org.junit.Assert.*;

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
		Food food = new Food("Burger", FoodType.ENTREE, 8.49);
		FoodData foodData = new FoodData(food, 2, "None");
		assertEquals(foodData.getQuantity(), 2);
	}
	
	@Test
	void testGetModifications() {
		Food food = new Food("Burger", FoodType.ENTREE, 8.49);
		FoodData foodData = new FoodData(food, 2, "None");
		assertEquals(foodData.getModifications(), "None");
	}
	
	@Test
	void testGetFood() {
		Food food = new Food("Burger", FoodType.ENTREE, 8.49);
		FoodData foodData = new FoodData(food, 2, "None");
		assertEquals(foodData.getFood(), food);
	}
	
	@Test
	void testsetQuantity() {
		Food food = new Food("Burger", FoodType.ENTREE, 8.49);
		FoodData foodData = new FoodData(food, 2, "None");
		foodData.setQuantity(1);
		assertEquals(foodData.getQuantity(), 1);
	}
	
	@Test
	void testSetModifications() {
		Food food = new Food("Burger", FoodType.ENTREE, 8.49);
		FoodData foodData = new FoodData(food, 2, "None");
		foodData.setModifications("Add Cheese");
		assertEquals(foodData.getModifications(), "Add Cheese");
	}
	
	@Test
	void testGetPrice() {
		Food food = new Food("Burger", FoodType.ENTREE, 8.49);
		FoodData foodData = new FoodData(food, 2, "None");
		assertEquals(foodData.getPrice(), 16.98);
	}
	
	@Test
	void testToString() {
		Food food = new Food("Burger", FoodType.ENTREE, 8.49);
		FoodData foodData = new FoodData(food, 2, "None");
		assertEquals(foodData.toString(), "FoodData [food=Burger, quantity=2, price: $16.98\nmodifications=None]");
	}
	
	@Test
	void testHashCode() {
		Food food = new Food("Burger", FoodType.ENTREE, 8.49);
		FoodData foodData = new FoodData(food, 2, "None");
		int hash = 31 * food.hashCode() + Objects.hash(food, "None", 2);
		assertEquals(foodData.hashCode(), hash);
	}
	
	@Test
	void testEqualsSame() {
		Food food = new Food("Bread", FoodType.APPETIZER, 4.35);
		FoodData foodData = new FoodData(food, 2, "None");
		assertTrue(foodData.equals(foodData));
	}

	@Test
	void testEqualsClass() {
		Food food = new Food("Bread", FoodType.APPETIZER, 4.35);
		FoodData foodData = new FoodData(food, 2, "None");
		assertFalse(foodData.equals(food));
	}
	
	@Test
	void testEqualsFood() {
		Food food = new Food("Bread", FoodType.APPETIZER, 4.35);
		Food food2 = new Food("Burger", FoodType.ENTREE, 8.49);
		FoodData foodData = new FoodData(food, 2, "add butter");
		FoodData foodData2 = new FoodData(food2, 2, "None");
		assertFalse(foodData.equals(foodData2));
	}
	
	@Test
	void testEqualsAll() {
		Food food = new Food("Bread", FoodType.APPETIZER, 4.35);
		FoodData foodData = new FoodData(food, 2, "None");
		FoodData foodData2 = new FoodData(food, 2, "None");
		assertTrue(foodData.equals(foodData2));
	}
	
	@Test
	void testEqualsQuantity() {
		Food food = new Food("Bread", FoodType.APPETIZER, 4.35);
		FoodData foodData = new FoodData(food, 1, "None");
		Food food2 = new Food("Bread", FoodType.APPETIZER, 4.35);
		FoodData foodData2 = new FoodData(food2, 2, "None");
		assertFalse(foodData.equals(foodData2));
	}
	
	@Test
	void testEqualsModifications() {
		Food food = new Food("Bread", FoodType.APPETIZER, 4.35);
		FoodData foodData = new FoodData(food, 2, "add butter");
		Food food2 = new Food("Bread", FoodType.APPETIZER, 4.35);
		FoodData foodData2 = new FoodData(food2, 2, "None");
		assertFalse(foodData.equals(foodData2));
	}
}
