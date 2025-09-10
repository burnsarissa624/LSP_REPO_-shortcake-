package org.howard.edu.lsp.assignment2;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ETLPipeline {
    private static final Path INPUT = Paths.get("data", "products.csv");
    private static final Path OUTPUT = Paths.get("data", "transformed_products.csv");
    private static int rowsRead = 0;
    private static int rowsTransformed = 0;
    private static int rowsSkipped = 0;

    private static class MissingFileException extends Exception {
        MissingFileException(String msg) { super(msg); }
    }
// --- EXTRACT ---
private static java.util.List<String[]> extract(java.nio.file.Path inputPath)
        throws MissingFileException, java.io.IOException {
    if (!java.nio.file.Files.exists(inputPath)) {
        throw new MissingFileException(
            "Input file not found at " + inputPath + ". Run from project root and ensure data/products.csv exists."
        );
    }
    java.util.List<String[]> rows = new java.util.ArrayList<>();
    try (java.io.BufferedReader br = java.nio.file.Files.newBufferedReader(
            inputPath, java.nio.charset.StandardCharsets.UTF_8)) {
        String line;
        while ((line = br.readLine()) != null) {
            rows.add(line.split(",", -1)); // simple CSV per spec
        }
    }
    rowsRead = Math.max(0, rows.size() - 1); // exclude header
    return rows;
}
// --- LOAD ---
private static void load(java.nio.file.Path outputPath, java.util.List<String[]> rows)
        throws java.io.IOException {
    java.nio.file.Files.createDirectories(outputPath.getParent());
    try (java.io.BufferedWriter bw = java.nio.file.Files.newBufferedWriter(
            outputPath,
            java.nio.charset.StandardCharsets.UTF_8,
            java.nio.file.StandardOpenOption.CREATE,
            java.nio.file.StandardOpenOption.TRUNCATE_EXISTING)) {
        for (String[] r : rows) {
            bw.write(String.join(",", r));
            bw.newLine();
        }
    }
}
// --- Helper: compute price range ---
private static String computePriceRange(java.math.BigDecimal price) {
    if (price.compareTo(new java.math.BigDecimal("10.00")) <= 0) return "Low";
    if (price.compareTo(new java.math.BigDecimal("100.00")) <= 0) return "Medium";
    if (price.compareTo(new java.math.BigDecimal("500.00")) <= 0) return "High";
    return "Premium";
}
// --- TRANSFORM ---
private static java.util.List<String[]> transform(java.util.List<String[]> input) {
    java.util.List<String[]> out = new java.util.ArrayList<>();
    // Always include required header
    out.add(new String[] { "ProductID", "Name", "Price", "Category", "PriceRange" });

    if (input == null || input.isEmpty()) return out;

    for (int i = 1; i < input.size(); i++) {
        String[] row = input.get(i);
        if (row == null || row.length < 4) { rowsSkipped++; continue; }

        String productIdStr = row[0].trim();
        String name        = row[1].trim();
        String priceStr    = row[2].trim();
        String category    = row[3].trim();

        if (productIdStr.isEmpty() || name.isEmpty() || priceStr.isEmpty() || category.isEmpty()) {
            rowsSkipped++; continue;
        }

        int productId;
        java.math.BigDecimal price;
        try {
            productId = Integer.parseInt(productIdStr);
            price = new java.math.BigDecimal(priceStr);
        } catch (NumberFormatException nfe) {
            rowsSkipped++; continue;
        }

        // 1) Uppercase name
        String finalName = name.toUpperCase();

        // 2) Discount if Electronics (10%), then round HALF_UP to 2 decimals
        String originalCategory = category;
        java.math.BigDecimal finalPrice = price;
        if ("Electronics".equalsIgnoreCase(category)) {
            finalPrice = price.multiply(new java.math.BigDecimal("0.90"));
        }
        finalPrice = finalPrice.setScale(2, java.math.RoundingMode.HALF_UP);

        // 3) Recategorize if > 500 after discount and originally Electronics
        String finalCategory = category;
        if ("Electronics".equalsIgnoreCase(originalCategory)
                && finalPrice.compareTo(new java.math.BigDecimal("500.00")) > 0) {
            finalCategory = "Premium Electronics";
        } else if ("electronics".equalsIgnoreCase(finalCategory)) {
            finalCategory = "Electronics"; // normalize casing
        }

        // 4) Price range from final price
        String priceRange = computePriceRange(finalPrice);

        out.add(new String[] {
            String.valueOf(productId),
            finalName,
            finalPrice.toPlainString(),
            finalCategory,
            priceRange
        });
        rowsTransformed++;
    }
    return out;
}

   public static void main(String[] args) {
    try {
        java.util.List<String[]> rows = extract(INPUT);
        java.util.List<String[]> transformed = transform(rows);
        load(OUTPUT, transformed);
        printSummary(OUTPUT);
    } catch (MissingFileException mfe) {
        System.err.println("[ERROR] " + mfe.getMessage());
    } catch (Exception e) {
        System.err.println("[ERROR] Unexpected failure: " + e.getMessage());
        e.printStackTrace(System.err);
    }
}
private static void printSummary(java.nio.file.Path outputPath) {
    System.out.println("==== ETL Run Summary ====");
    System.out.println("Rows read (excluding header): " + rowsRead);
    System.out.println("Rows transformed: " + rowsTransformed);
    System.out.println("Rows skipped: " + rowsSkipped);
    System.out.println("Output written to: " + outputPath);
}

}

