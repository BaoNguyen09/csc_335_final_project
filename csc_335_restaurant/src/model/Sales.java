/*
 * The Sales class keeps track of how many times an item has been ordered using
 * a HashMap.
 * */
package model;

import java.util.*;


public class Sales {
	/* The sales HashMap keeps track of how many of each item is sold
	 * The itemRevenue HashMap keeps track of how many of each item is sold */
	private HashMap<FoodData, Integer> sales;
	private HashMap<FoodData, Double> itemRevenue;
	
	// Constructor method
	public Sales() {
		sales = new HashMap<FoodData, Integer>();
		itemRevenue = new HashMap<FoodData, Double>();
	}
	
	
	/* This method adds all the food in bill to the hashmap by checking the food name
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
		HashMap<FoodData, Integer> copySales = new HashMap<>();
		for (FoodData item : sales.keySet()) {
			FoodData copyItem = new FoodData(item);
			copySales.put(copyItem, sales.get(item));
		}
		return copySales;
	}
	
	/* This method is sorted based off the most Sales */
	public ArrayList<FoodData>  sortMostSales(){
		ArrayList<FoodData> mostSales = new ArrayList<>(sales.keySet());
		// this code sorts the arrayList by comparing the key based on the value in the
		// sales hashMap.
		Collections.sort(mostSales, (o1,o2) -> sales.get(o1).compareTo(sales.get(o2)));
		ArrayList<FoodData> returnList = new ArrayList<>();
		for (FoodData item: mostSales) {
			FoodData copyItem = new FoodData(item);
			copyItem.setQuantity(sales.get(item));
			returnList.add(copyItem);
		}
		return returnList;
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
	public ArrayList<FoodData> sortOffRevenue(){
		// ArrayList full of the keys in itemRevenue
		ArrayList<FoodData> sortRevenue = new ArrayList<>(itemRevenue.keySet());
		// sorts items based off the total revenue they have made in ascending order
		Collections.sort(sortRevenue, (o1, o2) -> itemRevenue.get(o1).compareTo(itemRevenue.get(o2)));
		ArrayList<FoodData> copyList = new ArrayList<>();
		for (FoodData item: sortRevenue) {
			FoodData copyItem = new FoodData(item);
			copyItem.setQuantity(sales.get(item));
			copyList.add(copyItem);
		}
		return copyList;
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
