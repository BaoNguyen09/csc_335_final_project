 package view;

import model.RestaurantObserver;
import model.Restaurant;
import model.Server;

import javax.swing.*;

import controller.Controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantUI extends JFrame implements RestaurantObserver {
    private JList<String> serverList;
    private DefaultListModel<String> serverListModel;
    private JList<String> groupList;
    private DefaultListModel<String> groupListModel;
    private List<TableBox> tables;
    private Controller controller;

    public RestaurantUI(Controller controller) {
        super("Restaurant Floor Plan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        tables = new ArrayList<>();
        initComponents();
        this.controller = controller;
    }

    private void initComponents() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        serverListModel = new DefaultListModel<>();
        serverListModel.addElement("Alice");
        serverListModel.addElement("Bob");
        serverListModel.addElement("Charlie");
        serverList = new JList<>(serverListModel);
        serverList.setBorder(BorderFactory.createTitledBorder("Servers"));
        leftPanel.add(serverList);

        groupListModel = new DefaultListModel<>();
        groupListModel.addElement("Group 1");
        groupListModel.addElement("Group 2");
        groupListModel.addElement("Group 3");
        groupList = new JList<>(groupListModel);
        groupList.setBorder(BorderFactory.createTitledBorder("Waiting Groups"));
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(groupList);

        JButton assignServerBtn = new JButton("Assign Server");
        assignServerBtn.addActionListener(e -> assignServer());
        JButton assignGroupBtn = new JButton("Assign Group");
        assignGroupBtn.addActionListener(e -> assignGroup());
        JButton takeOrderBtn = new JButton("Take Order");
        takeOrderBtn.addActionListener(e -> takeOrder());
        JButton addServerBtn = new JButton("Add Server");
        addServerBtn.addActionListener(e -> addServer());
        JButton removeServerBtn = new JButton("Remove Server from Table");
        removeServerBtn.addActionListener(e -> removeServerFromTable());
        JButton salesBoardBtn = new JButton("Sales Board");
        salesBoardBtn.addActionListener(e -> showSalesBoard());

        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(assignServerBtn);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(assignGroupBtn);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(takeOrderBtn);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(addServerBtn);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(removeServerBtn);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(salesBoardBtn);

        add(leftPanel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        for (int i = 1; i <= 2; i++) {
            tables.add(new TableBox("T" + i, 10));
        }
        for (int i = 3; i <= 4; i++) {
            tables.add(new TableBox("T" + i, 4));
        }
        for (int i = 5; i <= 6; i++) {
            tables.add(new TableBox("T" + i, 2));
        }
        tables.forEach(centerPanel::add);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void assignServer() {
    	TableBox selected = getSelectedTable();
        String serverName = serverList.getSelectedValue();
        if (selected != null && serverName != null) {
            // when the server is assigned to the table need to update controller
        	// get the id of the table
        	int id = Integer.valueOf(selected.getId().substring(1));
        	controller.assignServer(serverName, id);
        } else {
            JOptionPane.showMessageDialog(this, "Select a table and a server first.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void assignGroup() {
    	TableBox selected = getSelectedTable();
        String groupInfo = groupList.getSelectedValue();
        if (selected != null && groupInfo != null) {
            // when the group is assigned to table need to update controller
        	// get the id of the table
        	int id = Integer.valueOf(selected.getId().substring(1));
        	// get the groupNum from the string
        	int groupNum = Integer.valueOf(groupInfo.substring(6));
        	controller.assignGroup(groupNum, id);
        } else {
            JOptionPane.showMessageDialog(this, "Select a table and a group first.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void takeOrder() {
        TableBox selected = getSelectedTable();
        if (selected != null && selected.getStatus() == TableBox.Status.OCCUPIED) {
            selected.setStatus(TableBox.Status.ORDERED);
            OrderingUI orderFrame = new OrderingUI();
            orderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            orderFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Select an occupied table to take order.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void addServer() {
        String name = JOptionPane.showInputDialog(this, "Enter server name:");
        if (name != null && !name.trim().isEmpty()) {
            serverListModel.addElement(name.trim());
        }
    }

    private void removeServerFromTable() {
        TableBox selected = getSelectedTable();
        if (selected != null && selected.server != null) {
        	// when the server is assigned to the table need to update controller
        	// get the id of the table
        	int id = Integer.valueOf(selected.getId().substring(1));
        	// get the name of the server assigned to selected table
        	controller.assignServer(selected.getServer(), id);
        } else {
            JOptionPane.showMessageDialog(this, "Select a table with an assigned server.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showSalesBoard() {
        SwingUtilities.invokeLater(() -> new SalesUI().setVisible(true));
    }

    private TableBox getSelectedTable() {
        for (TableBox t : tables) {
            if (t.isSelected()) return t;
        }
        return null;
    }

    static class TableBox extends JPanel {
        enum Status { VACANT, OCCUPIED, ORDERED }
        private String id;
        private int capacity;
        private String server;
        private String group;
        private Status status = Status.VACANT;
        private boolean selected = false;

        public TableBox(String id, int capacity) {
            this.id = id;
            this.capacity = capacity;
            setPreferredSize(new Dimension(100, 100));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            setToolTipText(id + " (cap " + capacity + ")");

            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    selected = !selected;
                    setBorder(BorderFactory.createLineBorder(selected ? Color.BLUE : Color.BLACK, 3));
                }
            });
            repaint();
        }

        public void setServer(String server) {
            this.server = server;
            repaint();
        }

        public void setGroup(String group) {
            this.group = group;
            repaint();
        }
        
        public String getId() {
        	return this.id;
        }
        
        public String getServer() {
        	return this.server;
        }

        public void setStatus(Status status) {
            this.status = status;
            switch (status) {
                case VACANT: setBackground(Color.WHITE); break;
                case OCCUPIED: setBackground(Color.RED); break;
                case ORDERED: setBackground(Color.YELLOW); break;
            }
            repaint();
        }

        public Status getStatus() {
            return status;
        }

        public boolean isSelected() {
            return selected;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            String text = id + " (" + capacity + ")";
            FontMetrics fm = g.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(text)) / 2;
            int y = getHeight() / 2;
            g.drawString(text, x, y);
            if (server != null) {
                String s = "Serv: " + server;
                g.drawString(s, 5, getHeight() - 20);
            }
            if (group != null) {
                String gstr = "Grp: " + group;
                g.drawString(gstr, 5, getHeight() - 5);
            }
        }
    }

    @Override
    public void assignServerEvent(Server s, int tableNum) {
    	/* when the restaurant is updated and calls the update observer function
    	 * this function is called which updates the UI in accordance with the model
    	 */
    	String tableId = "T" + tableNum;
    	for (TableBox t : tables) {
    		if (t.getId().equals(tableId)) {
    			t.setServer(s.getName());
    		}
    	}
    }

    @Override
    public void assignGroupEvent(int groupId, int tableNum) {
    	/* when the restaurant is updated and calls the update observer function
    	 * this function is called which updates the UI in accordance with the model
    	 */
    	String tableId = "T" + tableNum;
    	for (TableBox t : tables) {
    		if (t.getId().equals(tableId) && t.getStatus() == TableBox.Status.VACANT) {
    			String g = "Group " + groupId;
    			t.setGroup(g);
    			t.setStatus(TableBox.Status.OCCUPIED);
    		}
    	}
    }

	@Override
	public void removeServerEvent(Server s, int tableNum) {
		/* when the restaurant is updated and calls the update observer function
    	 * this function is called which updates the UI in accordance with the model
    	 */
		String tableId = "T" + tableNum;
    	for (TableBox t : tables) {
    		if (t.getId().equals(tableId)) {
    			t.setServer(null);
    		}
    	}
	}
} 

