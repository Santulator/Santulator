/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.settings;

import java.nio.file.Path;
import java.util.Optional;

public interface SettingsManager {
    Path getSessionsPath();

    void setSessionsPath(Path path);

    Path getDrawPath();

    void setDrawPath(Path path);

    Optional<WindowSettings> getWindowSettings();

    void setWindowSettings(WindowSettings windowSettings);
}
