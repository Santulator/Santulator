/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.settings;

import java.nio.file.Path;

public interface SettingsManager {
    Path getSessionsPath();

    void setSessionsPath(Path path);

    // TODO Add remaining settings
}
