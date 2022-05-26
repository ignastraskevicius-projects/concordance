package org.ignast.challenge.concordance.controller;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocalFileBasedCommunicationTest {

    private final LocalFileBasedCommunication fileBasedComms = new LocalFileBasedCommunication();

    private final String fileName = "mowingSimulation" + System.currentTimeMillis();

    private final File tempDir = new File(System.getProperty("java.io.tmpdir"));

    private File inputFile;

    private Path outputFilePath;

    @BeforeEach
    public void createInputFileAndOutputPath() throws IOException {
        inputFile =
            Files
                .createFile(Path.of(tempDir.getAbsolutePath() + File.separator + fileName + ".input"))
                .toAbsolutePath()
                .toFile();
        inputFile.deleteOnExit();
        outputFilePath = Path.of(tempDir.getAbsolutePath() + File.separator + fileName + ".output");
    }

    @AfterEach
    public void remoteCreatedFiles() {
        outputFilePath.toFile().deleteOnExit();
    }

    @Test
    public void shouldFailIfInputFileIsNotFound() {
        assertThatIllegalArgumentException()
            .isThrownBy(() ->
                fileBasedComms.handleRequest(Path.of("non-existent-file.input"), inputLines -> List.of())
            );
    }
}
