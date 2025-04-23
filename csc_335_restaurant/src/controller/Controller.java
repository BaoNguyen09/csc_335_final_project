package controller;

import model.Restaurant;

public class Controller {
	private Restaurant restaurant;
	
	public Controller(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	public void assignServer(String name, int tableNum) {
		restaurant.assignServer(name, tableNum);
	}
	
	public void assignGroup(int groupNum, int tableNum) {
		restaurant.assignGroup(groupNum, tableNum);
	}
	
	public void removeServer(String name, int tableNum) {
		restaurant.removeServer(name, tableNum);
	}
}
