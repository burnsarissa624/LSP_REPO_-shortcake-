package org.howard.edu.lsp.assignment3.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.howard.edu.lsp.assignment3.pipeline.Extractor;

/**
 * Extracts rows from a CSV file.
 * @param <T> the domain type
 */
public class CsvExtractor<T> implements Extractor<T> {
    private final Path inputPath;
    private final Function<String, T> rowMapper;
    private final boolean hasHeader;

    /**
     * Creates a CSV extractor for a file.
     * @param inputPath path to the CSV file
     * @param rowMapper maps a line to a domain object
     */
    public CsvExtractor(Path inputPath, Function<String, T> rowMapper) {
        this(inputPath, rowMapper, true);
    }

    /**
     * Creates a CSV extractor with explicit header control.
     * @param inputPath path to the CSV file
     * @param rowMapper maps a line to a domain object
     * @param hasHeader true if the first row should be skipped
     */
    public CsvExtractor(Path inputPath, Function<String, T> rowMapper, boolean hasHeader) {
        this.inputPath = inputPath;
        this.rowMapper = rowMapper;
        this.hasHeader = hasHeader;
    }

    /**
     * Extracts rows from the CSV file into domain objects.
     * @return list of extracted objects
     * @throws IOException if the file cannot be read
     */
    @Override
    public List<T> extract() throws IOException {
        List<T> result = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(inputPath)) {
            String line;
            boolean skip = hasHeader;
            while ((line = reader.readLine()) != null) {
                if (skip) { skip = false; continue; }
                result.add(rowMapper.apply(line));
            }
        }
        return result;
    }
}
