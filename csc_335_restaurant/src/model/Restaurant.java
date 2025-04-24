package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurant {
	// Core components of the restaurant
    private List<Server> servers; // All servers in the restaurant
    private Map<String, Server> serverMap; // Fast table lookup by server name (no duplicates allow)
    private Map<Integer, Table> tableMap; // Fast table lookup by table number
    private List<Group> waitlist; // Queue for unassigned groups
    private Map<Integer, Group> activeGroups; // List of all groups being served
    private List<Customer> allCustomers; // Central list of all known customers
    private Menu menu; // Menu of available food items
    private Sales sales; // Tracks completed orders for reporting
    private int availableTables; // Track how many tables are available

    public Restaurant(int[] tableCapacities) {
        this.servers = new ArrayList<>();
        this.serverMap = new HashMap<>();
        this.tableMap = new HashMap<>();
        this.waitlist = new ArrayList<>();
        this.activeGroups = new HashMap<>();
        this.allCustomers = new ArrayList<>();
        this.menu = new Menu();
        this.sales = new Sales();
        availableTables = tableCapacities.length;

        int numTables = tableCapacities.length;
        for (int i = 0; i < numTables; i++) {
            Table t = new Table(i + 1, tableCapacities[i]);
            tableMap.put(t.getTableNum(), t);
        }
    }

    // --------------------- Customer Management ---------------------

    public void addCustomer(String name) {
        if (getCustomerByName(name) == null) {
            allCustomers.add(new Customer(name));
        }
    }

    public List<Customer> getAllCustomers() {
    	List<Customer> customers = new ArrayList<>();
    	for (Customer c: allCustomers) {
    		customers.add(new Customer(c));
    	}
        return Collections.unmodifiableList(customers);
    }

    // --------------------- Group Management ---------------------

    public int addGroup(List<String> customerNames) {
        Group group = new Group();
        for (String name : customerNames) {
            Customer c = getCustomerByName(name);
            System.out.println(c);
            if (c != null) group.addPerson(c);
        }
        waitlist.add(group);
        assignTable();
        return group.getGroupId();
    }

    public void assignTable() {
        int i = 0;
        // iterate through the waitlist groups, and assign table for each of them
        while (i < waitlist.size() && availableTables > 0) {
            Group group = waitlist.get(i);
            for (Table table : tableMap.values()) {
                if (!table.isOccupied() && table.getMaxCapacity() >= group.getGroupSize()) {
                    if (table.assignGroup(group)) {
                    	Group newlyActiveGroup = waitlist.remove(i);
                    	i--;
                    	newlyActiveGroup.beingServed();
                        activeGroups.put(newlyActiveGroup.getGroupId(), newlyActiveGroup);
                        table.assignGroup(newlyActiveGroup);
                        availableTables -= 1;
                        break;
                    }
                }
            }
            i++;
        }
    }

    // --------------------- Server Management ---------------------

    public void addServer(String name) {
    	Server newServer = new Server(name);
        servers.add(newServer);
        serverMap.putIfAbsent(name, newServer);
    }

    public Server getTopTipEarner() {
        Server server = Collections.max(servers, Comparator.comparingDouble(s -> s.getTips()));
        return new Server(server); // add copy constructor for Server
    }

    public boolean assignServerToTable(String serverName, int tableNum) {
        Server server = serverMap.getOrDefault(serverName, null);
        Table table = getTableByNumber(tableNum);
        if (server != null && table != null) {
            return table.assignServer(server);
        }
        return false;
    }

    // --------------------- Order & Billing ---------------------

    public boolean orderFoodFor(int groupId, String customerName, Food food, int qty, String mods) {
        Group g = getGroupById(groupId);
        if (g != null && !g.onWaitlist()) {
            g.placeOrder(customerName, food, qty, mods);
            return true;
        }
        return false;
    }

    public void addTipFor(int groupId, String name, double amount) {
    	Group g = getGroupById(groupId);
        if (g != null && !g.onWaitlist()) {
            g.addTip(name, amount);
        }
    }

    public boolean payBillFor(int groupId, String name) {
        Group g = getGroupById(groupId);
        if ( g != null && g.payBill(name) && !g.onWaitlist()) {
            Customer c = getCustomerByName(name);
            System.out.println("god test: " +c);
            if (c != null && c.isBillPaid()) {
            	System.out.println("fyc");
            	sales.addCompletedOrder(c.getBill());
            }
            return true;
        }
        return false;
    }

    public void splitAndPayBillEvenly(int groupId) {
    	Group g = getGroupById(groupId);
        if (g != null && !g.onWaitlist()) {
            g.splitBillEvenly();
            for (String customerName: g.getCustomersName()) {
            	Customer c = getCustomerByName(customerName);
                if (c != null && !c.isBillPaid()) {
                	sales.addCompletedOrder(c.getBill());
                }
            }
        }
    }

    public void closeGroupOrder(int tableNum) {
        Table t = getTableByNumber(tableNum);
        if (t != null && t.isOccupied()) {
            t.clearTable();
            availableTables += 1;
            activeGroups.remove(t.getGroupId());
            assignTable();
        }
    }

    // --------------------- Sales Reporting ---------------------

    public List<FoodData> getTopSellingItems() {
        return sales.sortMostSales();
    }

    public List<FoodData> getTopMoneyMakers() {
        return sales.sortOffRevenue();
    }
    
    public Map<Food, Integer> getSales() {
    	System.out.println(sales);
    	return sales.getSales();
    }

    // --------------------- Helpers ---------------------

    private Table getTableByNumber(int tableNum) {
        return tableMap.getOrDefault(tableNum, null);
    }
    
    public void showMenu() {
    	System.out.println(menu);
    }

    public void showWaitlist() {
        waitlist.forEach(g -> System.out.println("Group " + g.getGroupId() + ": size = " + g.getGroupSize()));
    }

    public void showTopTipEarner() {
        Server top = getTopTipEarner();
        if (top != null) {
            System.out.println("Top tip earner: " + top.getName() + " ($" + top.getTips() + ")");
        }
    }

    public void showSalesReport() {
        System.out.println(sales);
    }
    
    private Customer getCustomerByName(String name) {
        for (Customer c : allCustomers) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }
    
    private Group getGroupById(int groupId) {
        return activeGroups.getOrDefault(groupId, null);
    }
}
