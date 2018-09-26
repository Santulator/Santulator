package io.github.santulator.gui.i18n;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.inject.Singleton;

@Singleton
public class I18nManagerImpl implements I18nManager {
    public static final String BUNDLE = "bundles/SantulatorBundle";

    private final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE);

    @Override
    public ResourceBundle bundle() {
        return bundle;
    }

    @Override
    public String text(final I18nKey key, final Object... arguments) {
        String template = bundle.getString(key.getKey());

        return MessageFormat.format(template, arguments);
    }
}
