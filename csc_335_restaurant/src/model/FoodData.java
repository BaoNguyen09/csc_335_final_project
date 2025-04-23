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
	private Food food;
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

	public double getPrice() {
		return super.getPrice();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + Objects.hash(modifications, quantity);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FoodData other = (FoodData) obj;
		return Objects.equals(modifications, other.modifications) && quantity == other.quantity;
	}

	@Override
	public String toString() {
		return "FoodData [food=" + this.getName() + ", quantity=" + quantity + ", price: $" + quantity * this.getPrice() +
				"\nmodifications=" + modifications + "]";
	}
}
