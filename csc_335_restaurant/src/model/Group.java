
package model;

import java.util.ArrayList;

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
	
}
