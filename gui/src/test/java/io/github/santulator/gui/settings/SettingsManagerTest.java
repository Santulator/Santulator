package io.github.santulator.gui.settings;

import io.github.santulator.test.core.TestFileManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.github.santulator.gui.settings.SettingsManagerImpl.SETTINGS_JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SettingsManagerTest {
    private final Path home = Paths.get(System.getProperty("user.home"));

    private final WindowSettings windowSettings = buildWindowSettings();

    private TestFileManager files;

    private Path dummyPath;

    private SettingsManager target;

    @BeforeEach
    public void setUp() throws Exception {
        files = new TestFileManager(getClass());
        Path settingsFile = files.addFile(SETTINGS_JSON);
        dummyPath = files.addFile("dummy");
        target = new SettingsManagerImpl(settingsFile);
    }

    @AfterEach
    public void tearDown() throws Exception {
        files.cleanup();
    }

    @Test
    public void testGetDefaultSessionsPath() {
        validateGetDefaultPath(target::getSessionsPath);
    }

    @Test
    public void testUpdateSessionsPath() throws Exception {
        validateUpdatePath(target::getSessionsPath, target::setSessionsPath);
    }

    @Test
    public void testMissingSessionsPath() {
        validateMissingPath(target::getSessionsPath, target::setSessionsPath);
    }

    @Test
    public void testGetDefaultImportPath() {
        validateGetDefaultPath(target::getImportPath);
    }

    @Test
    public void testUpdateImportPath() throws Exception {
        validateUpdatePath(target::getImportPath, target::setImportPath);
    }

    @Test
    public void testMissingImportPath() {
        validateMissingPath(target::getImportPath, target::setImportPath);
    }

    @Test
    public void testGetDefaultDrawPath() {
        validateGetDefaultPath(target::getDrawPath);
    }

    @Test
    public void testUpdateDrawPath() throws Exception {
        validateUpdatePath(target::getDrawPath, target::setDrawPath);
    }

    @Test
    public void testMissingDrawPath() {
        validateMissingPath(target::getDrawPath, target::setDrawPath);
    }

    @Test
    public void testMissingWindowSettings() {
        validateEmpty(target::getWindowSettings);
    }

    @Test
    public void testUpdateWindowSettings() {
        validateOptional(target::getWindowSettings, target::setWindowSettings, windowSettings, windowSettings);
    }

    private void validateGetDefaultPath(final Supplier<Path> getter) {
        Path path = getter.get();

        assertEquals(home, path, "Default path");
    }

    private void validateUpdatePath(final Supplier<Path> getter, final Consumer<Path> setter) throws Exception {
        Files.createDirectories(dummyPath);
        validate(getter, setter, dummyPath);
    }

    private void validateMissingPath(final Supplier<Path> getter, final Consumer<Path> setter) {
        validate(getter, setter, home);
    }

    private <T> void validateOptional(final Supplier<Optional<T>> getter, final Consumer<T> setter, final T expected, final T value) {
        validate(() -> getter.get().get(), setter, expected, value);
    }

    private void validate(final Supplier<Path> getter, final Consumer<Path> setter, final Path expected) {
        Path value = dummyPath;
        validate(getter, setter, expected, value);
    }

    private <T> void validate(final Supplier<T> getter, final Consumer<T> setter, final T expected, final T value) {
        setter.accept(value);
        T path = getter.get();

        assertEquals(expected, path, "Saved value");
    }

    private void validateEmpty(final Supplier<Optional<?>> getter) {
        Optional<?> o = getter.get();

        assertFalse(o.isPresent(), "Empty");
    }

    private WindowSettings buildWindowSettings() {
        WindowSettings settings = new WindowSettings();

        settings.setX(1);
        settings.setY(2);
        settings.setWidth(3);
        settings.setHeight(4);

        return settings;
    }
}
