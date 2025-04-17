package model;

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
		
		
		public boolean takeOrder() {
			if (!takenOrder) {
				group.takeGroupOrder();
				takenOrder = true;
				return true;
			}
			return false;
			
		}
		
		public boolean closeOrder() {
			if (takenOrder) {
				earnings = group.getTotalBills();
				tip = group.getTotalTip();
				assignedServer.addTips(tip);
				clearTable();
				return true;
			}
			return false;
			
		}
		
		// Question: do we want to assign a server to a table or to a group?
		// We assign group to a table so 
		
		// Returns true if the server was successfully assigned, false otherwise
		public boolean setServer (Server server) {
			if (assignedServer == null) {
				assignedServer = server;
				return true;
			}
			return false;
		}
		
		public void removeServer() {
			assignedServer = null;
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
