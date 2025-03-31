package org.lal.util;


import org.lal.model.Expense;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String DATA_FILE = "expenses.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public static void saveExpenses(List<Expense> expenses) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            // Write header
            writer.write("id,amount,category,date,description");
            writer.newLine();
            
            // Write data
            for (Expense expense : expenses) {
                writer.write(String.format("%s,%.2f,%s,%s,%s",
                        expense.getId(),
                        expense.getAmount(),
                        expense.getCategory(),
                        expense.getDate().format(DATE_FORMATTER),
                        expense.getDescription().replace(",", ";")));  // Replace commas in description
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving expenses: " + e.getMessage());
        }
    }
    
    public static List<Expense> loadExpenses() {
        List<Expense> expenses = new ArrayList<>();
        File file = new File(DATA_FILE);
        
        if (!file.exists()) {
            return expenses;  // Return empty list if file doesn't exist
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip header
            String line = reader.readLine();
            
            // Read data
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 5);  // Limit to 5 parts to handle commas in description
                
                if (parts.length >= 5) {
                    String id = parts[0];
                    double amount = Double.parseDouble(parts[1]);
                    String category = parts[2];
                    LocalDate date = LocalDate.parse(parts[3], DATE_FORMATTER);
                    String description = parts[4].replace(";", ",");  // Restore commas in description
                    
                    expenses.add(new Expense(id, amount, category, date, description));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading expenses: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing expense data: " + e.getMessage());
        }
        
        return expenses;
    }
}