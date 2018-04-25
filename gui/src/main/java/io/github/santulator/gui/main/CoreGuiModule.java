/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.main;

import com.google.inject.AbstractModule;
import io.github.santulator.core.ThreadPoolTool;
import io.github.santulator.core.ThreadPoolToolImpl;
import io.github.santulator.gui.status.StatusManager;
import io.github.santulator.gui.status.StatusManagerImpl;
import io.github.santulator.session.SessionSerialiser;
import io.github.santulator.session.SessionSerialiserImpl;

public class CoreGuiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ThreadPoolTool.class).to(ThreadPoolToolImpl.class);
        bind(SantulatorGui.class).to(SantulatorGuiImpl.class);
        bind(StatusManager.class).to(StatusManagerImpl.class);
        bind(SessionSerialiser.class).to(SessionSerialiserImpl.class);
    }
}
