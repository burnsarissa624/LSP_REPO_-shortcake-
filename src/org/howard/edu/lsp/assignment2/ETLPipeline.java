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

    public static void main(String[] args) {
        System.out.println("ETL skeleton started.");
    }
}

