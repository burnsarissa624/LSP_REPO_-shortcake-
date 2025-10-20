package org.howard.edu.lsp.assignment3.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import org.howard.edu.lsp.assignment3.pipeline.Loader;

/**
 * Loads rows into a CSV file.
 * @param <T> the domain type
 */
public class CsvLoader<T> implements Loader<T> {
    private final Path outputPath;
    private final Function<T, String> rowMapper;
    private final String header;

    /**
     * Creates a CSV loader for writing rows.
     * @param outputPath path to write the CSV file
     * @param rowMapper converts a domain object into a row string
     */
    public CsvLoader(Path outputPath, Function<T, String> rowMapper) {
        this(outputPath, rowMapper, null);
    }

    /**
     * Creates a CSV loader with a header.
     * @param outputPath path to write the CSV file
     * @param rowMapper converts a domain object into a row string
     * @param header optional header row, may be null
     */
    public CsvLoader(Path outputPath, Function<T, String> rowMapper, String header) {
        this.outputPath = outputPath;
        this.rowMapper = rowMapper;
        this.header = header;
    }

    /**
     * Writes objects into the CSV file.
     * @param data list of objects to write
     * @return number of rows written
     * @throws IOException if the file cannot be written
     */
    @Override
    public int load(List<T> data) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
            if (header != null) {
                writer.write(header);
                writer.newLine();
            }
            for (T item : data) {
                writer.write(rowMapper.apply(item));
                writer.newLine();
            }
        }
        return data.size();
    }
}
