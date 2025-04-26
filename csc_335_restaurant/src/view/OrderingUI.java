package view;

import model.Menu;
import model.Food;
import model.FoodData;
import model.Group;
import model.Restaurant;
import model.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.Controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

public class OrderingUI extends JFrame {
    private Controller controller;
    private int tableNum;
    private Group group;
    private Menu menuModel;

    private JTable menuTable;
    private JTable orderTable;
    private DefaultTableModel menuTableModel;
    private DefaultTableModel orderTableModel;
    private JLabel totalLabel;
    private JList<String> groupMembersList;
    private JTextField quantityField;
    private JTextField modificationsField;
    private List<OrderItem> pendingOrder = new ArrayList<>();
    private boolean placedOrder;

    private List<String> groupMembers;

    public OrderingUI(Controller controller, int groupId, int tableNum) {
        super("Ordering System - Group " + groupId);
        this.controller = controller;
        this.group = controller.getActiveGroups().get(groupId);
        this.tableNum = tableNum;
        this.menuModel = controller.getMenu();
        // This is so that we can trigger a different view 
        placedOrder = false;
        
        /*
         * ChatGPT was used in this portion of the code to generate the Jframe window
         * and to initialize the Swing components like JLabel.
         */
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 500);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
    	/*
         * ChatGPT was used in this entire init portion of the code to generate all
         * swing components that were needed and the design of the GUI.
         */
        groupMembers = group.getCustomersName();
        groupMembersList = new JList<>(groupMembers.toArray(new String[0]));
        groupMembersList.setBorder(BorderFactory.createTitledBorder("Group Members"));

        quantityField = new JTextField("1", 5);
        modificationsField = new JTextField(10);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);
        inputPanel.add(new JLabel("Modifications:"));
        inputPanel.add(modificationsField);

        // Menu Table
        String[] menuCols = {"Item", "Type", "Price"};
        menuTableModel = new DefaultTableModel(menuCols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        menuTable = new JTable(menuTableModel);
        menuTable.setAutoCreateRowSorter(true);
        loadMenuItems();
        JScrollPane menuScroll = new JScrollPane(menuTable);
        menuScroll.setBorder(BorderFactory.createTitledBorder("Menu"));

        // Order Table
        String[] orderCols = {"Person", "Item", "Quantity", "Modifications", "Price"};
        orderTableModel = new DefaultTableModel(orderCols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        orderTable = new JTable(orderTableModel);
        JScrollPane orderScroll = new JScrollPane(orderTable);
        orderScroll.setBorder(BorderFactory.createTitledBorder("Current Order"));

        // Buttons
        JButton addButton = new JButton("Add Order");
        addButton.addActionListener(e -> addToOrder());

        JButton removeButton = new JButton("Remove Order");
        removeButton.addActionListener(e -> removeFromOrder());

        JButton payButton = new JButton("Pay");
        payButton.addActionListener(e -> processPayment());
        
        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.addActionListener(e -> placeOrder());

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(payButton);
        buttonPanel.add(placeOrderButton);



        // Total Label
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(totalLabel.getFont().deriveFont(Font.BOLD, 14f));
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.add(totalLabel);

        // Layout Panels
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(groupMembersList, BorderLayout.CENTER);
        leftPanel.add(inputPanel, BorderLayout.NORTH);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        JSplitPane centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuScroll, orderScroll);
        centerSplit.setResizeWeight(0.5);

        add(leftPanel, BorderLayout.WEST);
        add(centerSplit, BorderLayout.CENTER);
        add(totalPanel, BorderLayout.SOUTH);
    }
    
    /*
     * In the following functions, ChatGPT was used in only the JOptionPane message to provide some
     * error checking.
     */
    
    private void loadMenuItems() {
        for (Food f : menuModel.getMenuItems()) {
            menuTableModel.addRow(new Object[]{f.getName(), f.getType().toString(), String.format("$%.2f", f.getPrice())});
        }
    }

    private void addToOrder() {
        String person = groupMembersList.getSelectedValue();
        int row = menuTable.getSelectedRow();
        if (person == null || row < 0) {
            JOptionPane.showMessageDialog(this, "Select a person and a menu item.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
            if (quantity <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String modifications = modificationsField.getText();

        int modelRow = menuTable.convertRowIndexToModel(row);
        String itemName = (String) menuTableModel.getValueAt(modelRow, 0);
        Food f = menuModel.getItemFromMenu(itemName);
        
        OrderItem item = new OrderItem(person, f, quantity, modifications);
        pendingOrder.add(item);

        double totalPrice = f.getPrice() * quantity;
        orderTableModel.addRow(new Object[]{person, f.getName(), quantity, modifications, String.format("$%.2f", totalPrice)});
        updateTotal();
    }

    
    private void placeOrder() {
        if (pendingOrder.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No items to place.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (OrderItem item : pendingOrder) {
        	
            // Backend call to place order
            controller.orderFoodFor(group.getGroupId(), item.person, item.food, item.quantity, item.modifications);
        }

        JOptionPane.showMessageDialog(this, "Order placed successfully!");
        pendingOrder.clear();  // Clear buffer after placing
        
    }
    
    private void removeFromOrder() {
        int row = orderTable.getSelectedRow();
        if (row < 0) return;

        // Remove from pendingOrder buffer
        if (row < pendingOrder.size()) {
            pendingOrder.remove(row);
        }

        // Remove from UI table
        orderTableModel.removeRow(row);

        updateTotal();
    }

    private void updateTotal() {
        double sum = 0;
        for (int i = 0; i < orderTableModel.getRowCount(); i++) {
            String priceStr = (String) orderTableModel.getValueAt(i, 4);
            sum += Double.parseDouble(priceStr.replace("$", ""));
        }
        totalLabel.setText(String.format("Total: $%.2f", sum));
    }

    private void processPayment() {

        if (orderTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No orders placed.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this, "Split bill evenly?", "Payment Option", JOptionPane.YES_NO_OPTION);
        boolean paymentSuccess = false;

        if (choice == JOptionPane.YES_OPTION) {
            paymentSuccess = controller.splitAndPayBillEvenly(group.getGroupId());
        } else { // pay individually
        	for (String payer: groupMembers) {
        		boolean paymentStatus = controller.payBillFor(group.getGroupId(), payer);
        		try {
        			if (!paymentStatus) throw new Exception();
        		} catch(Exception e) {
        			JOptionPane.showMessageDialog(this, "There's an error with payment of customer: " + payer, 
        					"Error", JOptionPane.ERROR_MESSAGE);
        		}
        	}
            paymentSuccess = true;
        }

        if (paymentSuccess) { // After paying, move to tipping
            try {
                for (String member : groupMembers) {
                	String tipInput = JOptionPane.showInputDialog(this, String.format("Enter tip amount for %s:", member));
                	double tip = Double.parseDouble(tipInput);
                    boolean tippingStatus = controller.addTipFor(group.getGroupId(), member, tip);
                    if (!tippingStatus) throw new Exception("Invalid tip entered. Skipping tip.");
                }
                String paymentSummary = controller.getPaymentSummary(group.getGroupId());
	            if (!controller.closeGroupOrder(tableNum)) throw new Exception("Can't close order. Check if this table has a valid group and server");
	            if (paymentSummary.contains("Error")) throw new Exception(paymentSummary);
	            JOptionPane.showMessageDialog(this, paymentSummary);
	            dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Payment failed.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }
    
    // This is a static class to hold all the details of a single row of an order
    private static class OrderItem {
        String person;
        Food food;
        int quantity;
        String modifications;

        public OrderItem(String person, Food food, int quantity, String modifications) {
            this.person = person;
            this.food = food;
            this.quantity = quantity;
            this.modifications = modifications;
        }
    }
}