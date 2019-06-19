/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.writer;

import io.github.santulator.core.CoreConstants;
import io.github.santulator.core.I18nBundleProvider;

import java.util.ResourceBundle;
import javax.inject.Singleton;

@Singleton
public class TestBundleProvider implements I18nBundleProvider {
    private static final String BUNDLE = "bundles/TestBundle";

    private final ResourceBundle bundle;

    public TestBundleProvider() {
        bundle = ResourceBundle.getBundle(BUNDLE, CoreConstants.LOCALE);
    }

    @Override
    public ResourceBundle bundle() {
        return bundle;
    }
}
