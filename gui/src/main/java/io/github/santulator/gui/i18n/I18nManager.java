package io.github.santulator.gui.i18n;

import java.util.ResourceBundle;

public interface I18nManager {
    ResourceBundle guiBundle();

    String guiText(I18nGuiKey key, Object... arguments);
}
