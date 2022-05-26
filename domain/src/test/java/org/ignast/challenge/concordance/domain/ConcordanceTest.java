package org.ignast.challenge.concordance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class ConcordanceTest {

    private static final Concordance concordance = new Concordance();

    @Test
    public void shouldNotGenerateForEmptyDocument() {
        assertThat(concordance.generate(List.of())).isEmpty();
    }
}
