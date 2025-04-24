package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * The Group class is used to get information about any groups of customers
 * and allow the restaurant to easily manage and assign table to them. 
 * It has methods to allow get total bill, tip, add person to group, etc.
 */

public class Group {
	private final int groupId; // groupId can only be assigned once when a new Group instance is created
	private static int nextGroupId = 1;
	private ArrayList<Customer> members;
	private Map<String, Customer> customerMap;
	private boolean onWaitlist;
	private boolean orderTaken;
	
	/* The constructor method */
	public Group() {
		this.groupId = nextGroupId;
		nextGroupId++;
		this.members = new ArrayList<Customer>();
		this.onWaitlist = true;
		orderTaken = false;
		customerMap = new HashMap<String, Customer>();
	}
	
	/* Copy constructor*/
	public Group(Group other) {
		this.groupId = other.groupId;
		// two line below won't work because it tries to access private variables
		// I put it there as reminder, if we end up needing to use this copy constructor
		// I will then implement methods to get this list and map
		this.members = other.members;
		this.customerMap = other.customerMap;
		this.orderTaken = other.orderTaken();
		this.onWaitlist = other.onWaitlist();
	}
	
	public void beingServed() {
		this.onWaitlist = false;
	}
	
	public boolean onWaitlist() {
		return onWaitlist;
	}
	
	public boolean orderTaken() {
		return orderTaken;
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
	
	public List<String> getCustomersName() {
		List<String> customers = new ArrayList<>();
		for (Customer c: members) {
			customers.add(c.getName());
		}
		return customers;
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
	
	/*
	 * This method will handle the ordering directly. Still need to discuss how to
	 * deal with customer with the same name (maybe add an id)
	 */
	public boolean placeOrder(String name, Food food, int qty, String mods) {
		Customer customer = customerMap.getOrDefault(name, null);
		if (customer != null) {
			orderTaken = true;
			return customer.orderFood(food, qty, mods);
		}
		return false;
	}
	
	/*
	 * This method will handle the paying bill directly.
	 */
	public boolean payBill(String name) {
		Customer customer = customerMap.getOrDefault(name, null);
		if (customer != null && customer.payBill()) {
			return true;
		}
		return false;
	}
	
	public Bill getCustomerBill(String name) {
		Customer customer = customerMap.getOrDefault(name, null);
		if (customer != null) {
			return new Bill(customer.getBill());
		}
		return null;
	}
	
	/*
	 * This method will handle the tipping directly.
	 */
	public void addTip(String name, double amount) {
		Customer customer = customerMap.getOrDefault(name, null);
		if (customer != null) {
			customer.tip(amount);
		}
	}
	
	public void addPerson(Customer newMember) {
		if (!members.contains(newMember)) { // only add if new member isn't already in the group
			members.add(newMember);
			customerMap.put(newMember.getName(), newMember);
		}
	}
	
	public void splitBillEvenly() {
		double totalBill = 0;
		for (Customer c: members) {
			totalBill += c.getBill().getFoodCost();
		}
		double splitAmount = totalBill / members.size();
		for (Customer c: members) {
			c.splitBill(splitAmount);
		}
	}
	
	public static void resetGroupIdCounter() {
	    nextGroupId = 1;
	}

}
