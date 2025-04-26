package model;

import java.util.ArrayList;
import java.util.Objects;

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


	/* Copy constructor*/
	public Bill(Bill other) {
		this.foodCost = other.foodCost;
		this.tip = other.tip;
		isPaid = other.isPaid;
		amountPaid = other.amountPaid;
		ArrayList<FoodData> copyOrder = new ArrayList<>(other.order);
		this.order = copyOrder;
	}

	
	/* This method adds a foodItem to order, it creates a new foodItem and adds it and each time
	 * this method runs it updates foodCost */
	public boolean addFoodItem(FoodData item) {
		if (order.add(item)) {
			this.foodCost += item.getTotalPrice();
			return true;
		}
		return false;
	}
	
	/* Sets the tip */ 
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
	
	public void splitBill(double amount) {
		amountPaid = amount;
		isPaid = true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bill other = (Bill) obj;
		return Double.doubleToLongBits(amountPaid) == Double.doubleToLongBits(other.amountPaid)
				&& Double.doubleToLongBits(foodCost) == Double.doubleToLongBits(other.foodCost)
				&& isPaid == other.isPaid && Objects.equals(order, other.order)
				&& Double.doubleToLongBits(tip) == Double.doubleToLongBits(other.tip);
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


