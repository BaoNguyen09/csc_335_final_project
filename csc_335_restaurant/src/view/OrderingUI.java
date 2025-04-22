package view;

import model.Menu;
import model.Food;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class OrderingUI extends JFrame {
    private Menu menuModel;
    private JTable menuTable;
    private JTable orderTable;
    private DefaultTableModel menuTableModel;
    private DefaultTableModel orderTableModel;
    private JLabel totalLabel;
    private JList<String> groupMembersList;
    private JTextField quantityField;
    private JTextField modificationsField;

    // Example group members
    private List<String> groupMembers = Arrays.asList("Alice", "Bob", "Charlie");

    public OrderingUI() {
        super("Group Ordering System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 500);
        setLocationRelativeTo(null);

        menuModel = new Menu();
        initComponents();
    }

    private void initComponents() {
        // Group Members List
        groupMembersList = new JList<>(groupMembers.toArray(new String[0]));
        groupMembersList.setBorder(BorderFactory.createTitledBorder("Group Members"));

        // Quantity and Modifications
        quantityField = new JTextField("1", 5);
        modificationsField = new JTextField(10);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);
        inputPanel.add(new JLabel("Modifications:"));
        inputPanel.add(modificationsField);

        // Menu table setup
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

        // Order table setup
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

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(payButton);

        // Total label
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(totalLabel.getFont().deriveFont(Font.BOLD, 14f));
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.add(totalLabel);

        // Layout panels
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
        double totalPrice = f.getPrice() * quantity;
        orderTableModel.addRow(new Object[]{person, f.getName(), quantity, modifications, String.format("$%.2f", totalPrice)});
        updateTotal();
    }

    private void removeFromOrder() {
        int row = orderTable.getSelectedRow();
        if (row < 0) return;
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
            JOptionPane.showMessageDialog(this, "No orders to pay.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Payment of " + totalLabel.getText() + " processed!", "Payment", JOptionPane.INFORMATION_MESSAGE);
        orderTableModel.setRowCount(0);
        updateTotal();
    }
}



