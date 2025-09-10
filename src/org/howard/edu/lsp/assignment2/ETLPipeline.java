package org.howard.edu.lsp.assignment2;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ETLPipeline {
    private static final Path INPUT = Paths.get("data", "products.csv");
    private static final Path OUTPUT = Paths.get("data", "transformed_products.csv");

    private static int rowsRead = 0;
    private static int rowsTransformed = 0;
    private static int rowsSkipped = 0;

    public static void main(String[] args) {
        try {
            List<String[]> rows = extract(INPUT);
            List<String[]> transformed = transform(rows);
            load(OUTPUT, transformed);
            printSummary(OUTPUT);
        } catch (MissingFileException mfe) {
            System.err.println("[ERROR] " + mfe.getMessage());
        } catch (Exception e) {
            System.err.println("[ERROR] Unexpected failure: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    /** Extract CSV into a list of rows (String arrays). */
    private static List<String[]> extract(Path inputPath) throws MissingFileException, IOException {
        if (!Files.exists(inputPath)) {
            throw new MissingFileException(
                "Input file not found at " + inputPath + ". Run from project root and ensure data/products.csv exists.");
        }
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                rows.add(line.split(",", -1)); // no embedded commas per spec
            }
        }
        rowsRead = Math.max(0, rows.size() - 1); // exclude header
        return rows;
    }

    /** Apply all required transformations. */
    private static List<String[]> transform(List<String[]> input) {
        List<String[]> out = new ArrayList<>();
        // always include header
        out.add(new String[] { "ProductID", "Name", "Price", "Category", "PriceRange" });

        if (input == null || input.isEmpty()) return out;

        for (int i = 1; i < input.size(); i++) {
            String[] row = input.get(i);
            if (row == null || row.length < 4) { rowsSkipped++; continue; }

            String productIdStr = row[0].trim();
            String name = row[1].trim();
            String priceStr = row[2].trim();
            String category = row[3].trim();

            if (productIdStr.isEmpty() || name.isEmpty() || priceStr.isEmpty() || category.isEmpty()) {
                rowsSkipped++; continue;
            }

            int productId;
            BigDecimal price;
            try {
                productId = Integer.parseInt(productIdStr);
                price = new BigDecimal(priceStr);
            } catch (NumberFormatException nfe) {
                rowsSkipped++; continue;
            }

            // 1) Uppercase name
            String finalName = name.toUpperCase();

            // 2) Discount if Electronics
            String originalCategory = category;
            BigDecimal finalPrice = price;
            if ("Electronics".equalsIgnoreCase(category)) {
                finalPrice = price.multiply(new BigDecimal("0.90"));
            }
            finalPrice = finalPrice.setScale(2, RoundingMode.HALF_UP);

            // 3) Recategorize if >500 and originally Electronics
            String finalCategory = category;
            if ("Electronics".equalsIgnoreCase(originalCategory)
                    && finalPrice.compareTo(new BigDecimal("500.00")) > 0) {
                finalCategory = "Premium Electronics";
            } else if ("electronics".equalsIgnoreCase(finalCategory)) {
                finalCategory = "Electronics";
            }

            // 4)  PriceRange
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

    /** Write transformed data CSV. */
    private static void load(Path outputPath, List<String[]> rows) throws IOException {
        Files.createDirectories(outputPath.getParent());
        try (BufferedWriter bw = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (String[] r : rows) {
                bw.write(String.join(",", r));
                bw.newLine();
            }
        }
    }

    /** Compute price range on final price. */
    private static String computePriceRange(BigDecimal price) {
        if (price.compareTo(new BigDecimal("10.00")) <= 0) return "Low";
        if (price.compareTo(new BigDecimal("100.00")) <= 0) return "Medium";
        if (price.compareTo(new BigDecimal("500.00")) <= 0) return "High";
        return "Premium";
    }

    /** Print summary of ETL run. */
    private static void printSummary(Path outputPath) {
        System.out.println("==== ETL Run Summary ====");
        System.out.println("Rows read (excluding header): " + rowsRead);
        System.out.println("Rows transformed: " + rowsTransformed);
        System.out.println("Rows skipped: " + rowsSkipped);
        System.out.println("Output written to: " + outputPath);
    }

    /** Custom exception for missing file case. */
    private static class MissingFileException extends Exception {
        MissingFileException(String msg) { super(msg); }
    }
}
