package org.ignast.challenge.concordance.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class AlphabeticCounterTest {

    private final AlphabeticCounter counter = new AlphabeticCounter();

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

    @Test
    public void shouldCountAsMultiLetterString() {
        Stream
            .generate(() -> counter.next())
            .skip(26)
            .findFirst()
            .ifPresent(c -> assertThat(c).isEqualTo("aa"));

        assertThat(counter.next()).isEqualTo("ab");

        Stream
            .generate(() -> counter.next())
            .skip(23)
            .findFirst()
            .ifPresent(c -> assertThat(c).isEqualTo("az"));

        assertThat(counter.next()).isEqualTo("ba");
    }
}
