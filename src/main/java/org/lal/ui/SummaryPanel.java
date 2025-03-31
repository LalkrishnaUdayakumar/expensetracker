package org.lal.ui;


import org.lal.model.Category;
import org.lal.model.ExpenseManager;

import javax.swing.*;
import java.awt.*;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class SummaryPanel extends JPanel {
    private ExpenseTrackerApp app;
    private JPanel chartPanel;
    private JPanel statsPanel;
    
    public SummaryPanel(ExpenseTrackerApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        
        // Create stats panel
        statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
        
        // Create chart panel
        chartPanel = new JPanel();
        chartPanel.setLayout(new BorderLayout());
        chartPanel.setBorder(BorderFactory.createTitledBorder("Expense Breakdown"));
        
        // Add panels to main panel
        add(statsPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);
    }
    
    public void updateCharts(ExpenseManager expenseManager) {
        // Update stats panel
        statsPanel.removeAll();
        
        double totalExpenses = expenseManager.getTotalExpenses();
        JLabel totalLabel = new JLabel(String.format("Total Expenses: ₹%.2f", totalExpenses));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsPanel.add(totalLabel);
        statsPanel.add(Box.createVerticalStrut(10));
        
        // Current month expenses
        YearMonth currentMonth = YearMonth.now();
        double monthlyTotal = expenseManager.getTotalForMonth(currentMonth);
        JLabel monthlyLabel = new JLabel(
            String.format("Current Month (%s) Expenses: ₹%.2f",
            currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")), 
            monthlyTotal)
        );
        monthlyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsPanel.add(monthlyLabel);
        
        // Update chart panel
        chartPanel.removeAll();
        
        // Create category breakdown chart
        JPanel categoryChart = createCategoryChart(expenseManager);
        chartPanel.add(categoryChart, BorderLayout.CENTER);
        
        // Refresh the panel
        revalidate();
        repaint();
    }
    
    private JPanel createCategoryChart(ExpenseManager expenseManager) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        // Get category data
        Map<String, Double> categoryData = expenseManager.getExpensesByCategory();
        if (categoryData.isEmpty()) {
            panel.add(new JLabel("No expense data to display", JLabel.CENTER));
            return panel;
        }
        
        // Create a simple bar chart
        JPanel barChart = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int width = getWidth();
                int height = getHeight();
                int barWidth = width / (categoryData.size() * 2);
                int spacing = barWidth / 2;
                
                // Find the maximum value for scaling
                double maxValue = categoryData.values().stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
                if (maxValue == 0.0) return;
                
                // Draw the bars
                int x = spacing;
                for (Map.Entry<String, Double> entry : categoryData.entrySet()) {
                    String category = entry.getKey();
                    double value = entry.getValue();
                    
                    // Calculate bar height (scaled)
                    int barHeight = (int) ((value / maxValue) * (height - 50));
                    
                    // Draw the bar
                    g2d.setColor(Category.getColorForCategory(category));
                    g2d.fillRect(x, height - barHeight - 30, barWidth, barHeight);
                    
                    // Draw category name
                    g2d.setColor(Color.BLACK);
                    g2d.drawString(category, x, height - 15);
                    
                    // Draw value
                    g2d.drawString(String.format("₹%.2f", value), x, height - 30);
                    
                    x += barWidth + spacing;
                }
            }
        };
        
        barChart.setPreferredSize(new Dimension(400, 300));
        panel.add(barChart, BorderLayout.CENTER);
        
        // Add a legend
        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new GridLayout(0, 2, 10, 5));
        legendPanel.setBorder(BorderFactory.createTitledBorder("Categories"));
        
        for (String category : categoryData.keySet()) {
            JPanel colorBox = new JPanel();
            colorBox.setBackground(Category.getColorForCategory(category));
            colorBox.setPreferredSize(new Dimension(20, 20));
            
            JLabel categoryLabel = new JLabel(category);
            
            JPanel entryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            entryPanel.add(colorBox);
            entryPanel.add(categoryLabel);
            
            legendPanel.add(entryPanel);
        }
        
        panel.add(legendPanel, BorderLayout.SOUTH);
        return panel;
    }
}