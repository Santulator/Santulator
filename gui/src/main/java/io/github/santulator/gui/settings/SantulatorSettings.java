/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.nio.file.Path;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SantulatorSettings {
    private Path sessionsPath;

    private Path importPath;

    private Path drawPath;

    private WindowSettings windowSettings;

    public Path getSessionsPath() {
        return sessionsPath;
    }

    public void setSessionsPath(final Path sessionsPath) {
        this.sessionsPath = sessionsPath;
    }

    public Path getImportPath() {
        return importPath;
    }

    public void setImportPath(final Path importPath) {
        this.importPath = importPath;
    }

    public Path getDrawPath() {
        return drawPath;
    }

    public void setDrawPath(final Path drawPath) {
        this.drawPath = drawPath;
    }

    public WindowSettings getWindowSettings() {
        return windowSettings;
    }

    public void setWindowSettings(final WindowSettings windowSettings) {
        this.windowSettings = windowSettings;
    }
}
