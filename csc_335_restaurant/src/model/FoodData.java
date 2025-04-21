/* 
 * This is a mutable class that extends the Food object, it calls the super
 * copy contructor to create the food object it is. As well as setting
 * variables for the quantity of this object and any modifications it holds.
 * it has getters and setters as well as an overriden getPrice() method that
 * returns the price based on the quantity. It also has a toString() method
 * and overriden hashcode and equals
 */

package model;

import java.util.Objects;

public class FoodData extends Food{
	private int quantity;
	private String modifications;
	
	public FoodData(String name, FoodType type, double price, int quantity, String modifications) {
		super(name, type, price);
		this.quantity = quantity;
		this.modifications = modifications;
	}
	
	//copy constructor for FoodData
	public FoodData(FoodData food) {
		super(food.getName(), food.getType(), food.getPrice());
		this.quantity = food.getQuantity();
		this.modifications = food.getModifications();
	}

	public int getQuantity() {
		return this.quantity;
	}

	public String getModifications() {
		return this.modifications;
	}


	public void setQuantity(int q) {
		this.quantity = q;
	}

	public void setModifications(String m) {
		this.modifications = m;
	}

	// need to override getPrice 
	@Override
	public double getPrice() {
		return quantity * super.getPrice();
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "FoodData [food=" + this.getName() + ", quantity=" + quantity + ", price: $" + this.getPrice() +
				"\nmodifications=" + modifications + "]";
	}
}
