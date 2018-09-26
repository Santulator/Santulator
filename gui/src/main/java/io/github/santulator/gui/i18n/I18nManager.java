package io.github.santulator.gui.i18n;

import java.util.ResourceBundle;

public interface I18nManager {
    ResourceBundle bundle();

    String text(I18nKey key, Object... arguments);
}
