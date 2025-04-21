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
	private ArrayList<FoodData> order;
	
	/* The constructor method */
	public Bill() {
		order = new ArrayList<FoodData>();
		tip = 0;
		foodCost = 0;
	}
	
	/* Copy constructor*/
	public Bill(Bill other) {
		this.foodCost = other.foodCost;
		this.tip = other.tip;
		ArrayList<FoodData> copyOrder = new ArrayList<>(other.order);
		this.order = copyOrder;
	}

	
	/* This method adds a foodItem to order, it creates a new foodItem and adds it and each time
	 * this method runs it updates foodCost */
	public void addFoodItem(FoodData item) {
		order.add(item);
		calculateFoodCost();
	}
	
	/* Sets the tip */ 
	public void setTip(double money) {
		this.tip = money;
	}
	
	/* This method calculates the cost of only the food */
	private void calculateFoodCost() {
		double cost = 0;
		for (FoodData item: order) {
			cost += item.getPrice();
		}
		this.foodCost = cost;

	/* Get the tip */
	public double getTip() {
		return tip;

	}
	
	/* This method returns the price of the bill which includes the price of the food
	 * plus the tip*/
	public double calculateBill() {
		return foodCost + tip;
	}
	
	public ArrayList<FoodData> getOrder(){
		ArrayList<FoodData> copyOrder = new ArrayList<>();
                for (FoodData fd : this.order) {
                    copyOrder.add(new FoodData(fd));  // new instance each time
                }
		return copyOrder;
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
