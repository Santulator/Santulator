/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.dialogues;

import io.github.santulator.gui.i18n.I18nGuiKey;

import static io.github.santulator.gui.i18n.I18nGuiKey.*;

public enum FileDialogueType {
    OPEN_SESSION(FILE_OPEN),
    SAVE_SESSION(FILE_SAVE),
    RUN_DRAW(FILE_RUN);

    private final I18nGuiKey titleKey;

    FileDialogueType(final I18nGuiKey titleKey) {
        this.titleKey = titleKey;
    }

    public I18nGuiKey getTitleKey() {
        return titleKey;
    }
}
