package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.MainModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TitleHandlerTest {
    private static final Path SESSION_FILE = Paths.get("root", "saved.file");

    private final MainModel model = new MainModel();

    private final TitleHandler target = new TitleHandler(model);

    @BeforeEach
    public void setUp() {
        target.initialise();
    }

    @Test
    public void testInitial() {
        validate("Untitled - Santulator");
    }

    @Test
    public void testInitialUnsaved() {
        model.changesSavedProperty().set(false);
        validate("Untitled - Unsaved Changes - Santulator");
    }

    @Test
    public void testSessionFile() {
        model.sessionFileProperty().set(SESSION_FILE);
        validate("saved.file - Santulator");
    }

    @Test
    public void testSessionFileUnsaved() {
        model.sessionFileProperty().set(SESSION_FILE);
        model.changesSavedProperty().set(false);
        validate("saved.file - Unsaved Changes - Santulator");
    }

    private void validate(final String expected) {
        String actual = model.titleProperty().get();

        assertEquals(expected, actual, "Title");
    }
}
