/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.writer;

import com.google.inject.AbstractModule;
import io.github.santulator.core.I18nBundleProvider;

public class TestResourcesModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(I18nBundleProvider.class).to(TestBundleProvider.class);
    }
}
