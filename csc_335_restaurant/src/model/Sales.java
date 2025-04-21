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
	private HashMap<FoodData, Integer> sales;
	private HashMap<FoodData, Double> itemRevenue;
	
	// Constructor method
	public Sales() {
		sales = new HashMap<FoodData, Integer>();
		itemRevenue = new HashMap<FoodData, Double>();
	}
	
	
	/* This method adds all the food in bill to the hashmap by checking the FoodData
	 * and adding the quantity
	 *  */
	public void addCompletedOrder(Bill bill) {
		for (FoodData food: bill.getOrder()) {
			if(!(sales.keySet().contains(food))) {
				sales.put(food, food.getQuantity());
				continue;
			}
			sales.put(food, sales.get(food) + food.getQuantity());
		}
		updateRevenueMap();
	}
	
	// getter method for sales hashmap
	public HashMap<FoodData, Integer> getSales(){
		HashMap<FoodData, Integer> copySales = new HashMap<>(this.sales);
		return copySales;
	}
	
	/* This method is sorted based off the most Sales */
	public String  sortMostSales(){
		ArrayList<FoodData> mostSales = new ArrayList<>(sales.keySet());
		// this code sorts the arrayList by comparing the key based on the value in the
		// sales hashMap. comparing o2 to o1 makes the list descending order instead of ascending
		Collections.sort(mostSales, (o1,o2) -> sales.get(o2).compareTo(sales.get(o1)));
		String result = "Items with Most Sales: \n";
		for (FoodData item: mostSales) {
			result += item.getName() + " " + sales.get(item) + "\n";
		}
		return result;
	}
	
	/* This method is a helper method that updates the itemRevenue hashmap everytime
	 * a completed order is added   */
	private void updateRevenueMap() {
		for (FoodData item: sales.keySet()) {
			Double cost = item.getPrice() * sales.get(item);
			itemRevenue.put(item, cost);
		}
		
	}
	
	/* This method will sort the items based on how much money has been made off each
	 * (most money to least) */
	public String sortOffRevenue(){
		String result = "Money Made: \n";
		// ArrayList full of the keys in itemRevenue
		ArrayList<FoodData> sortRevenue = new ArrayList<>(itemRevenue.keySet());
		// sorts items based off the total revenue they have made in descending order
		Collections.sort(sortRevenue, (o1, o2) -> itemRevenue.get(o2).compareTo(itemRevenue.get(o1)));
		for (FoodData item: sortRevenue) {
			result += item.getName() + " $" + itemRevenue.get(item) + "\n";
		}
		return result;
	}
	
	@Override 
	public String toString() {
		String result = "Item Sales:\n";
		ArrayList<FoodData> sortedFood = new ArrayList<>(sales.keySet());
		Collections.sort(sortedFood, (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
		for (FoodData food: sortedFood) {
			result += food.getName() + ": " +  sales.get(food) + "\n";
		}
		return result;
	}
}
