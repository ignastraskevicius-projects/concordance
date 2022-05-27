package org.ignast.challenge.concordance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ConcordancesTest {

    private static final Concordances CONCORDANCES = new Concordances();

    @Test
    public void shouldNotGenerateForEmptyDocument() {
        assertThat(CONCORDANCES.generate(List.of())).isEmpty();
    }

    @Test
    public void shouldGenerateForMultipleWordsInTheFirstLine() {
        assertThat(CONCORDANCES.generate(List.of(List.of("a", "b"))))
            .isEqualTo(Map.of("a", List.of(1), "b", List.of(1)));
    }

    @Test
    public void wordsShouldBeOrderedAlphabetically() {
        assertThat(
            CONCORDANCES.generate(List.of(List.of("d", "a", "f", "c", "b"))).keySet().stream().toList()
        )
            .isEqualTo(List.of("a", "b", "c", "d", "f"));
    }

    @Test
    public void shouldGenerateForSingleWord() {
        assertThat(CONCORDANCES.generate(List.of(List.of("a")))).isEqualTo(Map.of("a", List.of(1)));
    }

    @Test
    public void shouldGenerateForSameWordsInFirstLine() {
        assertThat(CONCORDANCES.generate(List.of(List.of("a", "a")))).isEqualTo(Map.of("a", List.of(1, 1)));
    }

    @Test
    public void shouldGenerateForSameWordsInNonFirstLine() {
        assertThat(CONCORDANCES.generate(List.of(List.of(), List.of("a"), List.of("a"))))
            .isEqualTo(Map.of("a", List.of(2, 3)));
    }

    @Test
    public void shouldNotGenerateForEmptyLines() {
        assertThat(CONCORDANCES.generate(List.of(List.of()))).isEmpty();
    }
}
