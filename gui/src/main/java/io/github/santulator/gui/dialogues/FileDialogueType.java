/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.dialogues;

public enum FileDialogueType {
    OPEN_SESSION("Open"),
    SAVE_SESSION("Save"),
    RUN_DRAW("Run draw");

    private final String title;

    FileDialogueType(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
