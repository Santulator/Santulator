/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.executable;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.github.santulator.core.I18nBundleProvider;
import io.github.santulator.engine.DrawService;
import io.github.santulator.engine.DrawServiceImpl;
import io.github.santulator.reader.ExcelRequirementsReader;
import io.github.santulator.reader.RequirementsReader;
import io.github.santulator.writer.DrawSelectionWriter;
import io.github.santulator.writer.DrawSelectionWriterImpl;

import java.util.Locale;

public class SantaModule extends AbstractModule {
    private final Locale locale;

    public SantaModule(final Locale locale) {
        this.locale = locale;
    }

    @Override
    protected void configure() {
        bind(DrawSelectionWriter.class).to(DrawSelectionWriterImpl.class);
        bind(DrawService.class).to(DrawServiceImpl.class);
        bind(RequirementsReader.class).to(ExcelRequirementsReader.class);
        bind(SimpleSantaRunner.class).to(SimpleSantaRunnerImpl.class);
    }

    @Provides
    public I18nBundleProvider bundleProvider() {
        return new CommandLineBundleProvider(locale);
    }
}
