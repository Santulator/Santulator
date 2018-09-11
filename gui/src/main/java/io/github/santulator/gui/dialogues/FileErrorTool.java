/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.dialogues;

import io.github.santulator.gui.i18n.I18nGuiKey;
import io.github.santulator.gui.i18n.I18nManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

import static io.github.santulator.gui.i18n.I18nGuiKey.*;

public final class FileErrorTool {
    private static final Logger LOG = LoggerFactory.getLogger(FileErrorTool.class);

    private FileErrorTool() {
        // Prevent instantiation - all methods are static
    }

    public static void open(final I18nManager i18nManager, final Path file, final RuntimeException e) {
        handleFileError(i18nManager, file, e, ERROR_SESSION_OPEN_TITLE, ERROR_SESSION_OPEN_DETAILS, "Unable to open file '{}'");
    }

    public static void save(final I18nManager i18nManager, final Path file, final RuntimeException e) {
        handleFileError(i18nManager, file, e, ERROR_SESSION_SAVE_TITLE, ERROR_SESSION_SAVE_DETAILS, "Unable to save session file '{}'");
    }

    public static void saveResults(final I18nManager i18nManager, final Path directory, final RuntimeException e) {
        handleFileError(i18nManager, directory, e, ERROR_RESULTS_SAVE_TITLE, ERROR_RESULTS_SAVE_DETAILS, "Unable to save draw results '{}'");
    }

    private static void handleFileError(final I18nManager i18nManager, final Path file, final RuntimeException e, final I18nGuiKey titleKey, final I18nGuiKey detailKey, final String log) {
        LOG.info(log, file, e);

        String title = i18nManager.guiText(titleKey);
        String message = i18nManager.guiText(detailKey, file.getFileName());
        ErrorDialogue dialogue = new ErrorDialogue(i18nManager, title, e, message);

        dialogue.showError();
    }
}
