package org.lal.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Category {
    private String name;
    private Color color;
    
    // Default categories
    private static final List<Category> DEFAULT_CATEGORIES = Arrays.asList(
        new Category("Food", new Color(46, 204, 113)),
        new Category("Transportation", new Color(52, 152, 219)),
        new Category("Entertainment", new Color(155, 89, 182)),
        new Category("Utilities", new Color(231, 76, 60)),
        new Category("Housing", new Color(243, 156, 18)),
        new Category("Healthcare", new Color(26, 188, 156)),
        new Category("Shopping", new Color(241, 196, 15)),
        new Category("Other", new Color(149, 165, 166))
    );
    
    public Category(String name, Color color) {
        this.name = name;
        this.color = color;
    }
    
    public String getName() {
        return name;
    }
    
    public Color getColor() {
        return color;
    }
    
    public static List<Category> getDefaultCategories() {
        return new ArrayList<>(DEFAULT_CATEGORIES);
    }
    
    public static List<String> getDefaultCategoryNames() {
        List<String> names = new ArrayList<>();
        for (Category category : DEFAULT_CATEGORIES) {
            names.add(category.getName());
        }
        return names;
    }
    
    public static Color getColorForCategory(String categoryName) {
        for (Category category : DEFAULT_CATEGORIES) {
            if (category.getName().equals(categoryName)) {
                return category.getColor();
            }
        }
        return Color.GRAY; // Default color if category not found
    }
    public static void addCategory(String name, Color color) {
        DEFAULT_CATEGORIES.add(new Category(name, color));
    }
    public static void editCategory(String oldName, String newName, Color newColor) {
        for (Category category : DEFAULT_CATEGORIES) {
            if (category.getName().equals(oldName)) {
                category.name = newName;
                category.color = newColor;
                break;
            }
        }
    }

    public static void deleteCategory(String name) {
        DEFAULT_CATEGORIES.removeIf(category -> category.getName().equals(name));
    }
}

