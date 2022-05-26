package org.ignast.challenge.concordance.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public class LocalFileBasedCommunication {

    private static final String INPUT_FILE_EXTENSION = ".input";

    public Path handleRequest(
        final Path inputFilePath,
        final Function<List<String>, List<String>> requestHandler
    ) {
        validateInputFilePath(inputFilePath);
        requestHandler.apply(readLinesFrom(inputFilePath));
        return null;
    }

    private void validateInputFilePath(Path inputFilePath) {
        if (!inputFilePath.toString().endsWith(INPUT_FILE_EXTENSION)) {
            throw new IllegalArgumentException("Input file name expected to end with '.input' extension");
        }
    }

    private List<String> readLinesFrom(Path inputFilePath) {
        try {
            return Files.readAllLines(inputFilePath);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
