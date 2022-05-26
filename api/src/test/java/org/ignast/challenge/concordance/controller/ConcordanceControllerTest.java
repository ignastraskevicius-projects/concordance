package org.ignast.challenge.concordance.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.val;
import org.ignast.challenge.concordance.domain.Concordance;
import org.junit.jupiter.api.Test;

class ConcordanceControllerTest {

    private final Concordance concordance = mock(Concordance.class);

    private final AlphabeticCounter counter = new AlphabeticCounter();

    @Test
    public void shouldParseEmptyFile() {
        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of(), List.of()),
            concordance,
            counter
        );

        controller.generateConcordance(mock(Path.class));

        verify(concordance).generate(List.of(List.of()));
    }

    @Test
    public void shouldParseMultipleEmptyLines() {
        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of("", ""), List.of()),
            concordance,
            counter
        );

        controller.generateConcordance(mock(Path.class));

        verify(concordance).generate(List.of(List.of()));
    }

    @Test
    public void shouldParseSingleWordLowercaseTextWithoutPunctuation() {
        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of("Word."), List.of()),
            concordance,
            counter
        );

        controller.generateConcordance(mock(Path.class));

        verify(concordance).generate(List.of(List.of("word")));
    }

    @Test
    public void shouldParseMultipleWords() {
        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of("Hello world."), List.of()),
            concordance,
            counter
        );

        controller.generateConcordance(mock(Path.class));

        verify(concordance).generate(List.of(List.of("hello", "world")));
    }

    @Test
    public void shouldParseParagraphsWords() {
        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of("Hello world.", "Hello computer."), List.of()),
            concordance,
            counter
        );

        controller.generateConcordance(mock(Path.class));

        verify(concordance).generate(List.of(List.of("hello", "world"), List.of("hello", "computer")));
    }

    @Test
    public void shouldParseSentenceSpanningMultipleLines() {
        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of("Hello", "world."), List.of()),
            concordance,
            counter
        );

        controller.generateConcordance(mock(Path.class));

        verify(concordance).generate(List.of(List.of("hello", "world")));
    }

    @Test
    public void wordsEndingWithDotButNotEndingTheSentenceShouldNotBeTakenToAccountWhenSplittingSentences() {
        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of("Hello world i.e. new program"), List.of()),
            concordance,
            counter
        );

        controller.generateConcordance(mock(Path.class));

        verify(concordance).generate(List.of(List.of("hello", "world", "i.e.", "new", "program")));
    }

    @Test
    public void commasShouldBeDropped() {
        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of("hello, world."), List.of()),
            concordance,
            counter
        );

        controller.generateConcordance(mock(Path.class));

        verify(concordance).generate(List.of(List.of("hello", "world")));
    }

    @Test
    public void colonsShouldBeDropped() {
        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of("hello: world."), List.of()),
            concordance,
            counter
        );

        controller.generateConcordance(mock(Path.class));

        verify(concordance).generate(List.of(List.of("hello", "world")));
    }

    @Test
    public void shouldNotSerializeIfTheTextIsEmpty() {
        when(concordance.generate(any())).thenReturn(Map.of());

        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of(), List.of()),
            concordance,
            counter
        );

        controller.generateConcordance(mock(Path.class));
    }

    @Test
    public void shouldSerializeConcordanceForSingleWord() {
        when(concordance.generate(any())).thenReturn(Map.of("word", List.of(4, 5)));

        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(List.of(), List.of("a.         word {2:4,5}")),
            concordance,
            counter
        );

        controller.generateConcordance(mock(Path.class));
    }

    @Test
    public void shouldSerializeConcordanceForMultipleWords() {
        when(concordance.generate(any()))
            .thenReturn(new TreeMap(Map.of("hello", List.of(1), "world", List.of(1))));

        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(
                List.of(),
                List.of("a.         hello {1:1}", "b.         world {1:1}")
            ),
            concordance,
            counter
        );

        controller.generateConcordance(mock(Path.class));
    }

    @Test
    public void spacingBetweenWordAndStatisticsShouldBeBigEnoughToAccommodateLongestWordAndStillMaintainConsistentTabulation() {
        when(concordance.generate(any()))
            .thenReturn(new TreeMap(Map.of("longlongword", List.of(1), "short", List.of(1))));

        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(
                List.of(),
                List.of("a.         longlongword {1:1}", "b.         short        {1:1}")
            ),
            concordance,
            counter
        );

        controller.generateConcordance(mock(Path.class));
    }

    @Test
    public void spacingBetweenLineNumberAndStatisticsShouldBeBigEnoughToAccommodateLongestLineNumberAndStillMaintainConsistentTabulation() {
        val counter = mock(AlphabeticCounter.class);
        when(counter.next()).thenReturn("a", "aaa");
        when(concordance.generate(any()))
            .thenReturn(new TreeMap(Map.of("hello", List.of(1), "world", List.of(1))));

        val controller = new ConcordanceController(
            new FileBasedCommunicationStub(
                List.of(),
                List.of("a.         hello {1:1}", "aaa.       world {1:1}")
            ),
            concordance,
            counter
        );

        controller.generateConcordance(mock(Path.class));
    }
}
