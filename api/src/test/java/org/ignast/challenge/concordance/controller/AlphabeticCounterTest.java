package org.ignast.challenge.concordance.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class AlphabeticCounterTest {

    private static final AlphabeticCounter counter = new AlphabeticCounter();

    @Test
    public void shouldCountAsSingleLetters() {
        assertThat(counter.next()).isEqualTo("a");
        assertThat(counter.next()).isEqualTo("b");

        Stream
            .generate(() -> counter.next())
            .skip(23)
            .findFirst()
            .ifPresent(c -> assertThat(c).isEqualTo("z"));
    }
}
