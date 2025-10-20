package org.howard.edu.lsp.assignment3.pipeline;

import java.util.List;

/**
 * Abstraction for loading domain objects into a target.
 *
 * @param <T> The domain model type
 */
public interface Loader<T> {
    /**
     * Load domain objects into the target.
     *
     * @param items list of domain objects
     * @return number of records written
     * @throws Exception if loading fails
     */
    int load(List<T> items) throws Exception;
}
