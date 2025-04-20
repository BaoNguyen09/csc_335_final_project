/*
* This class is for the server object, which should contain what tables
* the server is assigned to, the amount made in tips, and number of people
* assigned to the server. The system can use the number of people per server
* to determine who to seat next.
*/

package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Server {
	private ArrayList<Integer> tables;
	private String name;
	private double tips;
	
	public Server(String name) {
		this.tables = new ArrayList<Integer>();
		this.name = name;
		this.tips = 0.0;
	}
	
	public void addTable(int table) {
		tables.add(table);
	}
	
	public void removeTable(int table) {
		tables.remove(table);
	}
	
	public ArrayList<Integer> getTables() {
		return new ArrayList<Integer>(tables);
	}
	
	public String getName() {
		return name;
	}
	
	public double getTips() {
		return tips;
	}
	
	public void addTips(double tips) {
		this.tips += tips;
	}
	
	@Override
	public String toString() {
		String str = "Server: " + name;
		str += " has made $" + tips + " in tips.\n";
		return str;
	}
}