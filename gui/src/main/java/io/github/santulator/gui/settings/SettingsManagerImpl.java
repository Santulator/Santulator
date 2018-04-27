package io.github.santulator.gui.settings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SettingsManagerImpl implements SettingsManager {
    private static final Logger LOG = LoggerFactory.getLogger(SettingsManagerImpl.class);

    public static final String SETTINGS_JSON = "settings.json";

    public SettingsManagerImpl() {
        // TODO Implement settings manager
    }

    public SettingsManagerImpl(final Path settingsFile) {
        // TODO Implement settings manager
        LOG.info("Implement settings handling {}", settingsFile);
    }

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
