/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.main;

import com.google.inject.AbstractModule;
import io.github.santulator.core.ThreadPoolTool;
import io.github.santulator.core.ThreadPoolToolImpl;
import io.github.santulator.engine.DrawService;
import io.github.santulator.engine.DrawServiceImpl;
import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.gui.i18n.I18nManagerImpl;
import io.github.santulator.gui.services.ProgressSequencer;
import io.github.santulator.gui.services.ProgressSequencerImpl;
import io.github.santulator.gui.status.StatusManager;
import io.github.santulator.gui.status.StatusManagerImpl;
import io.github.santulator.session.SessionSerialiser;
import io.github.santulator.session.SessionSerialiserImpl;
import io.github.santulator.session.SessionStateTranslator;
import io.github.santulator.session.SessionStateTranslatorImpl;

public class CoreGuiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ThreadPoolTool.class).to(ThreadPoolToolImpl.class);
        bind(SantulatorGui.class).to(SantulatorGuiImpl.class);
        bind(StatusManager.class).to(StatusManagerImpl.class);
        bind(SessionSerialiser.class).to(SessionSerialiserImpl.class);
        bind(SessionStateTranslator.class).to(SessionStateTranslatorImpl.class);
        bind(DrawService.class).to(DrawServiceImpl.class);
        bind(ProgressSequencer.class).to(ProgressSequencerImpl.class);
        bind(I18nManager.class).to(I18nManagerImpl.class);
    }
}
