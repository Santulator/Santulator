/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.services;

import io.github.santulator.gui.common.Placement;

public interface EnvironmentManager {
    Placement getScreenSize();

    boolean isVisible(Placement placement);

    boolean useSystemMenuBar();

    boolean isExitOptionShown();
}
