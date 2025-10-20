package org.howard.edu.lsp.assignment3.model;

import java.util.Objects;

/**
 * Domain model for a Product row.
 * Represents one record from the products CSV file.
 */
public class Product {
    private final String id;
    private final String name;
    private final String category;
    private final String price; // keep as String if Assignment 2 treated price as text

    /**
     * Constructs a new Product.
     * @param id product ID
     * @param name product name
     * @param category product category
     * @param price product price
     */
    public Product(String id, String name, String category, String price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    /**
     * Parse a CSV line into a Product object (no header).
     * @param line a CSV line
     * @return a Product instance
     */
    public static Product fromCsv(String line) {
        String[] parts = line.split(",", -1);
        String id = parts.length > 0 ? parts[0].trim() : "";
        String name = parts.length > 1 ? parts[1].trim() : "";
        String category = parts.length > 2 ? parts[2].trim() : "";
        String price = parts.length > 3 ? parts[3].trim() : "";
        return new Product(id, name, category, price);
    }

    /**
     * Convert back to a CSV row string.
     * @return CSV row representation of this product
     */
    public String toCsvRow() {
        return String.join(",", safe(id), safe(name), safe(category), safe(price));
    }

    private static String safe(String s) {
        return s == null ? "" : s;
    }

    /** @return product ID */
    public String getId() { return id; }

    /** @return product name */
    public String getName() { return name; }

    /** @return product category */
    public String getCategory() { return category; }

    /** @return product price */
    public String getPrice() { return price; }

    /**
     * Returns a copy of this product with a new name.
     * @param newName the new product name
     * @return new Product with updated name
     */
    public Product withName(String newName) {
        return new Product(this.id, newName, this.category, this.price);
    }

    /**
     * Returns a copy of this product with a new category.
     * @param newCategory the new category
     * @return new Product with updated category
     */
    public Product withCategory(String newCategory) {
        return new Product(this.id, this.name, newCategory, this.price);
    }

    /**
     * Returns a copy of this product with a new price.
     * @param newPrice the new price
     * @return new Product with updated price
     */
    public Product withPrice(String newPrice) {
        return new Product(this.id, this.name, this.category, newPrice);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product)) return false;
        Product other = (Product) o;
        return Objects.equals(id, other.id)
            && Objects.equals(name, other.name)
            && Objects.equals(category, other.category)
            && Objects.equals(price, other.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, price);
    }
}
