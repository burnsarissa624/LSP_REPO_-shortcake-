package org.howard.edu.lsp.assignment3.pipeline;

import java.util.List;
import java.util.Objects;

/**
 * Orchestrates Extract → Transform → Load.
 *
 * @param <T> The domain model type.
 */
public class ETLPipeline<T> {
    private final Extractor<T> extractor;
    private final Transformer<T> transformer;
    private final Loader<T> loader;

    private ETLPipeline(Extractor<T> extractor, Transformer<T> transformer, Loader<T> loader) {
        this.extractor = Objects.requireNonNull(extractor);
        this.transformer = Objects.requireNonNull(transformer);
        this.loader = Objects.requireNonNull(loader);
    }

    /**
     * Runs the pipeline end-to-end.
     * @return number of records written
     * @throws Exception if extraction, transformation, or loading fails
     */
    public int run() throws Exception {
        List<T> extracted = extractor.extract();
        List<T> transformed = transformer.apply(extracted);
        return loader.load(transformed);
    }

    /**
     * Creates a builder for an ETLPipeline.
     * @param <T> the domain model type
     * @return a new Builder instance
     */
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    /**
     * Builder for ETLPipeline.
     * @param <T> domain model type
     */
    public static class Builder<T> {
        private Extractor<T> extractor;
        private Transformer<T> transformer = list -> list; // identity by default
        private Loader<T> loader;

        /**
         * Sets the extractor component.
         * @param extractor the extractor
         * @return this builder (for chaining)
         */
        public Builder<T> extractor(Extractor<T> extractor) {
            this.extractor = extractor;
            return this;
        }

        /**
         * Sets the transformer component.
         * @param transformer the transformer
         * @return this builder (for chaining)
         */
        public Builder<T> transformer(Transformer<T> transformer) {
            this.transformer = transformer;
            return this;
        }

        /**
         * Sets the loader component.
         * @param loader the loader
         * @return this builder (for chaining)
         */
        public Builder<T> loader(Loader<T> loader) {
            this.loader = loader;
            return this;
        }

        /**
         * Builds a pipeline from the configured extractor, transformer, and loader.
         * @return a new ETLPipeline instance
         */
        public ETLPipeline<T> build() {
            return new ETLPipeline<>(extractor, transformer, loader);
        }
    }
}

