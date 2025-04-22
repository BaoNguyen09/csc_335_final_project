package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SalesUI extends JFrame {

    private JTable serverSalesTable;
    private JTable foodSalesTable;

    public SalesUI() {
        super("Sales Board");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        // Dummy sales data
        Map<String, Double> serverTips = new HashMap<>();
        serverTips.put("Alice", 120.0);
        serverTips.put("Bob", 200.0);
        serverTips.put("Charlie", 150.0);

        Map<String, int[]> foodSales = new HashMap<>();
        // int[]{quantitySold, totalRevenue}
        foodSales.put("Burger", new int[]{25, 250});
        foodSales.put("Pasta", new int[]{40, 480});
        foodSales.put("Salad", new int[]{30, 300});

        // Server Sales Table (Tips Only)
        String[] serverCols = {"Server", "Total Tips ($)"};
        DefaultTableModel serverModel = new DefaultTableModel(serverCols, 0);
        serverTips.forEach((name, tips) -> {
            serverModel.addRow(new Object[]{name, String.format("%.2f", tips)});
        });
        serverSalesTable = new JTable(serverModel);
        JScrollPane serverScroll = new JScrollPane(serverSalesTable);
        serverScroll.setBorder(BorderFactory.createTitledBorder("Server Tips"));

        // Food Sales Table
        String[] foodCols = {"Food Item", "Quantity Sold", "Total Revenue ($)"};
        DefaultTableModel foodModel = new DefaultTableModel(foodCols, 0);
        foodSales.forEach((item, data) -> {
            foodModel.addRow(new Object[]{item, data[0], String.format("%.2f", (double) data[1])});
        });
        foodSalesTable = new JTable(foodModel);
        JScrollPane foodScroll = new JScrollPane(foodSalesTable);
        foodScroll.setBorder(BorderFactory.createTitledBorder("Food Sales"));

        // Sorting Options
        JPanel sortPanel = new JPanel();
        JButton sortByQuantity = new JButton("Sort by Quantity Sold");
        JButton sortByRevenue = new JButton("Sort by Revenue");

        sortByQuantity.addActionListener(e -> foodSalesTable.getRowSorter().toggleSortOrder(1));
        sortByRevenue.addActionListener(e -> foodSalesTable.getRowSorter().toggleSortOrder(2));

        foodSalesTable.setAutoCreateRowSorter(true);

        sortPanel.add(sortByQuantity);
        sortPanel.add(sortByRevenue);

        JPanel foodPanel = new JPanel(new BorderLayout());
        foodPanel.add(foodScroll, BorderLayout.CENTER);
        foodPanel.add(sortPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, serverScroll, foodPanel);
        splitPane.setResizeWeight(0.5);

        add(splitPane, BorderLayout.CENTER);
    }
}