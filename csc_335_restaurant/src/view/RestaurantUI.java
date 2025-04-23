package view;

import model.Menu;
import view.OrderingUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantUI extends JFrame {
    private JList<String> serverList;
    private DefaultListModel<String> serverListModel;
    private JList<String> groupList;
    private DefaultListModel<String> groupListModel;
    private List<TableBox> tables;

    public RestaurantUI() {
        super("Restaurant Floor Plan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        tables = new ArrayList<>();
        initComponents();
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
        groupListModel.addElement("Group A (3)");
        groupListModel.addElement("Group B (5)");
        groupListModel.addElement("Group C (2)");
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
        String server = serverList.getSelectedValue();
        if (selected != null && server != null) {
            selected.setServer(server);
        } else {
            JOptionPane.showMessageDialog(this, "Select a table and a server first.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void assignGroup() {
        TableBox selected = getSelectedTable();
        String group = groupList.getSelectedValue();
        if (selected != null && group != null) {
            selected.setGroup(group);
            selected.setStatus(TableBox.Status.OCCUPIED);
            groupListModel.removeElement(group);
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
            selected.setServer(null);
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
} 


