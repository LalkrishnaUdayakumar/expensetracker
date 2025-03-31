package org.lal.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExpenseManager {
    private List<Expense> expenses;

    public ExpenseManager() {
        expenses = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void removeExpense(String id) {
        expenses.removeIf(expense -> expense.getId().equals(id));
    }

    public void updateExpense(Expense updatedExpense) {
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getId().equals(updatedExpense.getId())) {
                expenses.set(i, updatedExpense);
                break;
            }
        }
    }

    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    public Map<String, Double> getExpensesByCategory() {
        Map<String, Double> categoryMap = new HashMap<>();

        for (Expense expense : expenses) {
            String category = expense.getCategory();
            double currentAmount = categoryMap.getOrDefault(category, 0.0);
            categoryMap.put(category, currentAmount + expense.getAmount());
        }

        return categoryMap;
    }

    public Map<YearMonth, Double> getMonthlyExpenses() {
        Map<YearMonth, Double> monthlyMap = new HashMap<>();

        for (Expense expense : expenses) {
            YearMonth yearMonth = YearMonth.from(expense.getDate());
            double currentAmount = monthlyMap.getOrDefault(yearMonth, 0.0);
            monthlyMap.put(yearMonth, currentAmount + expense.getAmount());
        }

        return monthlyMap;
    }

    public double getTotalExpenses() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public List<Expense> getExpensesForMonth(YearMonth yearMonth) {
        return expenses.stream()
                .filter(expense -> YearMonth.from(expense.getDate()).equals(yearMonth))
                .collect(Collectors.toList());
    }

    public List<Expense> getExpensesByCategory(String category) {
        return expenses.stream()
                .filter(expense -> expense.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    public double getTotalForMonth(YearMonth yearMonth) {
        return getExpensesForMonth(yearMonth).stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = new ArrayList<>(expenses);
    }
}