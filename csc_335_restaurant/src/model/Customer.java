/* 
 * This class represents the customer, they each have their own bill objects
 * so it is easier to split the bill as well as a way to tip, pay the bill,
 * order food and a few other getters
 */

package model;

public class Customer {
	private String name;
	private Bill bill;
	
	public Customer(String name) {
		this.name = name;
		this.bill = new Bill();
	}
	
	public void tip(double tip) {
		bill.setTip(tip);
	}
	
	public Bill getBill() {
		return new Bill(this.bill);
	}
	
	public void payBill() {
		// when bill is paid the reference is set to null
		bill = null;
	}
	
	public boolean isBillPaid() {
		return bill == null;
	}
	
	public double getBillCost() {
		return bill.calculateBill();
	}
	
	public String getName() {
		return name;
	}
	
	public void orderFood(Food food, int quantity, String mods) {
		/*
		 * the customer creates a foodData object when they order food which is then passed
		 * to the bill through this function
		 */
		FoodData foodData = new FoodData(food.getName(), food.getType(), food.getPrice(), quantity, mods);
		bill.addFoodItem(foodData);
	}
	
	public String toString() {
		return "Name: " + this.name + ", " + this.bill.toString();
	}
}
