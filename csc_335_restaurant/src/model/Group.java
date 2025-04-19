
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * The Group class is used to get information about any groups of customers
 * and allow the restaurant to easily manage and assign table to them. 
 * It has methods to allow get total bill, tip, add person to group, etc.
 */

public class Group {
	private int groupId;
	private ArrayList<Customer> members;
	
	/* The constructor method */
	public Group(int groupId, ArrayList<Customer> customers) {
		this.groupId = groupId;
		this.members = customers;
	}
	
	/* Copy constructor*/
	public Group(Group other) {
		this.groupId = other.groupId;
		this.members = other.members;
	}
	
	/* Return total amount of food and tip */
	public double getTotalBill() {
		double totalBill = 0;
		for (Customer customer: members) {
			totalBill += customer.getBillCost();
		}
		return totalBill;
	}
	
	/* Return total tip */
	public double getTotalTip() {
		double totalTip = 0;
		for (Customer customer: members) {
			totalTip += customer.getBill().getTip();
		}
		return totalTip;
	}
	
	public int getGroupId() {
		return groupId;
	}
	
	public int getGroupSize() {
		return members.size();
	}
	
	/*
	 * Gives a read-only view of this group’s customers.
	 * Callers can do session.orderFood(...) but can’t modify the list
	 * nor call any other Customer methods.
	 */
	public List<OrderFood> getOrderSessions() {
	  List<OrderFood> sessions = new ArrayList<>(members); // subtype polymorphism: upcasting
	  // …then wrap it so nobody can add/remove
	  return Collections.unmodifiableList(sessions);
	}
	
	public void addPerson(Customer newMember) {
		if (!members.contains(newMember)) { // only add if new member isn't already in the group
			members.add(newMember);
		}
	}
	
}
