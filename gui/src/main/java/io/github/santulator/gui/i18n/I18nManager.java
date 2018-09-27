package io.github.santulator.gui.i18n;

import io.github.santulator.core.I18nBundleProvider;

public interface I18nManager extends I18nBundleProvider {
    String text(I18nKey key, Object... arguments);
}
