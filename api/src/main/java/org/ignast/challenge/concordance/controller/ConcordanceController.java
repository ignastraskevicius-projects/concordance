package org.ignast.challenge.concordance.controller;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ignast.challenge.concordance.domain.Concordance;

@RequiredArgsConstructor
public class ConcordanceController {

    private final LocalFileBasedCommunication localFileBasedCommunication;

    private final Concordance concordanceGenerator;

    public Path generateConcordance(final Path inputFilePath) {
        return localFileBasedCommunication.handleRequest(
            inputFilePath,
            inputLines -> {
                concordanceGenerator.generate(parseSentences(inputLines));
                return List.of();
            }
        );
    }

    private List<List<String>> parseSentences(final List<String> list) {
        return list.isEmpty() ? List.of() : List.of(Arrays.asList(list.get(0).replace(".", "").toLowerCase().split(" ")));
    }
}
