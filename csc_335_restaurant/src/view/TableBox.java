package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.Controller;
import model.Restaurant;
import model.Table;

public class TableBox extends JPanel {
    private int tableNum;
    private Controller controller;
    private JLabel infoLabel;

    private boolean selected = false;
    private int groupId;

    public TableBox(int tableNum, Controller c) {
        this.tableNum = tableNum;
        this.groupId = 0;
        controller = c;

        setPreferredSize(new Dimension(120, 100));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setLayout(new BorderLayout());

        // 🔹 Fetch the table and get its capacity
        Table table = controller.getTableByNumberCopy(tableNum);
        int capacity = (table != null) ? table.getMaxCapacity() : 0;

        // 🔹 Display table number and capacity in the title
        JLabel titleLabel = new JLabel("Table " + tableNum + " (Capacity: " + capacity + ")", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        infoLabel = new JLabel("", SwingConstants.CENTER);
        add(infoLabel, BorderLayout.CENTER);

        // Add selection logic
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                selected = !selected;
                setBorder(BorderFactory.createLineBorder(selected ? Color.BLUE : Color.BLACK, 3));
                repaint();
            }
        });

        refreshStatus();
    }

    public void refreshStatus() {
        Table table = controller.getTableByNumberCopy(tableNum);

        if (table == null || !table.isOccupied()) {
            // Table doesn't exist or is vacant
            setBackground(Color.WHITE);
            groupId = 0;  // Reset groupId since no group is assigned
            String serverName = (table != null) ? table.getAssignedServerName() : "None";

            infoLabel.setText("<html>Server: " + (serverName.isEmpty() ? "None" : serverName) +
                              "<br>Group: None</html>");
        } else {
            // Table is occupied
            String serverName = table.getAssignedServerName();
            groupId = table.getGroupId();

            if (table.isOrderTaken()) {
                setBackground(Color.YELLOW);
            } else {
                setBackground(Color.RED);
            }

            infoLabel.setText("<html>Server: " + (serverName.isEmpty() ? "None" : serverName) +
                              "<br>Group: " + groupId + "</html>");
        }
        repaint();
    }

    public int getTableNum() {
        return tableNum;
    }
    
    public int getGroupId() {
        return groupId;
    }

    public boolean isSelected() {
        return selected;
    }
}
