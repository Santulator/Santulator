package io.github.santulator.gui.settings;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SettingsManagerImpl implements SettingsManager {
    @Override
    public Path getSessionsPath() {
        // TODO Get saved sessions path
        return Paths.get(System.getProperty("user.home"));
    }

    @Override
    public void setSessionsPath(final Path path) {
        // TODO Save sessions path
    }
}
