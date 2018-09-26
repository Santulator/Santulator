/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.common;

import io.github.santulator.gui.i18n.I18nKey;

import static io.github.santulator.gui.i18n.I18nKey.*;

public enum UnsavedResponse {
    SAVE(FILE_SAVE), DISCARD(FILE_DISCARD), CANCEL(FILE_CANCEL);

    private final I18nKey key;

    UnsavedResponse(final I18nKey key) {
        this.key = key;
    }

    public I18nKey getKey() {
        return key;
    }
}
