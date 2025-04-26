package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import model.*;

public class Controller implements ActionListener{
	
	private Restaurant restaurant;

    public Controller(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    
    public Map<Integer, Group> getActiveGroups() {
    	return restaurant.getActiveGroups();
    }
    
    public Menu getMenu() {
    	return restaurant.getMenu();
    }
    
    public Sales getSalesObject() {
    	return restaurant.getSalesObject();
    }
    
    public Map<Food, Integer> getSales() {
    	return restaurant.getSales();
    }
    
    public Server getTopTipEarner() {
    	return restaurant.getTopTipEarner();
    }

    public void handleAddGroup(ArrayList<String> memberNames) {
        restaurant.addGroup(memberNames);
    }

    public void addServer(String serverName) {
		restaurant.addServer(serverName);
	}

	public void assignServer(String name, int tableNum) {
		restaurant.assignServerToTable(name, tableNum);
	}
	
	public void assignGroup(int groupNum, int tableNum) {
		restaurant.assignTable(groupNum, tableNum);
	}
	
	public boolean splitAndPayBillEvenly(int groupId) {
		return restaurant.splitAndPayBillEvenly(groupId);
	}
	
	public boolean orderFoodFor(int groupId, String name, Food f, int qty, String mods) {
		return restaurant.orderFoodFor(groupId, name, f, qty, mods);
	}
	
	public boolean payBillFor(int groupId, String name) {
		return restaurant.payBillFor(groupId, name);
	}
	
	public boolean addTipFor(int groupId, String name, double amount) {
		return restaurant.addTipFor(groupId, name, amount);
	}
	
	public String getPaymentSummary(int groupId) {
		return restaurant.getPaymentSummary(groupId);
	}
	
	public boolean closeGroupOrder(int tableNum) {
		return restaurant.closeGroupOrder(tableNum);
	}
	
	public void registerSalesObserver(SalesObserver saleUI) {
		restaurant.registerSalesObserver(saleUI);
	}
	
//	public void removeServer(String name, int tableNum) {
//		restaurant.removeServerFromTable(name, tableNum);
//	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
