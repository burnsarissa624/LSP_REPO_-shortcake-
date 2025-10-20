package org.howard.edu.lsp.assignment3.transform;

import java.util.List;
import org.howard.edu.lsp.assignment3.model.Product;
import org.howard.edu.lsp.assignment3.pipeline.Transformer;

/**
 * Defines transformations to apply to Product data.
 */
public class ProductTransformations {
    /**
     * Builds a transformer for products.
     * Currently applies identity (no changes).
     * @return a Transformer for Product
     */
    public static Transformer<Product> build() {
        return (List<Product> products) -> products;
    }
}
