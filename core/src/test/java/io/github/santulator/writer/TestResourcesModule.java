package io.github.santulator.writer;

import com.google.inject.AbstractModule;
import io.github.santulator.core.I18nBundleProvider;

public class TestResourcesModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(I18nBundleProvider.class).to(TestBundleProvider.class);
    }
}
