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
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


}
