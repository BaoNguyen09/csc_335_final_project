package model;

import java.util.Collections;
import java.util.List;

public class Table {
		private int tableNum;
		private int maxCapacity;
		private Group group;
		private boolean isOccupied;
		private String assignedServer;

		/*
	  	 * @pre tableNum != null maxCapacity != null
	  	 */
		public Table(int tableNum, int maxCapacity) {
			this.tableNum = tableNum;
			this.maxCapacity = maxCapacity;
			group = null;
			assignedServer = "";			
		}
		
		
		// Copy constructor
		/*
	  	 * @pre other != null
	  	 */
		public Table(Table other) {
			this.tableNum = other.tableNum;
			this.maxCapacity = other.maxCapacity;
			
			if (other.group != null) {
				this.group = new Group(other.group);

			} else {
				this.group = null;
			}
			
			this.isOccupied = other.isOccupied;
			this.assignedServer = other.assignedServer;
		}


		public int getTableNum() {
			return tableNum;
		}
		
		public int getMaxCapacity() {
			return maxCapacity;
		}
		
		public boolean isOccupied() {
			return (group != null);
		}
		
		
		public String getAssignedServerName() {
			return assignedServer;
		}
		
		public boolean isOrderTaken() {
		    return (group != null) && group.orderTaken();
		}
		
		
			

		/*
	  	 * @pre severName != null tableNum != null
	  	 */
		// Returns true if the server was successfully assigned, false otherwise
		public boolean assignServer (String serverName) {
			if (assignedServer == "") {
				assignedServer = serverName;
				return true;
			}
			return false;
		}
		
		/*
	  	 * @pre group != null
	  	 */
		// Returns true if the group was successfully assigned, false otherwise
		public boolean assignGroup (Group group) {
			if (!isOccupied & group.getGroupSize() <= maxCapacity) {
				this.group = new Group(group);
				isOccupied = true;
				return true;
			}
			return false;
			
		}
		
		public int getGroupId() {
			return group.getGroupId();
		}
		
		public void clearTable() {
			group = null;
			isOccupied = false;
			assignedServer = "";
		}

		
		
}
