/*
 * This class is for the server object, which shoulc contain what tables
 * the server is assigned to, the amount made in tips, and number of people
 * assigned to the server. The system can use the number of people per server
 * to determine who to seat next.
 */

import java.util.ArrayList;

public class Server {
	// private ArrayList<Table> tables;
	private String name;
	private int num_seated;
	private double tips;
	
	public Server(String name) {
		//tables = new ArrayList<Table>();
		this.name = name;
		num_seated = 0;
		tips = 0.0;
	}
	
	/*
	public void seatTable(Tabel t) {
		// when every table is sat num_seated is updated to add the num of seats from that table
		tables.add(t);
		num_seated += t.getSeats();
	}
	
	public Table popTable(Table t) {
		// when every table is popped num_seated is updated to remove the num of seats from that table
		num_seated -= t.getSeats();
		return tables.remove(t);
	}
	*/
	public String getName() {
		return name;
	}
	
	public void addTips(double tip) {
		tips += tip;
	}
	
	public double getTotalTips() {
		return tips;
	}
	
	public int getSection() {
		// retuns all the people seated in this servers section
		return num_seated;
	}
	
	@Override
	public String toString() {
		String str = "Server: ";
		str += name + " has: " + num_seated;
		str += " people sat in their section, and has made $" + tips + " in tips.\n";
		return str;
	}
}
