/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.services;

import io.github.santulator.gui.common.Placement;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PlacementManagerImpl implements PlacementManager {
    public static final double WINDOW_SIZE_FACTOR = 0.85;

    private final EnvironmentManager environmentManager;

    @Inject
    public PlacementManagerImpl(final EnvironmentManager environmentManager) {
        this.environmentManager = environmentManager;
    }

    @Override
    public Placement getMainWindow() {
        return defaultWindowPlacement();
    }

    private Placement defaultWindowPlacement() {
        Placement screenSize = environmentManager.getScreenSize();
        double width = screenSize.getWidth() * WINDOW_SIZE_FACTOR;
        double height = screenSize.getHeight() * WINDOW_SIZE_FACTOR;

        return new Placement(width, height);
    }
}
