package model;

import java.util.Collections;
import java.util.List;

public class Table {
		private int tableNum;
		private int maxCapacity;
		private Group group;
		private boolean isOccupied;
		private String assignedServer;

		public Table(int tableNum, int maxCapacity) {
			this.tableNum = tableNum;
			this.maxCapacity = maxCapacity;
			group = null;
			assignedServer = "";			
		}
		
		
		// Copy constructor
		public Table(Table other) {
			this.tableNum = other.tableNum;
			this.maxCapacity = other.maxCapacity;
			this.group = other.group;
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
		
		
//		public String closeOrder() {
//		    if (takenOrder) {
//		        double earnings = group.getTotalBill();
//		        double tips = group.getTotalTip();
//		        clearTable();
//		        return "Earnings: $" + String.format("%.2f", earnings) + " Tips: $" + String.format("%.2f", tips);
//		    }
//		    return "No Order";
//		}
			

		
		// Returns true if the server was successfully assigned, false otherwise
		public boolean assignServer (String serverName) {
			if (assignedServer == "") {
				assignedServer = serverName;
				return true;
			}
			return false;
		}
		
		
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
