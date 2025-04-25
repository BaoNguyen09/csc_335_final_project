package controller;

import model.Restaurant;

public class Controller {
	private Restaurant restaurant;
	
	public Controller(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	public void assignServer(String name, int tableNum) {
		restaurant.assignServerToTable(name, tableNum);
	}
	
	public void assignGroup(int groupNum, int tableNum) {
		restaurant.assignTable(groupNum, tableNum);
	}
	
	public void removeServer(String name, int tableNum) {
		restaurant.removeServerFromTable(name, tableNum);
	}
}
