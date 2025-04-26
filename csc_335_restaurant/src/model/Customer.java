/* 
 * This class represents the customer, they each have their own bill objects
 * so it is easier to split the bill as well as a way to tip, pay the bill,
 * order food and a few other getters
 */

package model;

import java.util.Objects;

public class Customer implements OrderFood{
	

	private String name;
	private Bill bill;
	
	/*
	 * @pre name != null
	 */
	public Customer(String name) {
		this.name = name;
		this.bill = new Bill();
	}
	
	/* Copy constructor
	 * 
	 * @pre other != null
	 */
	public Customer(Customer other) {
		name = other.getName();
		bill = other.getBill();
	}
	
	/* Sets tip for the customer 
	 * 
	 * @pre tip != null
	 */
	public void tip(double tip) {
		bill.setTip(tip);
	}
	
	public Bill getBill() {
		return new Bill(this.bill);
	}
	
	public boolean payBill() {
		// when bill is paid the reference is set to null
		boolean result = bill.payBill();
		return result;
	}
	/*
	 * Pass along method because restaurant only stores groups and not bills.
	 * 
	 * @pre amount != null
	 */
	public void splitBill(double amount) {
		bill.splitBill(amount);
	}
	
	public boolean isBillPaid() {
		return bill.isPaid();
	}
	
	public double getBillCost() {
		return bill.calculateBill();
	}
	
	public String getName() {
		return name;
	}
	
	/*
	 * @pre 
	 * food != null
	 * quantity != null
	 * mods != null
	 * 
	 */
	public boolean orderFood(Food food, int quantity, String mods) {
		/*
		 * the customer creates a foodData object when they order food which is then passed
		 * to the bill through this function
		 */
		FoodData foodData = new FoodData(food.getName(), food.getType(), food.getPrice(), quantity, mods);
		return bill.addFoodItem(foodData);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(bill, other.bill) && Objects.equals(name, other.name);
	}
	
	public String toString() {
		return "Name: " + this.name + ", " + this.bill.toString();
	}
	
}
