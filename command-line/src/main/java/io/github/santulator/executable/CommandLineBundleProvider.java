package io.github.santulator.executable;

import io.github.santulator.core.I18nBundleProvider;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.inject.Singleton;

@Singleton
public class CommandLineBundleProvider implements I18nBundleProvider {
    public static final String BUNDLE = "bundles/CommandLineBundle";

    private final ResourceBundle bundle;

    public CommandLineBundleProvider(final Locale locale) {
        bundle = ResourceBundle.getBundle(BUNDLE, locale);
    }

    @Override
    public ResourceBundle bundle() {
        return bundle;
    }
}
