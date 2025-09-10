package org.howard.edu.lsp.assignment2;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ETLPipeline {
    private static final Path INPUT = Paths.get("data", "products.csv");
    private static final Path OUTPUT = Paths.get("data", "transformed_products.csv");

    public static void main(String[] args) {
        System.out.println("ETL skeleton started.");
    }
}

