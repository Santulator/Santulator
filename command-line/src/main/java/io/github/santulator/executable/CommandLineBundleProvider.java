/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.executable;

import io.github.santulator.core.I18nBundleProvider;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.inject.Singleton;

@Singleton
public class CommandLineBundleProvider implements I18nBundleProvider {
    private static final String BUNDLE_BASE_NAME = "bundles/CommandLineBundle";

    private final ResourceBundle bundle;

    public CommandLineBundleProvider(final Locale locale) {
        bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
    }

    @Override
    public ResourceBundle bundle() {
        return bundle;
    }
}
