package org.ignast.challenge.concordance.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.nio.file.Path;
import java.util.List;
import lombok.val;
import org.ignast.challenge.concordance.domain.Concordance;
import org.junit.jupiter.api.Test;

class ConcordanceControllerTest {

    private final Concordance concordance = mock(Concordance.class);

    @Test
    public void shouldParseEmptyFile() {
        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of(), List.of()),
            concordance
        );

        controller.generateConcordance(mock(Path.class));

        verify(concordance).generate(List.of());
    }

    @Test
    public void shouldParseSingleWordLowercaseTextWithoutPunctuation() {
        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of("Word."), List.of()),
            concordance
        );

        controller.generateConcordance(mock(Path.class));

        verify(concordance).generate(List.of(List.of("word")));
    }
}
