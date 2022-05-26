package org.ignast.challenge.concordance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ConcordanceTest {

    private static final Concordance concordance = new Concordance();

    @Test
    public void shouldNotGenerateForEmptyDocument() {
        assertThat(concordance.generate(List.of())).isEmpty();
    }

    @Test
    public void shouldGenerateForMultipleWordsInTheFirstLine() {
        assertThat(concordance.generate(List.of(List.of("a", "b"))))
            .isEqualTo(Map.of("a", List.of(1), "b", List.of(1)));
    }

    @Test
    public void shouldGenerateForSingleWord() {
        assertThat(concordance.generate(List.of(List.of("a")))).isEqualTo(Map.of("a", List.of(1)));
    }

    @Test
    public void shouldGenerateForSameWords() {
        assertThat(concordance.generate(List.of(List.of("a", "a")))).isEqualTo(Map.of("a", List.of(1, 1)));
    }

    @Test
    public void shouldNotGenerateForEmptyLines() {
        assertThat(concordance.generate(List.of(List.of()))).isEmpty();
    }
}
