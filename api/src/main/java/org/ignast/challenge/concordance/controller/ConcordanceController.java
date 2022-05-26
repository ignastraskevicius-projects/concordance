package org.ignast.challenge.concordance.controller;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.ignast.challenge.concordance.domain.Concordance;

@RequiredArgsConstructor
public class ConcordanceController {

    private final LocalFileBasedCommunication localFileBasedCommunication;

    private final Concordance concordanceGenerator;

    private final AlphabeticCounter counter;

    public Path generateConcordance(final Path inputFilePath) {
        return localFileBasedCommunication.handleRequest(
            inputFilePath,
            inputLines -> serialize(concordanceGenerator.generate(parseSentences(inputLines)))
        );
    }

    private List<String> serialize(Map<String, List<Integer>> concordance) {
        val spaceForEachWord = getLengthOfLongestWord(concordance);
        return concordance
            .entrySet()
            .stream()
            .map(entry ->
                String.format(
                    "%s %s {%d:%s}",
                    addTabulationAfter(counter.next() + ".", 10),
                    addTabulationAfter(entry.getKey(), spaceForEachWord),
                    entry.getValue().size(),
                    entry.getValue().stream().map(String::valueOf).collect(Collectors.joining(","))
                )
            )
            .collect(Collectors.toUnmodifiableList());
    }

    private Integer getLengthOfLongestWord(Map<String, List<Integer>> concordance) {
        return concordance
            .keySet()
            .stream()
            .max(Comparator.comparing(String::length))
            .map(String::length)
            .orElse(0);
    }

    private String addTabulationAfter(String word, Integer spaceToTake) {
        return word + (" ".repeat(spaceToTake - word.length()));
    }

    private List<List<String>> parseSentences(final List<String> lines) {
        return splitBySentences(removePunctuation(concatLines(lines)))
            .stream()
            .map(line -> toWords(line))
            .collect(Collectors.toUnmodifiableList());
    }

    private String removePunctuation(String text) {
        return text.replace(",", "").replace(":", "");
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
