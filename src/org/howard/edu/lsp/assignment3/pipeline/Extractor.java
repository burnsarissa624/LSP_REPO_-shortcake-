package org.howard.edu.lsp.assignment3.pipeline;

import java.util.List;

/**
 * Abstraction for extracting domain objects from a source.
 *
 * @param <T> The domain model type
 */
public interface Extractor<T> {
    /**
     * Extract domain objects from the data source.
     *
     * @return list of extracted objects
     * @throws Exception if extraction fails
     */
    List<T> extract() throws Exception;
}
