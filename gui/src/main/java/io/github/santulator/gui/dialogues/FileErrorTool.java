/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.dialogues;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public final class FileErrorTool {
    private static final Logger LOG = LoggerFactory.getLogger(FileErrorTool.class);

    private FileErrorTool() {
        // Prevent instantiation - all methods are static
    }

    public static void open(final Path file, final RuntimeException e) {
        handleFileError(file, e, "Open File Error", "Couldn't open file '%s'", "Unable to open file '{}'");
    }

    public static void save(final Path file, final RuntimeException e) {
        handleFileError(file, e, "Save Session Error", "Couldn't save file '%s'", "Unable to save session file '{}'");
    }

    public static void saveResults(final Path directory, final RuntimeException e) {
        handleFileError(directory, e, "Save Draw Results Error", "Couldn't save to directory '%s'", "Unable to save draw results '{}'");
    }

    private static void handleFileError(final Path file, final RuntimeException e, final String title, final String message, final String log) {
        LOG.info(log, file, e);

        ErrorDialogue dialogue = new ErrorDialogue(title, e, String.format(message, file.getFileName()));

        dialogue.showError();
    }
}
