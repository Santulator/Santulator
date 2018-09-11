package io.github.santulator.gui.i18n;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.inject.Singleton;

@Singleton
public class I18nManagerImpl implements I18nManager {
    public static final String BUNDLE_GUI = "bundles/santulator-gui-messages";

    private final ResourceBundle guiBundle = ResourceBundle.getBundle(BUNDLE_GUI);

    @Override
    public ResourceBundle guiBundle() {
        return guiBundle;
    }

    @Override
    public String guiText(final I18nGuiKey key, final Object... arguments) {
        String template = guiBundle.getString(key.getKey());

        return MessageFormat.format(template, arguments);
    }
}
