package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Bill;
import model.Customer;
import model.Food;
import model.FoodType;
import model.Group;
import model.OrderFood;

class GroupTests {

	private Food burger;
    private Food soda;
    private Customer alice;
    private Customer bob;
    private ArrayList<Customer> initialMembers;
    private Group group;

    @BeforeEach
    void setup() {
    	Group.resetGroupIdCounter(); // reset counter before each test
        burger = new Food("Burger", FoodType.ENTREE, 10.0);
        soda = new Food("Soda", FoodType.DRINK, 2.5);

        alice = new Customer("Alice");
        alice.orderFood(burger, 1, "");
        alice.tip(2.0);

        bob = new Customer("Bob");
        bob.orderFood(soda, 2, "no ice");
        bob.tip(1.0);

        group = new Group();
        group.addPerson(alice);
        group.addPerson(bob);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(1, group.getGroupId());
        assertEquals(2, group.getGroupSize());
    }

    @Test
    void testGetTotalBill() {
        // Alice: $10 + $2 tip, Bob: $5 + $1 tip
        double expected = 10.0 + 2.0 + 5.0 + 1.0;
        assertEquals(expected, group.getTotalBill(), 0.001); // The 0.001 is a delta, or tolerance value, used when comparing floating-point numbers
    }

    @Test
    void testGetTotalTip() {
        assertEquals(3.0, group.getTotalTip(), 0.001); // The 0.001 is a delta, or tolerance value, used when comparing floating-point numbers
    }

    @Test
    void testCopyConstructorSharesMembers() {
        Group copy = new Group(group);
        assertEquals(group.getGroupId(), copy.getGroupId());
        assertEquals(group.getGroupSize(), copy.getGroupSize());
        assertEquals(group.getTotalBill(), copy.getTotalBill(), 0.001);
        // same object references for members
        assertSame(group.getOrderSessions().get(0), copy.getOrderSessions().get(0));
    }

    @Test
    void testGetOrderSessionsIsUnmodifiable() {
        List<OrderFood> sessions = group.getOrderSessions();
        assertEquals(2, sessions.size());
        assertTrue(sessions.get(0).getName().equals("Alice") || sessions.get(0).getName().equals("Bob"));

        assertThrows(UnsupportedOperationException.class, () -> {
            sessions.add(new Customer("Charlie"));
        });
    }
    
    @Test
    void testOrderFoodThroughOrderSession() {
        // Get Alice via OrderFood interface
        OrderFood aliceSession = group.getOrderSessions()
            .stream()
            .filter(c -> c.getName().equals("Alice"))
            .findFirst()
            .orElseThrow();

        // Add a new order for Alice
        Food fries = new Food("Fries", FoodType.SIDE, 3.0);
        aliceSession.orderFood(fries, 2, "extra crispy");

        // Check Alice's updated bill
        double expectedCost = 10.0 + 2.0 + 5.0 + 1.0 + (2 * 3.0);  // Previous group total + new Fries
        assertEquals(expectedCost, group.getTotalBill(), 0.001);
        assertEquals("Alice", aliceSession.getName()); // ensure the interface behavior works as expected
    }

    @Test
    void testAddPersonAddsNewCustomer() {
        Customer charlie = new Customer("Charlie");
        group.addPerson(charlie);
        assertEquals(3, group.getGroupSize());
        assertTrue(group.getOrderSessions().stream().anyMatch(c -> c.getName().equals("Charlie")));
    }

    @Test
    void testAddPersonDoesNotDuplicate() {
        group.addPerson(alice); // already in group
        assertEquals(2, group.getGroupSize());
    }
    
    @Test
    void testGetGroupId() {
    	assertEquals(1, group.getGroupId());
    	Group group2 = new Group();
    	group2.addPerson(alice);
    	group2.addPerson(bob);
    	assertEquals(2, group2.getGroupId());
    }
    
    @Test
    void testPlaceOrder() {
        // Place an order for Alice
        Food fries = new Food("Fries", FoodType.SIDE, 3.0);
        group.placeOrder("Alice", fries, 2, "extra crispy");
        // Alice's bill should reflect 2 * 3.0 + 10.0 (burger) + 2.0 (tip)= 18.0
        assertEquals(18.0, alice.getBillCost(), 0.001);
    }

    @Test
    void testAddTip() {
        // Add a tip for Bob
        group.addTip("Bob", 4.5);
        // Bob's tip should be 4.5
        assertEquals(4.5, bob.getBill().getTip(), 0.001);
    }

    @Test
    void testPayBill() {
        // Alice orders something
        Food burger = new Food("Burger", FoodType.ENTREE, 10.0);
        group.placeOrder("Alice", burger, 1, "");
        // Pay Alice's bill
        assertFalse(alice.isBillPaid());
        group.payBill("Alice");
        assertTrue(alice.isBillPaid());
    }

    @Test
    void testNonexistentCustomer() {
        // Methods should silently ignore unknown names
        group.placeOrder("Charlie", new Food("Soda", FoodType.DRINK, 2.5), 1, "");
        group.addTip("Charlie", 1.0);
        group.payBill("Charlie");
        // Original customers unchanged
        assertEquals(12, alice.getBillCost(), 0.001);
        assertEquals(6.0, bob.getBillCost(), 0.001);
    }
    
    @Test
    void testGetCustomersName() {
    	ArrayList<String> names = new ArrayList<>();
    	names.add("Alice");
    	names.add("Bob");
    	
    	assertEquals(group.getCustomersName(), names);
    }
    
    @Test
    void testGetCustomerBill() {
    	assertEquals(alice.getBill(), group.getCustomerBill("Alice"));
    }
    
    @Test
    void testSplitBillEvenly() {
    	group.splitBillEvenly();
    	
    	Bill aliceBill = alice.getBill();
    	Bill bobBill = bob.getBill();
    	
    	assertEquals(aliceBill.getAmountPaid(), 7.5);
    	assertEquals(bobBill.getAmountPaid(), 7.5);
    }

}
