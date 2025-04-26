package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import model.Restaurant;
import model.SalesObserver;
import model.Server;
import model.Food;
import model.FoodData;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class SalesUI extends JFrame implements SalesObserver {

    private JTable foodSalesTable;
    private Restaurant restaurant;
    private JLabel topTipServerLabel;

    public SalesUI(Restaurant restaurant, Controller controller) {
        super("Sales Board");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        this.restaurant = restaurant;

        topTipServerLabel = new JLabel("Top Tip Server: None");

        restaurant.registerSalesObserver(this);

        initComponents();
        updateSalesUI();
    }

    private void initComponents() {
        topTipServerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Food Sales Table Setup
        String[] foodCols = {"Food Item", "Quantity Sold", "Total Revenue ($)"};
        DefaultTableModel foodModel = new DefaultTableModel(foodCols, 0);
        foodSalesTable = new JTable(foodModel);
        foodSalesTable.setAutoCreateRowSorter(true);

        JScrollPane foodScroll = new JScrollPane(foodSalesTable);
        foodScroll.setBorder(BorderFactory.createTitledBorder("Food Sales"));

        // Sorting Buttons
        JButton sortByQuantity = new JButton("Sort by Quantity Sold");
        JButton sortByRevenue = new JButton("Sort by Revenue");

        sortByQuantity.addActionListener(e -> sortByQuantity());
        sortByRevenue.addActionListener(e -> sortByRevenue());

        JPanel sortButtonPanel = new JPanel();
        sortButtonPanel.add(sortByQuantity);
        sortButtonPanel.add(sortByRevenue);

        // Layout Setup
        JPanel foodPanel = new JPanel(new BorderLayout());
        foodPanel.add(foodScroll, BorderLayout.CENTER);
        foodPanel.add(sortButtonPanel, BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topTipServerLabel, BorderLayout.NORTH);
        mainPanel.add(foodPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void sortByQuantity() {
        DefaultTableModel foodModel = (DefaultTableModel) foodSalesTable.getModel();
        foodModel.setRowCount(0);  // Clear existing rows

        // Assuming restaurant.getSalesObject() returns the Sales instance
        ArrayList<FoodData> sortedList = restaurant.getSalesObject().sortMostSales();

        // Sorting in descending order because sales returns in ascending
        for (int i = sortedList.size() - 1; i >= 0; i--) {
            FoodData item = sortedList.get(i);
            double revenue = item.getPrice() * item.getQuantity();
            foodModel.addRow(new Object[]{
                item.getName(),
                item.getQuantity(),
                String.format("%.2f", revenue)
            });
        }
    }

    private void sortByRevenue() {
        DefaultTableModel foodModel = (DefaultTableModel) foodSalesTable.getModel();
        foodModel.setRowCount(0);  // Clear existing rows

        ArrayList<FoodData> sortedList = restaurant.getSalesObject().sortOffRevenue();

        // Sorting in descending order
        for (int i = sortedList.size() - 1; i >= 0; i--) {
            FoodData item = sortedList.get(i);
            double revenue = item.getPrice() * item.getQuantity();
            foodModel.addRow(new Object[]{
                item.getName(),
                item.getQuantity(),
                String.format("%.2f", revenue)
            });
        }
    }

	@Override
    public void onSaleUpdate(double totalSales) {
        updateSalesUI();
    }

    public void updateSalesUI() {
        // Update Top Tip Server Label
        Server topTipServer = restaurant.getTopTipEarner();
        if (topTipServer != null) {
        	topTipServerLabel.setText("Top Tip Server: " + topTipServer.getName() + 
        			" ($" + String.format("%.2f", topTipServer.getTips()) + ")");
        	
        } else {
            topTipServerLabel.setText("Top Tip Server: None");
        }

        // Update Food Sales Table
        DefaultTableModel foodModel = (DefaultTableModel) foodSalesTable.getModel();
        foodModel.setRowCount(0);  // Clear existing rows

        Map<Food, Integer> foodSales = restaurant.getSales(); 

        for (Food item : foodSales.keySet()) {
            int quantity = foodSales.get(item);
            double revenue = item.getPrice() * quantity;

            foodModel.addRow(new Object[]{
                item.getName(),
                quantity,
                String.format("%.2f", revenue)
            });
        }
    }
}