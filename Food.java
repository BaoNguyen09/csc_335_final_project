/* 
 * This is an immutable class with the fields of name which is a String,
 * Type which is an Enum FoodType which is if the food is an entree,
 * appetizer, side, dessert or drink, Finally there is a double for price.
 * The class has getters for all the instance variables and a toString()
 * as well as overriden equals and hashcode
 */

package model;

import java.util.Objects;

public class Food {
	private String name;
	private FoodType TYPE;
	private double price;
	
	public Food(String name, FoodType type, double price) {
		this.name = name;
		this.TYPE = type;
		this.price = price;
	}
	
	// copy contructor for FoodData
	public Food(Food food) {
		this.name = food.name;
		this.TYPE = food.TYPE;
		this.price = food.price;
	}
	
	public String getName() {
		return this.name;
	}
	
	public FoodType getType() {
		return this.TYPE;
	}
	
	public double getPrice() {
		return this.price;
	}

	@Override
	public String toString() {
		return "Food [name=" + name + ", TYPE=" + TYPE + ", price=$" + price + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(TYPE, name, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Food other = (Food) obj;
		return TYPE == other.TYPE && Objects.equals(name, other.name)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price);
	}
}
