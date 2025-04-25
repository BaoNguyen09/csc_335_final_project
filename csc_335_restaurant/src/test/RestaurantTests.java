package test;

import model.*; // Import necessary model classes
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;


import java.util.Arrays;
import java.util.Collections; // Keep for Collections.emptyList() if needed
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTests {

    private Restaurant restaurant;
    private final int[] TABLE_CAPACITIES = {2, 4, 6}; // Tables 1, 2, 3

    // Sample data - We can define them but cannot directly verify their state inside Restaurant
    private final String aliceName = "Alice";
    private final String bobName = "Bob";
    private final String charlieName = "Charlie";
    private final String davidName = "David";

    private final String server1Name = "Server1";
    private final String server2Name = "Server2";

    // We create Food objects to pass them into orderFoodFor,
    // assuming the Menu inside Restaurant is somehow pre-populated or accepts these.
    // This is a necessary assumption as we cannot call restaurant.getMenu().addItem().
    private final Food burger = new Food("Burger", FoodType.ENTREE, 10.0);
    private final Food salad = new Food("Salad", FoodType.SIDE,8.0);
    private final Food pizza = new Food("Pizza", FoodType.ENTREE, 15.0);


    @BeforeEach
    void setup() {
        Group.resetGroupIdCounter();

        restaurant = new Restaurant();

        restaurant.addServer(server1Name);
        restaurant.addServer(server2Name);
    }

    @Test
    void testConstructorRuns() {
        // Very basic test: can we create an instance?
        assertNotNull(restaurant);

    }

    @Test
    void testAddServerRuns() {
        // We can call the method, but cannot verify internal list/map state.
        assertTrue(() -> restaurant.addServer("Server3"));
        assertFalse(() -> restaurant.addServer(server1Name)); // Try adding duplicate
    }

    @Test
    void testAddGroupRuns() {
        // Call addGroup, but we cannot verify if it was assigned or waitlisted,
        // but we can get the group ID directly from this method.
        List<String> groupMembers = Arrays.asList(aliceName, bobName); // Size 2
        int groupId = restaurant.addGroup(groupMembers);
        assertEquals(1, groupId); // make sure a new group was added
        // Cannot assert waitlist size, active group size, or available tables.
    }

    @Test
    void testAssignServerToTableReturnsBoolean() {
        // Assuming server1 was added in setup
        // Assign server1 to table 1 (assume table 1 exists)
        assertTrue(restaurant.assignServerToTable(server1Name, 1), "Assigning valid server to valid table should return true");

        // Assign non-existent server to table 1
        assertFalse(restaurant.assignServerToTable("NonExistentServer", 1), "Assigning invalid server should return false");

        // Assign server1 to non-existent table
        assertFalse(restaurant.assignServerToTable(server1Name, 99), "Assigning to invalid table should return false");

        // We cannot verify that table 1 *actually* has server1 assigned.
    }

    @Test
    void testOrderFoodAndPayUpdatesSales() {
        // Add a group
        final int groupId = restaurant.addGroup(Arrays.asList(aliceName));
        System.out.println("group id: " + groupId);
        restaurant.assignTable(groupId, 1);
        restaurant.assignServerToTable(server1Name, groupId);

        // Order food for Alice in Group 1
        assertTrue(restaurant.orderFoodFor(groupId, aliceName, burger, 2, "")); // 2 Burgers

        // Pay Alice's bill for Group 1
        assertTrue(restaurant.payBillFor(groupId, aliceName));

        // Verify using public sales reporting methods
        Map<Food, Integer> salesMap = restaurant.getSales();
        assertEquals(1, salesMap.size(), "Sales map should have one entry after one payment");
        assertTrue(salesMap.containsKey(burger), "Sales map should contain Burger");
        assertEquals(2, salesMap.get(burger), "Sales map should show quantity 2 for Burger");

        List<FoodData> topSelling = restaurant.getTopSellingItems();
        assertEquals(1, topSelling.size());
        FoodData burgerData = new FoodData(burger, 2, "");
        assertEquals(burgerData, topSelling.get(0));
        assertEquals(2, topSelling.get(0).getQuantity());

        List<FoodData> topMoney = restaurant.getTopMoneyMakers();
        assertEquals(1, topMoney.size());
        assertEquals(burgerData, topMoney.get(0));
        assertEquals(2 * burger.getPrice(), topMoney.get(0).getTotalPrice(), 0.001);
    }

    @Test
    void testSplitBillUpdatesSales() {
        final int groupId = restaurant.addGroup(Arrays.asList(aliceName, bobName));
        System.out.println("group id: " + groupId);
        assertTrue(restaurant.assignServerToTable(server1Name, groupId));
        restaurant.assignTable(groupId, 2);
        // Order food
        assertTrue(restaurant.orderFoodFor(groupId, aliceName, burger, 1, "")); // 1 Burger
        assertTrue(restaurant.orderFoodFor(groupId, bobName, salad, 1, ""));   // 1 Salad

        // Split and pay
        assertTrue(restaurant.splitAndPayBillEvenly(groupId));

        // Verify the EXTERNAL sales object was updated
        Map<Food, Integer> salesMap = restaurant.getSales(); // Check the object we passed in
        assertEquals(2, salesMap.size(), "External Sales map should have two entries");
        assertTrue(salesMap.containsKey(burger), "External Sales map should contain Burger");
        assertEquals(1, salesMap.get(burger), "External Sales map should show quantity 1 for Burger");
        assertTrue(salesMap.containsKey(salad), "External Sales map should contain Salad");
        assertEquals(1, salesMap.get(salad), "External Sales map should show quantity 1 for Salad");
    }
        
    @Test
    void testSplitBillUpdatesSalesInternalFix() {
        // IF the intention of splitAndPayBillEvenly was to update the *internal* sales,
        // the Restaurant code should be:
        //   this.sales.addCompletedOrder(c.getBill());
        // instead of
        //   sales.addCompletedOrder(c.getBill());
        // Assuming that fix is made for this test:

        
        final int groupId = restaurant.addGroup(Arrays.asList(aliceName, bobName));
        assertTrue(restaurant.assignServerToTable(server1Name, groupId));
        restaurant.assignTable(groupId, 2);
        assertTrue(restaurant.orderFoodFor(groupId, aliceName, burger, 1, ""));
        assertTrue(restaurant.orderFoodFor(groupId, bobName, salad, 1, ""));

        // Pass a dummy Sales object, assuming the method uses this.sales internally
        restaurant.splitAndPayBillEvenly(groupId); // Pass dummy object

        // Verify the INTERNAL sales object was updated
        Map<Food, Integer> internalSalesMap = restaurant.getSales();
        assertEquals(2, internalSalesMap.size(), "Internal Sales map should have two entries");
        assertTrue(internalSalesMap.containsKey(burger));
        assertEquals(1, internalSalesMap.get(burger));
        assertTrue(internalSalesMap.containsKey(salad));
        assertEquals(1, internalSalesMap.get(salad));
    }


    @Test
    void testAddTipAndGetTopEarner() {
         // Servers server1Name, server2Name added in setup

        // Add groups (Assume IDs 1, 2)
        final int group1Id = restaurant.addGroup(Arrays.asList(aliceName)); // Group 1
        final int group2Id = restaurant.addGroup(Arrays.asList(bobName, charlieName));   // Group 2

        // Assign servers
        // We don't know which table got which group, assign servers to tables 1 and 2
        assertTrue(restaurant.assignServerToTable(server1Name, 1));
        assertTrue(restaurant.assignServerToTable(server2Name, 3));
        restaurant.assignTable(group1Id, 1);
        restaurant.assignTable(group2Id, 3);

        // Order food
        assertTrue(restaurant.orderFoodFor(group1Id, aliceName, burger, 1, "")); // 1 Burger
        assertTrue(restaurant.orderFoodFor(group2Id, bobName, salad, 1, ""));   // 1 Salad
        
        // Add tips via the groups
        assertTrue(restaurant.addTipFor(group1Id, aliceName, 10.0)); // Tip for Group 1 (Server1)
        assertTrue(restaurant.addTipFor(group2Id, bobName, 25.0));   // Tip for Group 2 (Server2)
        assertTrue(restaurant.addTipFor(group2Id, charlieName, 5.0));    // Another tip for Group 2 (Server2) -> Total 30
        restaurant.closeGroupOrder(1);
        restaurant.closeGroupOrder(3);
        // Get top earner
        Server top = restaurant.getTopTipEarner();

        assertNotNull(top);
        // Server2 should have 30, Server1 should have 10
        assertEquals(server2Name, top.getName());
         assertEquals(30.0, top.getTips(), 0.001);
    }

    @Test
    void testGetTopTipEarnerNoServers() {
        // Create a restaurant with no servers added
        Restaurant emptyRestaurant = new Restaurant();
        assertThrows(NoSuchElementException.class, () -> {
            emptyRestaurant.getTopTipEarner();
        });
    }

    @Test
    void testCloseGroupOrderAffectsSalesReporting() {
         // Add group (ID 1), order, pay, check sales
        final int groupId = restaurant.addGroup(Arrays.asList(aliceName));;
        final int tableNum = 1; // Assumption based on first fit
        assertTrue(restaurant.assignServerToTable(server1Name, tableNum));
        restaurant.assignTable(groupId, tableNum);
        assertTrue(restaurant.orderFoodFor(groupId, aliceName, burger, 1, ""));
        assertTrue(restaurant.payBillFor(groupId, aliceName));

        Map<Food, Integer> salesBeforeClose = restaurant.getSales();
        assertEquals(1, salesBeforeClose.size());

        // Close the group/table
        System.out.println("table num: " + tableNum);
        assertTrue(() -> restaurant.closeGroupOrder(tableNum)); // Assume table 1 was used

        // Sales data should persist after closing
        Map<Food, Integer> salesAfterClose = restaurant.getSales();
        assertEquals(1, salesAfterClose.size(), "Sales data should remain after group close");
        assertEquals(1, salesAfterClose.get(burger));

        // Add another group (ID 2), order same item, pay, close
        final int group2Id = restaurant.addGroup(Arrays.asList(bobName)); // Group 2 -> Table 1 assumed (now free)
        assertTrue(restaurant.assignServerToTable(server1Name, tableNum));
        restaurant.assignTable(group2Id, tableNum);
        restaurant.orderFoodFor(group2Id, bobName, burger, 2, "");
        restaurant.payBillFor(group2Id, bobName);
        restaurant.closeGroupOrder(tableNum); // Close table 1 again

        // Check combined sales
        Map<Food, Integer> finalSales = restaurant.getSales();
        assertEquals(1, finalSales.size(), "Sales map should still only contain burger");
        assertEquals(1 + 2, finalSales.get(burger), "Burger quantity should be cumulative (1+2=3)");

        List<FoodData> topSelling = restaurant.getTopSellingItems();
        assertEquals(1, topSelling.size());
        FoodData burgerData = new FoodData(burger, 3, "");
        assertEquals(burgerData, topSelling.get(0));
        assertEquals(3, topSelling.get(0).getQuantity());
    }

     @Test
    void testInvalidOperationsDoNotThrow() {
         // Operations with invalid IDs or names should generally not throw exceptions,
         // but silently fail or do nothing according to the original code structure.

         // Use an invalid group ID (assuming 999 is never valid)
         final int invalidGroupId = 999;
         final int validTableId = 1;
         final String validCustomer = aliceName;
         final String invalidCustomer = "NonExistent";

         assertDoesNotThrow(() -> restaurant.orderFoodFor(invalidGroupId, validCustomer, burger, 1, ""));
         assertDoesNotThrow(() -> restaurant.orderFoodFor(1, invalidCustomer, burger, 1, "")); // Valid Group ID assumed 1
         assertDoesNotThrow(() -> restaurant.addTipFor(invalidGroupId, validCustomer, 5.0));
         assertDoesNotThrow(() -> restaurant.addTipFor(1, invalidCustomer, 5.0)); // Valid Group ID assumed 1
         assertDoesNotThrow(() -> restaurant.payBillFor(invalidGroupId, validCustomer));
         assertDoesNotThrow(() -> restaurant.payBillFor(1, invalidCustomer)); // Valid Group ID assumed 1
         assertDoesNotThrow(() -> restaurant.splitAndPayBillEvenly(invalidGroupId));
         assertFalse(() -> restaurant.closeGroupOrder(999)); // Invalid Table ID
         assertFalse(() -> restaurant.assignServerToTable("NonExistent", validTableId));
         assertFalse(() -> restaurant.assignServerToTable(server1Name, 999));

         // Check that sales weren't affected by invalid operations
         Map<Food, Integer> salesMap = restaurant.getSales();
         assertTrue(salesMap.isEmpty(), "Sales map should be empty after only invalid operations");
    }
     
     @Test
     void testRemoveServer() {
    	 restaurant.assignServerToTable(server1Name, 1);
    	 assertTrue(restaurant.removeServerFromTable(server1Name, 1));
    	 assertFalse(restaurant.removeServerFromTable(server1Name, 0));
     }
     
     @Test
     void testGetMenu() {
    	 assertTrue(restaurant.getMenu() instanceof Menu);
     }
}
