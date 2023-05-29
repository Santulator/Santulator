/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.main;

import io.github.santulator.gui.dialogues.FileDialogueType;
import io.github.santulator.gui.dialogues.FileFormatType;
import io.github.santulator.gui.i18n.I18nKey;
import io.github.santulator.model.SessionState;

import java.nio.file.Path;

public interface GuiTestValidator {
    void validateWebPage(I18nKey key);

    void validateOpenPath(Path path);

    void validateSavedSession(Path file, SessionState expected);

    void validateDraw(Path directory, String... names);

    void setUpFileDialogue(FileDialogueType dialogueType, FileFormatType fileType, String file);

    void setUpFileDialogue(FileDialogueType dialogueType, FileFormatType fileType, Path file);
}
