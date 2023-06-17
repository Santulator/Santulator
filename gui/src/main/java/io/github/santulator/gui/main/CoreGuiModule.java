/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.main;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.github.santulator.core.I18nBundleProvider;
import io.github.santulator.core.ThreadPoolTool;
import io.github.santulator.core.ThreadPoolToolImpl;
import io.github.santulator.engine.DrawService;
import io.github.santulator.engine.DrawServiceImpl;
import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.gui.i18n.I18nManagerImpl;
import io.github.santulator.gui.services.ExternalEventBroker;
import io.github.santulator.gui.services.ExternalEventBrokerImpl;
import io.github.santulator.gui.services.ProgressSequencer;
import io.github.santulator.gui.services.ProgressSequencerImpl;
import io.github.santulator.gui.status.StatusManager;
import io.github.santulator.gui.status.StatusManagerImpl;
import io.github.santulator.gui.validator.ValidationService;
import io.github.santulator.gui.validator.ValidationServiceImpl;
import io.github.santulator.session.*;
import io.github.santulator.writer.DrawSelectionWriter;
import io.github.santulator.writer.DrawSelectionWriterImpl;
import jakarta.inject.Singleton;

import java.nio.file.Paths;
import java.util.List;

public class CoreGuiModule extends AbstractModule {
    private final List<String> args;

    public CoreGuiModule(final String... args) {
        this.args = List.of(args);
    }

    @Override
    protected void configure() {
        bind(ThreadPoolTool.class).to(ThreadPoolToolImpl.class);
        bind(SantulatorGui.class).to(SantulatorGuiImpl.class);
        bind(StatusManager.class).to(StatusManagerImpl.class);
        bind(SessionSerialiser.class).to(SessionSerialiserImpl.class);
        bind(SessionImporter.class).to(SessionImporterImpl.class);
        bind(SessionStateTranslator.class).to(SessionStateTranslatorImpl.class);
        bind(DrawSelectionWriter.class).to(DrawSelectionWriterImpl.class);
        bind(DrawService.class).to(DrawServiceImpl.class);
        bind(ProgressSequencer.class).to(ProgressSequencerImpl.class);
        bind(I18nManager.class).to(I18nManagerImpl.class);
        bind(I18nBundleProvider.class).to(I18nManagerImpl.class);
        bind(ValidationService.class).to(ValidationServiceImpl.class);
    }

    @Provides
    @Singleton
    public ExternalEventBroker provideExternalEventBroker() {
        ExternalEventBroker broker = new ExternalEventBrokerImpl();

        if (!args.isEmpty()) {
            broker.openFile(Paths.get(args.get(0)));
        }

        return broker;
    }
}
