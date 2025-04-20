/*
* This class is for the server object, which should contain what tables
* the server is assigned to, the amount made in tips, and number of people
* assigned to the server. The system can use the number of people per server
* to determine who to seat next.
*/

package model;

import java.util.ArrayList;

public class Server {
	private ArrayList<Integer> tableNums;
	private String name;
	private double tips;
	
	public Server(String name) {
		this.tableNums = new ArrayList<Integer>();
		this.name = name;
		this.tips = 0.0;
	}
	
	public void addTable(int tableNum) {
		if (!tableNums.contains(tableNum)) {
		    tableNums.add(tableNum);
		}
	}
	
	public void removeTable(int tableNum) {
		if (tableNums.contains(tableNum)) {
			tableNums.remove(Integer.valueOf(tableNum));
		}
	}
	
	public ArrayList<Integer> getTables() {
		return new ArrayList<Integer>(tableNums);
	}
	
	public String getName() {
		return name;
	}
	
	public double getTips() {
		return tips;
	}

	public void addTips(double tips) {
		if (tips > 0) this.tips += tips;
	}
	
	@Override
	public String toString() {
		String str = "Server: " + name;
		str += " has made $" + tips + " in tips.\n";
		return str;
	}
}
