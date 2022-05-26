package org.ignast.challenge.concordance.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import lombok.val;

public class Concordance {
    public Map<String, List<Integer>> generate(List<List<String>> text) {
        return text.isEmpty()
            ? Map.of()
            : text
                .get(0)
                .stream()
                .reduce(
                    new HashMap<String, List<Integer>>(),
                    (map, word) -> {
                        map.compute(
                            word,
                            (k, v) -> {
                                if (v == null) {
                                    val frequencies = new ArrayList<Integer>();
                                    frequencies.add(1);
                                    return frequencies;
                                } else {
                                    v.add(1);
                                    return v;
                                }
                            }
                        );
                        return map;
                    },
                    combinationNotSupported()
                );
    }

    private <T> BinaryOperator<T> combinationNotSupported() {
        return (a, b) -> {
            throw new UnsupportedOperationException();
        };
    }
}
