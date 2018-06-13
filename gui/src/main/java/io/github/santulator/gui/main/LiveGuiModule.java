/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.main;

import com.google.inject.AbstractModule;
import io.github.santulator.core.GuiTaskHandler;
import io.github.santulator.core.GuiTaskHandlerImpl;
import io.github.santulator.gui.dialogues.FileDialogueFactory;
import io.github.santulator.gui.services.*;
import io.github.santulator.gui.settings.SettingsManager;
import io.github.santulator.gui.settings.SettingsManagerImpl;

public class LiveGuiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(SettingsManager.class).to(SettingsManagerImpl.class);
        bind(FileDialogueFactory.class).to(FileDialogueFactoryImpl.class);
        bind(PlacementManager.class).to(PlacementManagerImpl.class);
        bind(EnvironmentManager.class).to(EnvironmentManagerImpl.class);
        bind(DesktopResourceTool.class).to(DesktopResourceToolImpl.class);
        bind(GuiTaskHandler.class).to(GuiTaskHandlerImpl.class);
    }
}
