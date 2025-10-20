package org.howard.edu.lsp.assignment3.pipeline;

import java.util.List;

/**
 * Abstraction for transforming domain objects.
 *
 * @param <T> The domain model type
 */
public interface Transformer<T> {
    /**
     * Apply a transformation to a list of domain objects.
     *
     * @param input list of input objects
     * @return transformed list of objects
     */
    List<T> apply(List<T> input);
}
