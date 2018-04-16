/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.main;

import com.google.inject.AbstractModule;
import io.github.santulator.gui.services.*;

public class LiveGuiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PlacementManager.class).to(PlacementManagerImpl.class);
        bind(EnvironmentManager.class).to(EnvironmentManagerImpl.class);
        bind(WebPageTool.class).to(WebPageToolImpl.class);
    }
}
