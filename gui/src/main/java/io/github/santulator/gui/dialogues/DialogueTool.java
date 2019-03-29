/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.dialogues;

import io.github.santulator.gui.i18n.I18nKey;
import io.github.santulator.gui.i18n.I18nManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import javax.inject.Inject;
import javax.inject.Singleton;

import static io.github.santulator.gui.i18n.I18nKey.*;

@Singleton
public final class DialogueTool {
    private static final Logger LOG = LoggerFactory.getLogger(DialogueTool.class);

    private final I18nManager i18nManager;

    @Inject
    public DialogueTool(final I18nManager i18nManager) {
        this.i18nManager = i18nManager;
    }

    public void errorOnOpen(final Path file, final RuntimeException e) {
        handleFileError(file, e, ERROR_SESSION_OPEN_TITLE, ERROR_SESSION_OPEN_DETAILS, "Unable to open file '{}'");
    }

    public void errorOnImportSession(final Path file, final RuntimeException e) {
        handleFileError(file, e, ERROR_SESSION_IMPORT_TITLE, ERROR_SESSION_IMPORT_DETAILS, "Unable to import file '{}'");
    }

    public void errorOnSave(final Path file, final RuntimeException e) {
        handleFileError(file, e, ERROR_SESSION_SAVE_TITLE, ERROR_SESSION_SAVE_DETAILS, "Unable to save session file '{}'");
    }

    public void errorOnSaveResults(final Path directory, final RuntimeException e) {
        handleFileError(directory, e, ERROR_RESULTS_SAVE_TITLE, ERROR_RESULTS_SAVE_DETAILS, "Unable to save draw results '{}'");
    }

    private void handleFileError(final Path file, final RuntimeException e, final I18nKey titleKey, final I18nKey detailKey, final String log) {
        LOG.info(log, file, e);

        String title = i18nManager.text(titleKey);
        String message = i18nManager.text(detailKey, file.getFileName());
        ErrorDialogue dialogue = new ErrorDialogue(i18nManager, title, e, message);

        dialogue.showError();
    }

    public UnsavedChangesDialogue unsavedChangesDialogue(final Path file) {
        return new UnsavedChangesDialogue(file, i18nManager);
    }
}
