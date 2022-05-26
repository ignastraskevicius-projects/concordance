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
        readLinesFrom(inputFilePath);
        return null;
    }

    private List<String> readLinesFrom(Path inputFilePath) {
        try {
            return Files.readAllLines(inputFilePath);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
