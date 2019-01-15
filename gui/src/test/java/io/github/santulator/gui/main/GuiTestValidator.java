/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.main;

import io.github.santulator.gui.dialogues.FileDialogueType;
import io.github.santulator.gui.dialogues.FileFormatType;
import io.github.santulator.model.SessionState;

import java.nio.file.Path;

public interface GuiTestValidator {
    void validateWebPage(final String page);

    void validateOpenPath(final Path path);

    void validateSavedSession(Path file, final SessionState expected);

    void validateDraw(final Path directory, final String... names);

    void setUpFileDialogue(FileDialogueType dialogueType, final FileFormatType fileType, String file);

    void setUpFileDialogue(FileDialogueType dialogueType, final FileFormatType fileType, Path file);
}
