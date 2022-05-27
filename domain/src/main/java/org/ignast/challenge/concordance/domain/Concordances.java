package org.ignast.challenge.concordance.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class Concordances {

    public Map<String, List<Integer>> generate(List<List<String>> sentences) {
        return new ConcordanceGenerator(sentences).generate();
    }

    @RequiredArgsConstructor
    private static class ConcordanceGenerator {

        private final List<List<String>> sentences;

        private final Map<String, List<Integer>> concordance = new TreeMap<>();

        public Map<String, List<Integer>> generate() {
            IntStream
                .range(0, sentences.size())
                .forEach(sentenceIndex -> {
                    sentences
                        .get(sentenceIndex)
                        .stream()
                        .reduce(
                            concordance,
                            (locations, word) -> {
                                markWordLocation(locations, sentenceIndex, word);
                                return locations;
                            },
                            combinationNotSupported()
                        );
                });
            return concordance;
        }

        private void markWordLocation(final Map<String, List<Integer>> map, int sentenceIndex, String word) {
            map.compute(
                word,
                (k, v) -> {
                    if (v == null) {
                        val frequencies = new ArrayList<Integer>();
                        frequencies.add(oneBasedIndex(sentenceIndex));
                        return frequencies;
                    } else {
                        v.add(oneBasedIndex(sentenceIndex));
                        return v;
                    }
                }
            );
        }

        private int oneBasedIndex(final int i) {
            return i + 1;
        }

        private <T> BinaryOperator<T> combinationNotSupported() {
            return (a, b) -> {
                throw new UnsupportedOperationException();
            };
        }
    }
}
