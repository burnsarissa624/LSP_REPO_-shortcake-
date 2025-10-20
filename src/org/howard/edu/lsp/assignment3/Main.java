package org.howard.edu.lsp.assignment3;

import java.nio.file.Paths;
import org.howard.edu.lsp.assignment3.io.CsvExtractor;
import org.howard.edu.lsp.assignment3.io.CsvLoader;
import org.howard.edu.lsp.assignment3.model.Product;
import org.howard.edu.lsp.assignment3.pipeline.ETLPipeline;
import org.howard.edu.lsp.assignment3.pipeline.ValidationException;
import org.howard.edu.lsp.assignment3.transform.ProductTransformations;

/**
 * Entry point for Assignment 3 ETL pipeline.
 * Configures extractor, transformer, and loader for products.csv.
 */
public class Main {
    /**
     * Runs the ETL pipeline using Assignment 3 design.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        try {
            var input = Paths.get("data/products.csv");
            var output = Paths.get("data/transformed_products.csv");

            // Build and run the pipeline
            ETLPipeline<Product> pipeline = ETLPipeline.<Product>builder()
                    .extractor(new CsvExtractor<Product>(input, Product::fromCsv))
                    .transformer(ProductTransformations.build()) // identity for now
                    .loader(new CsvLoader<Product>(output, Product::toCsvRow))
                    .build();

            int rows = pipeline.run();
            System.out.println("ETL complete. Rows written: " + rows);

        } catch (ValidationException ve) {
            System.err.println("Validation failed: " + ve.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
