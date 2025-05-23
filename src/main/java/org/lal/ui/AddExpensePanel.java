package org.lal.ui;

import org.lal.model.Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

public class AddExpensePanel extends JPanel {
    private ExpenseTrackerApp app;
    private JTextField amountField;
    private JComboBox<String> categoryComboBox;
    private JTextField dateField;
    private JTextField descriptionField;

    public AddExpensePanel(ExpenseTrackerApp app) {
        this.app = app;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Add New Expense"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Amount
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Amount:"), gbc);

        gbc.gridx = 1;
        amountField = new JTextField(10);
        add(amountField, gbc);

        // Category
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Category:"), gbc);

        gbc.gridx = 1;
        List<String> categories = Category.getDefaultCategoryNames();
        categoryComboBox = new JComboBox<>(categories.toArray(new String[0]));
        add(categoryComboBox, gbc);

        // Add category button
        gbc.gridx = 2;
        gbc.gridy = 1;
        JButton addCategoryButton = new JButton("Add");
        addCategoryButton.addActionListener(this::addCategory);
        add(addCategoryButton, gbc);

        // Edit category button
        gbc.gridx = 3;
        gbc.gridy = 1;
        JButton editCategoryButton = new JButton("Edit");
        editCategoryButton.addActionListener(this::editCategory);
        add(editCategoryButton, gbc);

        // Delete category button
        gbc.gridx = 4;
        gbc.gridy = 1;
        JButton deleteCategoryButton = new JButton("Delete");
        deleteCategoryButton.addActionListener(this::deleteCategory);
        add(deleteCategoryButton, gbc);

        // Date
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Date (YYYY-MM-DD):"), gbc);

        gbc.gridx = 1;
        dateField = new JTextField(10);
        dateField.setText(LocalDate.now().toString()); // Default to current date
        add(dateField, gbc);

        // Description
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        descriptionField = new JTextField(20);
        add(descriptionField, gbc);

        // Add button
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        JButton addButton = new JButton("Add Expense");
        addButton.addActionListener(this::addExpense);
        add(addButton, gbc);
    }

    private void addExpense(ActionEvent e) {
        try {
            // Validate and parse amount
            double amount = Double.parseDouble(amountField.getText().trim());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be greater than zero", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get category
            String category = (String) categoryComboBox.getSelectedItem();

            // Validate and parse date
            LocalDate date;
            try {
                date = LocalDate.parse(dateField.getText().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Use YYYY-MM-DD", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get description
            String description = descriptionField.getText().trim();

            // Add expense
            app.addExpense(amount, category, date, description);

            // Clear fields except date
            amountField.setText("");
            descriptionField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount format", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCategory(ActionEvent e) {
        String categoryName = JOptionPane.showInputDialog(this, "Enter new category name:");
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            Color newColor = Color.ORANGE; // Default color for new categories
            Category.addCategory(categoryName, newColor);
            categoryComboBox.addItem(categoryName);
        }
    }

    private void editCategory(ActionEvent e) {
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        String newCategoryName = JOptionPane.showInputDialog(this, "Enter new category name:", selectedCategory);
        if (newCategoryName != null && !newCategoryName.trim().isEmpty()) {
            Color newColor = Color.ORANGE; // Default color for edited categories
            Category.editCategory(selectedCategory, newCategoryName, newColor);
            categoryComboBox.removeItem(selectedCategory);
            categoryComboBox.addItem(newCategoryName);
        }
    }

    private void deleteCategory(ActionEvent e) {
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this category?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Category.deleteCategory(selectedCategory);
            categoryComboBox.removeItem(selectedCategory);
        }
    }
}