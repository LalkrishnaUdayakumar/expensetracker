package org.lal.ui;

import org.lal.model.Category;
import org.lal.model.Expense;
import org.lal.model.ExpenseManager;
import org.lal.util.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;

public class ExpenseTrackerApp extends JFrame {
    private ExpenseManager expenseManager;
    private AddExpensePanel addExpensePanel;
    private ExpenseListPanel expenseListPanel;
    private SummaryPanel summaryPanel;

    public ExpenseTrackerApp() {
        // Initialize model
        expenseManager = new ExpenseManager();

        // Load saved expenses
        expenseManager.setExpenses(FileHandler.loadExpenses());

        // Set up the frame
        setTitle("Expense Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create components
        addExpensePanel = new AddExpensePanel(this);
        expenseListPanel = new ExpenseListPanel(this);
        summaryPanel = new SummaryPanel(this);

        // Set up layout
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(addExpensePanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(expenseListPanel), BorderLayout.CENTER);

        tabbedPane.addTab("Expenses", mainPanel);
        tabbedPane.addTab("Summary", summaryPanel);

        add(tabbedPane);

        // Add window listener to save data when closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                FileHandler.saveExpenses(expenseManager.getAllExpenses());
            }
        });

        // Update UI components
        updateExpenseList();
        updateSummary();
    }

    public void addExpense(double amount, String category, LocalDate date, String description) {
        Expense expense = new Expense(amount, category, date, description);
        expenseManager.addExpense(expense);
        updateExpenseList();
        updateSummary();
    }

    public void removeExpense(String id) {
        expenseManager.removeExpense(id);
        updateExpenseList();
        updateSummary();
    }

    public void updateExpenseList() {
        expenseListPanel.setExpenses(expenseManager.getAllExpenses());
    }

    public void updateSummary() {
        summaryPanel.updateCharts(expenseManager);
    }

    public ExpenseManager getExpenseManager() {
        return expenseManager;
    }
}