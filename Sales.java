/*
 * The Sales class keeps track of how many times an item has been ordered using
 * a HashMap.
 * */
package model;

import java.util.*;


public class Sales {
	/* I made sales HashMap String, Integer instead of food, integer because changing
	 * using food as the key would require changing the hashcode, and equals method
	 * in FoodData so contains is able to work. Possible to change if we absolutely need to though.
	 * */
	private HashMap<String, Integer> sales;
	
	// Constructor method
	public Sales() {
		sales = new HashMap<String, Integer>();
	}
	
	
	/* This method adds all the food in bill to the hashmap by checking the food name
	 * and adding the quantity
	 *  */
	public void addCompletedOrder(Bill bill) {
		for (FoodData food: bill.getOrder()) {
			if(!(sales.keySet().contains(food.getName()))) {
				sales.put(food.getName(), food.getQuantity());
				continue;
			}
			sales.put(food.getName(), sales.get(food.getName()) + food.getQuantity());
		}
	}
	
	// getter method for sales hashmap
	public HashMap<String, Integer> getSales(){
		HashMap<String, Integer> copySales = new HashMap<>(this.sales);
		return copySales;
	}
	
	@Override 
	public String toString() {
		String result = "Item Sales:\n";
		ArrayList<String> sortedFood = new ArrayList<>(sales.keySet());
		Collections.sort(sortedFood);
		for (String food: sortedFood) {
			result += food + ": " +  sales.get(food) + "\n";
		}
		return result;
	}
}
