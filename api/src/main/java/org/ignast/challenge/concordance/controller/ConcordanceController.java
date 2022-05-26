package org.ignast.challenge.concordance.controller;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.val;
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

    private List<List<String>> parseSentences(final List<String> lines) {
        return splitBySentences(concatLines(lines))
            .stream()
            .map(line -> toWords(line))
            .collect(Collectors.toUnmodifiableList());
    }

    private List<String> toWords(String line) {
        return Arrays
            .asList(line.toLowerCase().split(" "))
            .stream()
            .filter(word -> !word.isEmpty())
            .collect(Collectors.toUnmodifiableList());
    }

    private String concatLines(List<String> list) {
        return list.stream().collect(Collectors.joining(" "));
    }

    private List<String> splitBySentences(String text) {
        val chars = text.toCharArray();
        IntStream
            .range(1, chars.length - 1)
            .filter(i -> chars[i - 1] == '.')
            .filter(i -> chars[i] == ' ')
            .filter(i -> Character.isUpperCase(chars[i + 1]))
            .forEach(i -> chars[i] = '\n');
        removeDotFromLastSentence(chars);
        return Arrays.asList(String.valueOf(chars).split(".\n"));
    }

    private void removeDotFromLastSentence(char[] chars) {
        if (chars.length > 0 && chars[chars.length - 1] == '.') {
            chars[chars.length - 1] = ' ';
        }
    }
}
