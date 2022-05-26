package org.ignast.challenge.concordance.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;
import lombok.val;

public class Concordance {

    public Map<String, List<Integer>> generate(List<List<String>> text) {
        val concordance = new TreeMap<String, List<Integer>>();
        IntStream
            .range(0, text.size())
            .forEach(lineIndex -> {
                text
                    .get(lineIndex)
                    .stream()
                    .reduce(
                        concordance,
                        (map, word) -> {
                            map.compute(
                                word,
                                (k, v) -> {
                                    if (v == null) {
                                        val frequencies = new ArrayList<Integer>();
                                        frequencies.add(oneBasedIndex(lineIndex));
                                        return frequencies;
                                    } else {
                                        v.add(oneBasedIndex(lineIndex));
                                        return v;
                                    }
                                }
                            );
                            return map;
                        },
                        combinationNotSupported()
                    );
            });
        return concordance;
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
