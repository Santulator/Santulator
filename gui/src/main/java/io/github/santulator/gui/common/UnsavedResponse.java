/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.common;

import io.github.santulator.gui.i18n.I18nGuiKey;

import static io.github.santulator.gui.i18n.I18nGuiKey.*;

public enum UnsavedResponse {
    SAVE(FILE_SAVE), DISCARD(FILE_DISCARD), CANCEL(FILE_CANCEL);

    private final I18nGuiKey key;

    UnsavedResponse(final I18nGuiKey key) {
        this.key = key;
    }

    public I18nGuiKey getKey() {
        return key;
    }
}
