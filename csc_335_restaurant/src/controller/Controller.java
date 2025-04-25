package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.Restaurant;

public class Controller implements ActionListener{
	
	private Restaurant restaurant;

    public Controller(Restaurant restaurant) {
        this.restaurant = restaurant;
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
	
//	public void removeServer(String name, int tableNum) {
//		restaurant.removeServerFromTable(name, tableNum);
//	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
