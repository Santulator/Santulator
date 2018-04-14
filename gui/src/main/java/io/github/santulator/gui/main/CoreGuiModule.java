/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.main;

import com.google.inject.AbstractModule;

public class CoreGuiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(SantulatorGui.class).to(SantulatorGuiImpl.class);
    }
}
