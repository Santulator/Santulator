/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.dialogues;

import io.github.santulator.gui.i18n.I18nKey;

import static io.github.santulator.gui.i18n.I18nKey.*;

public enum FileDialogueType {
    OPEN_SESSION(FILE_OPEN),
    SAVE_SESSION(FILE_SAVE),
    RUN_DRAW(FILE_RUN);

    private final I18nKey titleKey;

    FileDialogueType(final I18nKey titleKey) {
        this.titleKey = titleKey;
    }

    public I18nKey getTitleKey() {
        return titleKey;
    }
}
