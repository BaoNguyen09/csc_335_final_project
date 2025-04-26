package model;

import java.util.ArrayList;

/*
 * The Bill class is used to hold information on individual orders. 
 * Each person has their own bill and this contains everything a person orders
 * and how much tip they would like to add.
 */

public class Bill {
	private double foodCost;
	private double tip;
	private double amountPaid;
	private ArrayList<FoodData> order;
	private boolean isPaid;
	
	/* The constructor method */
	public Bill() {
		order = new ArrayList<FoodData>();
		tip = 0;
		foodCost = 0;
		isPaid = false;
		amountPaid = 0;
	}
	

	/* Copy constructor
	 * 
	 * @pre other != null
	 * */
	public Bill(Bill other) {
		this.foodCost = other.foodCost;
		this.tip = other.tip;
		isPaid = other.isPaid;
		amountPaid = other.amountPaid;
		ArrayList<FoodData> copyOrder = new ArrayList<>(other.order);
		this.order = copyOrder;
	}

	
	/* This method adds a foodItem to order, it creates a new foodItem and adds it and each time
	 * this method runs it updates foodCost 
	 * 
	 * @pre item != null
	 * 
	 * */
	public boolean addFoodItem(FoodData item) {
		if (order.add(item)) {
			this.foodCost += item.getTotalPrice();
			return true;
		}
		return false;
	}
	
	/* Sets the tip 
	 * @pre money != null
	 * */ 
	public void setTip(double money) {
		this.tip = money;
	}
	
	/* Get the tip */
	public double getTip() {
		return tip;
	}
	
	public double getAmountPaid() {
		return amountPaid;
	}
	
	public double getFoodCost() {
		return foodCost;
	}
	
	/* This method returns the price of the bill which includes the price of the food
	 * plus the tip*/
	public double calculateBill() {
		return foodCost + tip;
	}
	
	public ArrayList<FoodData> getOrder(){
		ArrayList<FoodData> copyOrder = new ArrayList<>();
		for (FoodData item: order) {
			FoodData copyFood = new FoodData(item);
			copyOrder.add(copyFood);
		}
		return copyOrder;
	}
	
	public boolean isPaid() {
		return isPaid;
	}
	
	public boolean payBill() {
		if (calculateBill() >= 0) {
			amountPaid = calculateBill();
			isPaid = true;
			return true;
		}
		return false;
	}
	
	/* This will set the amount of the customer bill to
	 * be evenly split among all customers in the group.
	 * 
	 * @pre amount != null
	 * 
	 */
	public void splitBill(double amount) {
		amountPaid = amount;
		isPaid = true;
	}
	
	@Override
	public String toString() {
		String result = "Order: ";
		for (FoodData item: order) {
			result += item.toString() + "\n";
		}
		result += "Tip: $" + tip + "\n";
		result += "Total Cost: $" + (foodCost + tip) + "\n";
		return result;
	}
	
}


