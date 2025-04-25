package view;

import model.Group;
import model.Restaurant;
import model.Server;
import model.Table;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestaurantUI extends JFrame {
    private JList<String> serverList;
    private DefaultListModel<String> serverListModel;
    private JList<String> groupList;
    private DefaultListModel<String> groupListModel;
    private List<TableBox> tables;
    private Restaurant restaurant;

    public RestaurantUI(Restaurant restaurant) {
        super("Restaurant Floor Plan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        this.restaurant = restaurant;

        initComponents();
    }

    private void initComponents() {
        // Left Panel Setup
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Server List
        serverListModel = new DefaultListModel<>();
        serverList = new JList<>(serverListModel);
        serverList.setBorder(BorderFactory.createTitledBorder("Servers"));
        leftPanel.add(serverList);

        // Group List
        groupListModel = new DefaultListModel<>();
        groupList = new JList<>(groupListModel);
        groupList.setBorder(BorderFactory.createTitledBorder("Waiting Groups"));
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(groupList);

        // Buttons
        JButton addServerBtn = new JButton("Add Server");
        addServerBtn.addActionListener(e -> addServer());

        JButton addGroupBtn = new JButton("Add Group");
        addGroupBtn.addActionListener(e -> addGroup());

        JButton assignServerBtn = new JButton("Assign Server");
        assignServerBtn.addActionListener(e -> assignServer());

        JButton assignGroupBtn = new JButton("Assign Group");
        assignGroupBtn.addActionListener(e -> assignGroup());

        JButton takeOrderBtn = new JButton("Take Order");
        takeOrderBtn.addActionListener(e -> takeOrder());

        JButton salesBoardBtn = new JButton("Sales Board");
        salesBoardBtn.addActionListener(e -> showSalesBoard());

        // Add buttons to left panel
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(assignServerBtn);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(assignGroupBtn);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(takeOrderBtn);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(addServerBtn);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(addGroupBtn);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(salesBoardBtn);

        add(leftPanel, BorderLayout.WEST);

        // Center Panel for Table Display
        Map<Integer, Table> restaurantTables = restaurant.getTables();
        int tableCount = restaurantTables.size();

        int rows = (int) Math.ceil(tableCount / 3.0);
        JPanel centerPanel = new JPanel(new GridLayout(rows, 3, 20, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        tables = new ArrayList<>();
        for (Integer tableNum : restaurantTables.keySet()) {
            TableBox tableBox = new TableBox(tableNum, restaurant);
            tables.add(tableBox);
            centerPanel.add(tableBox);
        }

        add(centerPanel, BorderLayout.CENTER);
    }

    // ----------------- List Population -----------------

    public void populateServerList(Map<String, Server> servers) {
        serverListModel.clear();
        for (String serverName : servers.keySet()) {
            serverListModel.addElement(serverName);
        }
    }

    public void populateGroupList(Map<Integer, Group> waitingList) {
        groupListModel.clear();
        for (Map.Entry<Integer, Group> entry : waitingList.entrySet()) {
            int groupId = entry.getKey();
            Group group = entry.getValue();
            groupListModel.addElement("Group " + groupId + " (" + group.getGroupSize() + ")");
        }
    }

    // ----------------- Table Updating Logic -----------------

    public void refreshAllTables() {
        tables.forEach(TableBox::refreshStatus);
    }

    private TableBox getSelectedTable() {
        for (TableBox t : tables) {
            if (t.isSelected()) return t;
        }
        return null;
    }

    // ----------------- Button Actions -----------------

    private void assignServer() {
        TableBox selected = getSelectedTable();
        String serverName = serverList.getSelectedValue();

        if (selected != null && serverName != null) {
            int tableNum = selected.getTableNum();
            Table table = restaurant.getTableByNumber(tableNum);

            if (table.assignServer(serverName)) {
                JOptionPane.showMessageDialog(this, "Server assigned to Table " + tableNum);
            } else {
                JOptionPane.showMessageDialog(this, "Table already has a server.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            refreshAllTables();
        } else {
            JOptionPane.showMessageDialog(this, "Select a table and a server first.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void assignGroup() {
        TableBox selected = getSelectedTable();
        String groupEntry = groupList.getSelectedValue();

        if (selected != null && groupEntry != null) {
            int tableNum = selected.getTableNum();
            int groupId = Integer.parseInt(groupEntry.split(" ")[1]);

            Group group = restaurant.getWaitlist().get(groupId);
            Table table = restaurant.getTableByNumber(tableNum);

            if (table.assignGroup(group)) {
                groupListModel.removeElement(groupEntry);
                JOptionPane.showMessageDialog(this, "Group assigned to Table " + tableNum);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to assign group. Check capacity or occupancy.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            refreshAllTables();
        } else {
            JOptionPane.showMessageDialog(this, "Select a table and a group first.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void takeOrder() {
        TableBox selected = getSelectedTable();
        if (selected != null) {
            int tableNum = selected.getTableNum();
            Table table = restaurant.getTableByNumber(tableNum);

            if (table.isOccupied() && !table.isOrderTaken()) {
                Group group = table.getGroup();  // If this doesn't exist, adjust to lookup by groupId
                group.markOrderTaken();

                OrderingUI orderFrame = new OrderingUI();
                orderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                orderFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Select an occupied table without an order.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            refreshAllTables();
        } else {
            JOptionPane.showMessageDialog(this, "Select a table first.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void addServer() {
        String name = JOptionPane.showInputDialog(this, "Enter server name:");
        if (name != null && !name.trim().isEmpty()) {
            serverListModel.addElement(name.trim());
            restaurant.addServer(name.trim());  // Assuming such a method exists
        }
    }

    private void addGroup() {
        String sizeStr = JOptionPane.showInputDialog(this, "Enter group size:");
        try {
            int size = Integer.parseInt(sizeStr);
            Group group = new Group(size);  // Assuming constructor auto-generates ID
            restaurant.addGroup(group);
            populateGroupList(restaurant.getWaitlist());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid group size.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showSalesBoard() {
        SwingUtilities.invokeLater(() -> new SalesUI(restaurant).setVisible(true));
    }
}


