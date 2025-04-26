/*
 * The Sales class keeps track of how many times an item has been ordered using
 * a HashMap.
 * */
package model;

import java.util.*;


public class Sales {
	/* The sales HashMap keeps track of how many of each item is sold
	 * The itemRevenue HashMap keeps track of how many of each item is sold */
	private HashMap<Food, Integer> sales;
	private HashMap<Food, Double> itemRevenue;
	private List<SalesObserver> observers = new ArrayList<>();

	
	// Constructor method
	public Sales() {
		sales = new HashMap<Food, Integer>();
		itemRevenue = new HashMap<Food, Double>();
	}
	
	public void addObserver(SalesObserver observer) {
	    observers.add(observer);
	}
	
	private void notifyObservers() {
	    for (SalesObserver observer : observers) {
	        observer.onSaleUpdate(0);
	    }
	}
	
	public Sales(Sales other) {
		sales = new HashMap<Food, Integer>(other.sales);
		itemRevenue = new HashMap<Food, Double>(other.itemRevenue);
	}
	
	/* This method adds all the food in bill to the hashmap by checking the food name
	 * and adding the quantity
	 *  */
	public void addCompletedOrder(Bill bill) {
		for (FoodData food: bill.getOrder()) {
			Food copyFood = new Food(food.getName(), food.getType(), food.getPrice());
			if(!(sales.keySet().contains(copyFood))) {
				sales.put(copyFood, food.getQuantity());
				continue;
			}
			sales.put(copyFood, sales.get(copyFood) + food.getQuantity());
		}
		updateRevenueMap();
		
		// Will notify the salesUI that a new sales
		// has been made
		notifyObservers();
	}
	
	// getter method for sales hashmap
	public HashMap<Food, Integer> getSales(){
		HashMap<Food, Integer> copySales = new HashMap<>();
		for (Food item : sales.keySet()) {
			Food copyItem = new Food(item);
			copySales.put(copyItem, sales.get(item));
		}
		return copySales;
	}
	
	/* This method is sorted based off the most Sales most to least*/
	public ArrayList<FoodData>  sortMostSalesDescending(){
		ArrayList<Food> mostSales = new ArrayList<>(sales.keySet());
		// this code sorts the arrayList by comparing the key based on the value in the
		// sales hashMap.
		Collections.sort(mostSales, (o1,o2) -> sales.get(o1).compareTo(sales.get(o2)));
		ArrayList<FoodData> returnList = new ArrayList<>();
		for (Food item: mostSales) {
			FoodData copyItem = new FoodData(item.getName(), item.getType(), 
					item.getPrice(), sales.get(item), "");
			returnList.add(copyItem);
		}
		return returnList;
	}
	
	/* This method is sorted based off the most Sales least to most*/
	public ArrayList<FoodData>  sortMostSalesAscending(){
		ArrayList<Food> mostSales = new ArrayList<>(sales.keySet());
		// this code sorts the arrayList by comparing the key based on the value in the
		// sales hashMap.
		Collections.sort(mostSales, (o1,o2) -> sales.get(o2).compareTo(sales.get(o1)));
		ArrayList<FoodData> returnList = new ArrayList<>();
		for (Food item: mostSales) {
			FoodData copyItem = new FoodData(item.getName(), item.getType(), 
					item.getPrice(), sales.get(item), "");
			returnList.add(copyItem);
		}
		return returnList;
	}
	
	/* This method is a helper method that updates the itemRevenue hashmap everytime
	 * a completed order is added   */
	private void updateRevenueMap() {
		for (Food item: sales.keySet()) {
			Double cost = item.getPrice() * sales.get(item);
			itemRevenue.put(item, cost);
		}
		
	}
	
	/* This method will sort the items based on how much money has been made off each
	 * (most money to least) */
	public ArrayList<FoodData> sortOffRevenueDescending(){
		// ArrayList full of the keys in itemRevenue
		ArrayList<Food> sortRevenue = new ArrayList<>(itemRevenue.keySet());
		// sorts items based off the total revenue they have made in ascending order
		Collections.sort(sortRevenue, (o1, o2) -> itemRevenue.get(o1).compareTo(itemRevenue.get(o2)));
		ArrayList<FoodData> copyList = new ArrayList<>();
		for (Food item: sortRevenue) {
			FoodData copyItem = new FoodData(item.getName(), item.getType(), 
					item.getPrice(), sales.get(item), "");
			copyList.add(copyItem);
		}
		return copyList;
	}
	
	/* This method will sort the items based on how much money has been made off each
	 * (least money to most) */
	public ArrayList<FoodData> sortOffRevenueAscending(){
		// ArrayList full of the keys in itemRevenue
		ArrayList<Food> sortRevenue = new ArrayList<>(itemRevenue.keySet());
		// sorts items based off the total revenue they have made in ascending order
		Collections.sort(sortRevenue, (o1, o2) -> itemRevenue.get(o2).compareTo(itemRevenue.get(o1)));
		ArrayList<FoodData> copyList = new ArrayList<>();
		for (Food item: sortRevenue) {
			FoodData copyItem = new FoodData(item.getName(), item.getType(), 
					item.getPrice(), sales.get(item), "");
			copyList.add(copyItem);
		}
		return copyList;
	}
	
	@Override 
	public String toString() {
		String result = "Item Sales:\n";
		ArrayList<Food> sortedFood = new ArrayList<>(sales.keySet());
		Collections.sort(sortedFood, (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
		for (Food food: sortedFood) {
			result += food.getName() + ": " +  sales.get(food) + "\n";
		}
		return result;
	}
}