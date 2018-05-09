package io.github.santulator.gui.settings;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import javax.inject.Singleton;

@Singleton
public class SettingsManagerImpl extends BaseSettingsManager<SantulatorSettings> implements SettingsManager {
    public static final String SETTINGS_JSON = "settings.json";

    public SettingsManagerImpl() {
        super(SETTINGS_JSON, SantulatorSettings.class);
    }

    public SettingsManagerImpl(final Path settingsFile) {
        super(settingsFile, SantulatorSettings.class);
    }

    @Override
    public Path getSessionsPath() {
        return getPath(SantulatorSettings::getSessionsPath);
    }

    @Override
    public void setSessionsPath(final Path path) {
        setPath(SantulatorSettings::setSessionsPath, path);
    }

    @Override
    public Path getDrawPath() {
        return getPath(SantulatorSettings::getDrawPath);
    }

    @Override
    public void setDrawPath(final Path path) {
        setPath(SantulatorSettings::setDrawPath, path);
    }

    @Override
    public Optional<WindowSettings> getWindowSettings() {
        WindowSettings value = getValue(SantulatorSettings::getWindowSettings);

        return Optional.ofNullable(value);
    }

    @Override
    public void setWindowSettings(final WindowSettings windowSettings) {
        setValue(SantulatorSettings::setWindowSettings, windowSettings);
    }

    private <T> T getValue(final Function<SantulatorSettings, T> getter) {
        SantulatorSettings settings = readSettings();

        return getter.apply(settings);
    }

    private <T> void setValue(final BiConsumer<SantulatorSettings, T> setter, final T value) {
        SantulatorSettings settings = readSettings();

        setter.accept(settings, value);
        writeSettings(settings);
    }

    private Path getPath(final Function<SantulatorSettings, Path> getter) {
        SantulatorSettings settings = readSettings();
        Path path = getter.apply(settings);

        if (path != null && Files.isDirectory(path)) {
            return path;
        }

        return Paths.get(System.getProperty("user.home"));
    }

    private void setPath(final BiConsumer<SantulatorSettings, Path> setter, final Path path) {
        SantulatorSettings settings = readSettings();

        setter.accept(settings, path);
        writeSettings(settings);
    }
}
