package model;

import java.util.Collections;
import java.util.List;

public class Table {
		private int tableNum;
		private int maxCapacity;
		private Group group;
		private boolean isOccupied;
		private boolean takenOrder;
		private Server assignedServer;

		public Table(int tableNum, int maxCapacity) {
			this.tableNum = tableNum;
			this.maxCapacity = maxCapacity;
			group = null;
			isOccupied = false;
			assignedServer = null;
			takenOrder = false;
			
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
		
		public boolean hastakenOrder() {
			return takenOrder;
		}
		
		public String getAssignedServerName() {
			if (assignedServer != null) {
				return assignedServer.getName();
			}
			return "No Server";
		}
		
		
		/* The following will return the orderSessions of the group ig their order
		 * has not been taken yet else it will return null.
		 * 
		 * How to take orders inside the view: 
		 * List<OrderFood> sessions = table.takeOrder();
				for (OrderFood personSession: sessions) {
					personSession.orderFood(Food food, int quantity, String mods);
					
				}
		 */
		public List<OrderFood> takeOrder() {
			// Can only take order if haven't taken order before or a group is at the table
			if (!takenOrder & group != null) {
				takenOrder = true;
				return group.getOrderSessions();
			}
			return Collections.emptyList();
			
		}
		
		public String closeOrder() {
		    if (takenOrder) {
		        double earnings = group.getTotalBill();
		        double tips = group.getTotalTip();
		        assignedServer.addTips(tips);
		        clearTable();
		        return "Earnings: $" + String.format("%.2f", earnings) + " Tips: $" + String.format("%.2f", tips);
		    }
		    return "No Order";
		}
			

		
		// Returns true if the server was successfully assigned, false otherwise
		public boolean assignServer (Server server) {
			if (assignedServer == null) {
				assignedServer = server;
				assignedServer.addTable(tableNum);
				return true;
			}
			return false;
		}
		
		public void removeServer() {
			if (assignedServer != null) {
				assignedServer.removeTable(tableNum);
				assignedServer = null;
			}
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
		
		public void clearTable() {
			group = null;
			isOccupied = false;
			takenOrder = false;
		}

		
		
}
