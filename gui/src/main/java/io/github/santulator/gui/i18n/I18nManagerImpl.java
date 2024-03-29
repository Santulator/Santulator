/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.i18n;

import io.github.santulator.core.CoreConstants;
import jakarta.inject.Singleton;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@Singleton
public class I18nManagerImpl implements I18nManager {
    private static final String BUNDLE_BASE_NAME = "bundles/SantulatorBundle";

    private final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME);

    @Override
    public ResourceBundle bundle() {
        return bundle;
    }

    @Override
    public void initialise() {
        Locale.setDefault(CoreConstants.LOCALE);
    }

    @Override
    public String text(final I18nKey key, final Object... arguments) {
        String template = bundle.getString(key.getKey());

        return MessageFormat.format(template, arguments);
    }
}
