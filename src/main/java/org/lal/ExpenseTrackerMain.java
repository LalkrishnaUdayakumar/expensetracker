package org.lal;

import org.lal.ui.ExpenseTrackerApp;

import javax.swing.*;

public class ExpenseTrackerMain {
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch the application using SwingUtilities
        SwingUtilities.invokeLater(() -> {
            ExpenseTrackerApp app = new ExpenseTrackerApp();
            app.setVisible(true);
        });
    }
}

// File structure for Expense Tracker application

// 1. Model classes
// src/model/Expense.java - represents a single expense transaction
// src/model/Category.java - represents expense categories
// src/model/ExpenseManager.java - handles the expense data and operations

// 2. UI classes
// src/ui/ExpenseTrackerApp.java - main application class
// src/ui/AddExpensePanel.java - panel for adding new expenses
// src/ui/ExpenseListPanel.java - panel displaying list of expenses
// src/ui/SummaryPanel.java - panel showing expense summaries and charts

// 3. Utility classes
// src/util/FileHandler.java - handles saving and loading expense data
// src/util/DateUtil.java - utility methods for date handling

// 4. Main class
// src/ExpenseTrackerMain.java - application entry point