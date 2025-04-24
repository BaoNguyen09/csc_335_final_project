package controller;

import java.util.ArrayList;
import java.util.List;

import model.Restaurant;

public class Controller {
	private Restaurant restaurant;
	
	public Controller(Restaurant restaurant) {
		this.restaurant = restaurant;
		restaurant.addServer("Alice");
		restaurant.addServer("Bob");
		restaurant.addServer("Charlie");
		List<String> l1 = new ArrayList<>();
		l1.add("Bill");
		l1.add("Allie");
		l1.add("George");
		List<String> l2 = new ArrayList<>();
		l2.add("Chris");
		l2.add("Tyler");
		List<String> l3 = new ArrayList<>();
		l3.add("Sienna");
		l3.add("Julia");
		l3.add("Lili");
		l3.add("John");
		restaurant.addGroup(l1);
		restaurant.addGroup(l2);
		restaurant.addGroup(l3);
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
