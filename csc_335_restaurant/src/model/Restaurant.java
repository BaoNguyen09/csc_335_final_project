package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observer;

public class Restaurant {
	// Core components of the restaurant
    private Map<String, Server> serverMap; // Fast table lookup by server name (no duplicates allow)
    private Map<Integer, Table> tableMap; // Fast table lookup by table number
    private Map<Integer, Group> waitlist; // Queue for unassigned groups
    private Map<Integer, Group> activeGroups; // List of all groups being served
    private Menu menu; // Menu of available food items
    private Sales sales; // Tracks completed orders for reporting

    
    // This is all the observers of the restaurant class
    private ArrayList<RestaurantObserver> rObservers;


    public Restaurant() {
        this.serverMap = new HashMap<>();
        this.tableMap = new HashMap<>();
        this.waitlist = new HashMap<>();
        this.activeGroups = new HashMap<>();
        this.menu = new Menu();
        this.sales = new Sales();
        this.rObservers = new ArrayList<>();
        
        int[] tableCapacities = {2, 2, 4, 4, 10, 10}; // Initial table
        int numTables = tableCapacities.length;
        for (int i = 0; i < numTables; i++) {
            Table t = new Table(i + 1, tableCapacities[i]);
            tableMap.put(t.getTableNum(), t);
        }
    }
    
    // --------------------- Table Management ---------------------
    public Map<Integer, Table> getTables(){
    	return new HashMap<Integer, Table>(tableMap);
    }

    /*
	 * @pre tableNum != null
	 */
    public Table getTableByNumberCopy(int tableNum) {
        Table original = tableMap.get(tableNum);
        if (original != null) {
            return new Table(original);
        } 
        return null;
    }
    
    

    // --------------------- Group Management ---------------------
    
    public Map<Integer, Group> getWaitlist(){
    	return new HashMap<Integer, Group>(waitlist);
    } 
    
    public Map<Integer, Group> getActiveGroups(){
    	return new HashMap<Integer, Group>(activeGroups);
    } 
    

    /*
	 * @pre customerNames != null
	 */
    public int addGroup(List<String> customerNames) {
        Group group = new Group();
        for (String name : customerNames) {
            Customer c = new Customer(name);
            if (c != null) group.addPerson(c);
        }
        waitlist.put(group.getGroupId(), group);
        notifyRestaurantObserversToAddGroup();
        return group.getGroupId();
    }

    /*
	 * @pre groupId != null, tableNum != null
	 */
	public void assignTable(int groupId, int tableNum) {
    	Group group = waitlist.getOrDefault(groupId, null);
        Table table = tableMap.getOrDefault(tableNum, null);
        if (group != null && table != null) {
        	Group newlyActiveGroup = waitlist.get(groupId);
        	newlyActiveGroup.beingServed();
            activeGroups.put(newlyActiveGroup.getGroupId(), newlyActiveGroup);
            table.assignGroup(newlyActiveGroup);
        	waitlist.remove(groupId);
        	notifyAddGroupTable(groupId, tableNum);
        }
    }

    // --------------------- Server Management ---------------------
    
    public Map<String, Server> getServers() {
    	return new HashMap<String, Server>(serverMap);
    }

    /*
	 * @pre name != null
	 */
    public boolean addServer(String name) {
    	if (!serverMap.containsKey(name)) {
    		Server newServer = new Server(name);
            serverMap.put(name, newServer);
            notifyRestaurantObserversToAddServer();
            return true;
    	}
    	return false;
    	
    }

    public Server getTopTipEarner() {
    	List<Server> servers = new ArrayList<>(serverMap.values());
        Server server = Collections.max(servers, Comparator.comparingDouble(s -> s.getTips()));
        return new Server(server); // add copy constructor for Server
    }

    /*
	 * @pre serverName != null, tableNum != null
	 */
    public boolean assignServerToTable(String serverName, int tableNum) {
        Server server = serverMap.getOrDefault(serverName, null);
        Table table = getTableByNumber(tableNum);
        if (server != null && table != null) {
        	server.addTable(tableNum);
        	table.assignServer(serverName);
        	notifyAddServerTable(serverName, tableNum);
            return true;
        }
        return false;
    }
    
    /*
	 * @pre serverName != null, tableNum != null
	 */
    public boolean removeServerFromTable(String serverName, int tableNum) {
        Server server = serverMap.getOrDefault(serverName, null);
        Table table = getTableByNumber(tableNum);
        if (server != null && table != null) {
        	server.removeTable(tableNum);
        	table.assignServer(null);
//        	notifyRemoveServerTable(tableNum);
            return true;
        }
        return false;
    }

    // --------------------- Order & Billing ---------------------
    
    /*
	 * @pre groupId != null, customerName != null, food != null, qty != null, mods != null
	 */
    public boolean orderFoodFor(int groupId, String customerName, Food food, int qty, String mods) {
        Group g = getGroupById(groupId);
        if (g != null && !g.onWaitlist()) {
            g.placeOrder(customerName, food, qty, mods);
            notifyUpdateTable();
            return true;
        }
        return false;
    }


	/*
	 * @pre groupId != null, customerName != null, amount != null
	 */    public boolean addTipFor(int groupId, String customerName, double amount) {
    	Group g = getGroupById(groupId);
        if (g != null && !g.onWaitlist()) {
            g.addTip(customerName, amount);
            return true;
        }
        return false;
    }

    /*
	 * @pre groupId != null, name != null
	 */
    public boolean payBillFor(int groupId, String name) {
        Group g = getGroupById(groupId);
        if ( g != null && g.payBill(name) && !g.onWaitlist()) {
            Bill customerBill = g.getCustomerBill(name);
            if (customerBill != null) {
            	sales.addCompletedOrder(customerBill);
                return true;
            }
        }
        return false;
    }

    /*
	 * @pre groupId != null
	 */
    public boolean splitAndPayBillEvenly(int groupId) {
    	Group g = getGroupById(groupId);
        if (g != null && !g.onWaitlist()) {
            g.splitBillEvenly();
            for (String customerName: g.getCustomersName()) {
            	Bill customerBill = g.getCustomerBill(customerName);
                if (customerBill != null) {
                	sales.addCompletedOrder(customerBill);
                	continue;
                }
                return false;
            }
            return true;
        }
        return false;
    }

    /*
	 * @pre tableNum != null
	 */
    public boolean closeGroupOrder(int tableNum) {
        Table t = getTableByNumber(tableNum);
        if (t != null && t.isOccupied()) {
        	Group g = getGroupById(t.getGroupId());
            Server s = serverMap.getOrDefault(t.getAssignedServerName(), null);
            if (g == null || s == null) {
            	return false;
            }
            activeGroups.remove(t.getGroupId());
            if (g.orderTaken()) {
		        double tips = g.getTotalTip();
		        s.addTips(tips);
            }
            t.clearTable();
            notifyUpdateTable();
            return true;
        }
        return false;
    }

    // --------------------- Sales Reporting ---------------------

    /*
	 * @pre onserver != null
	 */
    public void registerSalesObserver(SalesObserver observer) {
        this.sales.addObserver(observer);
    }
    
    public List<FoodData> getTopSellingItems() {
        return sales.sortMostSales();
    }

    public List<FoodData> getTopMoneyMakers() {
        return sales.sortOffRevenue();
    }
    
    public Map<Food, Integer> getSales() {
    	return sales.getSales();
    }
    
    public Sales getSalesObject() {
    	return new Sales(sales);
    }

    // --------------------- Helpers ---------------------


    private Table getTableByNumber(int tableNum) {
        return tableMap.getOrDefault(tableNum, null);
    }
    
    public Menu getMenu() {
    	return menu;
    }
    
    public String getPaymentSummary(int groupId) {
    	Group group = getGroupById(groupId);
    	if (group == null) return "Error: Group not found. Can't show payment summary";
    	String paymentSummary = "Payment Summary for Group " + group.getGroupId() + "\n";
    	int index = 1;
    	for (String customer: group.getCustomersName()) {
    		Bill customerBill = group.getCustomerBill(customer);
    		double tip = customerBill.getTip();
    		double total = customerBill.getAmountPaid();
    		String paymentStatus = customerBill.isPaid() ? "Paid" : "Not Paid";
    		paymentSummary += String.format(
                    "%d. %s\n  - Total: $%.2f, Tip: $%.2f, Status: %s\n",
                    index, customer, total, tip, paymentStatus);
    		index++;
    	}
    	return paymentSummary;
    }

//    public void showWaitlist() {
//        waitlist.forEach(g -> System.out.println("Group " + g.getGroupId() + ": size = " + g.getGroupSize()));
//    }

    public void showTopTipEarner() {
        Server top = getTopTipEarner();
        if (top != null) {
            System.out.println("Top tip earner: " + top.getName() + " ($" + top.getTips() + ")");
        }
    }

    public void showSalesReport() {
        System.out.println(sales);
    }
    
//    private Customer getCustomerByName(String name) {
//        for (Customer c : allCustomers) {
//            if (c.getName().equalsIgnoreCase(name)) {
//                return c;
//            }
//        }
//        return null;
//    }
    
    private Group getGroupById(int groupId) {
        return activeGroups.getOrDefault(groupId, null);
    }
    

    // --------------------- Observer Methods ---------------------
    /*
	 * @pre o != null
	 */
    public void addRestaurantObserver(RestaurantObserver o) {
    	this.rObservers.add(o);
    }
    
    /*
  	 * @pre o != null
  	 */
    public void removeRestaurantObserver(RestaurantObserver o) {
    	this.rObservers.remove(o);
    }

    private void notifyRestaurantObserversToAddGroup() {
    	for (RestaurantObserver observer : rObservers) {
            observer.onGroupUpdate();
    	}
	}
    
    private void notifyRestaurantObserversToAddServer() {
    	for (RestaurantObserver observer : rObservers) {
            observer.onServerUpdate();
    	}
	}
    
    private void notifyUpdateTable() {
    	for (RestaurantObserver observer: rObservers) {
    		observer.onTableUpdate();
    	}
    }

    /*
  	 * @pre groupNum != null tableNum != null
  	 */
    private void notifyAddGroupTable(int groupNum, int tableNum) {
    	for (RestaurantObserver o : this.rObservers) {
    		o.assignGroupEvent(groupNum, tableNum);
    	}
    }
    /*
  	 * @pre serverName != null tableNum != null
  	 */
    private void notifyAddServerTable(String serverName, int tableNum) {
    	for (RestaurantObserver o : this.rObservers) {
    		o.assignServerEvent(serverName, tableNum);
    	}
    }
    
//    private void notifyRemoveServerTable(int tableNum) {
//    	for (RestaurantObserver o : this.rObservers) {
//    		o.removeServerEvent(tableNum);
//    	}
//    }
    
}
