package org.ignast.challenge.concordance.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
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

        verify(concordance).generate(List.of(List.of()));
    }

    @Test
    public void shouldParseMultipleEmptyLines() {
        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of("", ""), List.of()),
            concordance
        );

        controller.generateConcordance(mock(Path.class));

        verify(concordance).generate(List.of(List.of()));
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

    @Test
    public void shouldParseMultipleWords() {
        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of("Hello world."), List.of()),
            concordance
        );

        controller.generateConcordance(mock(Path.class));

        verify(concordance).generate(List.of(List.of("hello", "world")));
    }

    @Test
    public void shouldParseParagraphsWords() {
        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of("Hello world.", "Hello computer."), List.of()),
            concordance
        );

        controller.generateConcordance(mock(Path.class));

        verify(concordance).generate(List.of(List.of("hello", "world"), List.of("hello", "computer")));
    }

    @Test
    public void shouldParseSentenceSpanningMultipleLines() {
        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of("Hello", "world."), List.of()),
            concordance
        );

        controller.generateConcordance(mock(Path.class));

        verify(concordance).generate(List.of(List.of("hello", "world")));
    }

    @Test
    public void wordsEndingWithDotButNotEndingTheSentenceShouldNotBeTakenToAccountWhenSplittingSentences() {
        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of("Hello world i.e. new program"), List.of()),
            concordance
        );

        controller.generateConcordance(mock(Path.class));

        verify(concordance).generate(List.of(List.of("hello", "world", "i.e.", "new", "program")));
    }

    @Test
    public void commasShouldBeDropped() {
        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of("hello, world."), List.of()),
            concordance
        );

        controller.generateConcordance(mock(Path.class));

        verify(concordance).generate(List.of(List.of("hello", "world")));
    }

    @Test
    public void colonsShouldBeDropped() {
        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of("hello: world."), List.of()),
            concordance
        );

        controller.generateConcordance(mock(Path.class));

        verify(concordance).generate(List.of(List.of("hello", "world")));
    }

    @Test
    public void shouldNotSerializeIfTheTextIsEmpty() {
        when(concordance.generate(any())).thenReturn(Map.of());

        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of(), List.of()),
            concordance
        );

        controller.generateConcordance(mock(Path.class));
    }

    @Test
    public void shouldSerializeConcordance() {
        when(concordance.generate(any())).thenReturn(Map.of("word", List.of(4, 5)));

        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of(), List.of("a. word {2:4,5}")),
            concordance
        );

        controller.generateConcordance(mock(Path.class));
    }
}
