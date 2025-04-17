package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import model.Food;
import model.FoodType;

class FoodTests {

	@Test
	void testGetName() {
		Food food = new Food("Burger", FoodType.ENTREE, 8.49);
		assertEquals(food.getName(), "Burger");
	}
	
	@Test
	void testGetType() {
		Food food = new Food("Fries", FoodType.SIDE, 3.89);
		assertEquals(food.getType(), FoodType.SIDE);
	}
	
	@Test
	void testGetPrice() {
		Food food = new Food("Cheesecake", FoodType.DESSERT, 11.60);
		assertEquals(food.getPrice(), 11.60);
	}
	
	@Test
	void testToString() {
		Food food = new Food("Bread", FoodType.APPETIZER, 4.35);
		assertEquals(food.toString(), "Food [name=Bread, TYPE=APPETIZER, price=$4.35]");
	}
	
	@Test
	void testEqualsSame() {
		Food food = new Food("Bread", FoodType.APPETIZER, 4.35);
		assertTrue(food.equals(food));
	}
	
	@Test
	void testEqualsNull() {
		Food food = new Food("Bread", FoodType.APPETIZER, 4.35);
		assertFalse(food.equals(null));
	}
	
	@Test
	void testEqualsClass() {
		Food food = new Food("Bread", FoodType.APPETIZER, 4.35);
		assertFalse(food.equals(""));
	}
	
	@Test
	void testEqualsName() {
		Food food = new Food("Spaghetti", FoodType.ENTREE, 8.49);
		Food food2 = new Food("Burger", FoodType.ENTREE, 8.49);
		assertFalse(food.equals(food2));
	}
	
	@Test
	void testEqualsPrice() {
		Food food = new Food("Burger", FoodType.ENTREE, 4.35);
		Food food2 = new Food("Burger", FoodType.ENTREE, 8.49);
		assertFalse(food.equals(food2));
	}
	
	@Test
	void testEqualsFoodType() {
		Food food = new Food("Bread", FoodType.APPETIZER, 4.35);
		Food food2 = new Food("Burger", FoodType.ENTREE, 8.49);
		assertFalse(food.equals(food2));
	}
	
	@Test
	void testHashCode() {
		int hash = Objects.hash(FoodType.APPETIZER, "Bread", 4.35);
		Food food = new Food("Bread", FoodType.APPETIZER, 4.35);
		assertEquals(food.hashCode(), hash);
	}
}
