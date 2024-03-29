/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.services;

import io.github.santulator.gui.common.Placement;
import io.github.santulator.gui.settings.SettingsManager;
import io.github.santulator.gui.settings.WindowSettings;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class PlacementManagerImpl implements PlacementManager {
    public static final double WINDOW_SIZE_FACTOR = 0.85;

    private final EnvironmentManager environmentManager;

    private final SettingsManager settingsManager;

    @Inject
    public PlacementManagerImpl(final EnvironmentManager environmentManager, final SettingsManager settingsManager) {
        this.environmentManager = environmentManager;
        this.settingsManager = settingsManager;
    }

    @Override
    public Placement getMainWindow() {
        Optional<WindowSettings> settings = settingsManager.getWindowSettings();

        return settings.map(this::windowPlacement)
            .filter(environmentManager::isVisible)
            .orElseGet(this::defaultWindowPlacement);
    }

    private Placement windowPlacement(final WindowSettings settings) {
        return new Placement(settings.getWidth(), settings.getHeight(), settings.getX(), settings.getY());
    }

    private Placement defaultWindowPlacement() {
        Placement screenSize = environmentManager.getScreenSize();
        double width = screenSize.width() * WINDOW_SIZE_FACTOR;
        double height = screenSize.height() * WINDOW_SIZE_FACTOR;

        return new Placement(width, height);
    }
}
