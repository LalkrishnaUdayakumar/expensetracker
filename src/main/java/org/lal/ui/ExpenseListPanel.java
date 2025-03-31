package org.lal.ui;

import org.lal.model.Expense;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExpenseListPanel extends JPanel {
    private ExpenseTrackerApp app;
    private JTable expenseTable;
    private DefaultTableModel tableModel;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ExpenseListPanel(ExpenseTrackerApp app) {
        this.app = app;
        setLayout(new BorderLayout());

        // Create table model with non-editable cells
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Set column names
        tableModel.addColumn("Date");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Category");
        tableModel.addColumn("Description");
        tableModel.addColumn("ID"); // Hidden column for ID

        // Create table
        expenseTable = new JTable(tableModel);
        expenseTable.getColumnModel().getColumn(4).setMinWidth(0);
        expenseTable.getColumnModel().getColumn(4).setMaxWidth(0);
        expenseTable.getColumnModel().getColumn(4).setWidth(0);

        // Add table to a scroll pane
        add(new JScrollPane(expenseTable), BorderLayout.CENTER);

        // Add delete button
        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(e -> deleteSelectedExpense());
        add(deleteButton, BorderLayout.SOUTH);
    }

    public void setExpenses(List<Expense> expenses) {
        // Clear table
        tableModel.setRowCount(0);

        // Add expenses to table
        for (Expense expense : expenses) {
            tableModel.addRow(new Object[]{
                    expense.getDate().format(DATE_FORMATTER),
                    String.format("$%.2f", expense.getAmount()),
                    expense.getCategory(),
                    expense.getDescription(),
                    expense.getId()
            });
        }
    }

    private void deleteSelectedExpense() {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow >= 0) {
            String id = (String) tableModel.getValueAt(selectedRow, 4);
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this expense?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                app.removeExpense(id);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an expense to delete", "No Selection", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}