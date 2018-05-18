package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.MainModel;
import io.github.santulator.gui.model.SessionModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static io.github.santulator.gui.model.SessionModel.DEFAULT_DRAW_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TitleHandlerTest {
    private static final Path SESSION_FILE = Paths.get("root", "saved.file");

    private static final String DRAW_NAME = "New Draw Name";

    private final SessionModel sessionModel = new SessionModel();

    private final MainModel mainModel = new MainModel();

    private final TitleHandler target = new TitleHandler(mainModel);

    @BeforeEach
    public void setUp() {
        mainModel.initialise(sessionModel);
        target.initialise();
    }

    @Test
    public void testInitial() {
        validate(DEFAULT_DRAW_NAME + " - Santulator");
    }

    @Test
    public void testInitialUnsaved() {
        mainModel.changesSavedProperty().set(false);
        validate(DEFAULT_DRAW_NAME + " - Unsaved Changes - Santulator");
    }

    @Test
    public void testSessionFile() {
        mainModel.sessionFileProperty().set(SESSION_FILE);
        validate(DEFAULT_DRAW_NAME + " (saved.file) - Santulator");
    }

    @Test
    public void testSessionFileUnsaved() {
        mainModel.sessionFileProperty().set(SESSION_FILE);
        mainModel.changesSavedProperty().set(false);
        validate(DEFAULT_DRAW_NAME + " (saved.file) - Unsaved Changes - Santulator");
    }

    @Test
    public void testDrawName() {
        sessionModel.setDrawName(DRAW_NAME);
        validate(DRAW_NAME + " - Santulator");
    }

    @Test
    public void testDrawNameUnsaved() {
        sessionModel.setDrawName(DRAW_NAME);
        mainModel.changesSavedProperty().set(false);
        validate(DRAW_NAME + " - Unsaved Changes - Santulator");
    }

    @Test
    public void testSessionFileDrawName() {
        mainModel.sessionFileProperty().set(SESSION_FILE);
        sessionModel.setDrawName(DRAW_NAME);
        validate(DRAW_NAME + " (saved.file) - Santulator");
    }

    @Test
    public void testSessionFileDrawNameUnsaved() {
        mainModel.sessionFileProperty().set(SESSION_FILE);
        sessionModel.setDrawName(DRAW_NAME);
        mainModel.changesSavedProperty().set(false);
        validate(DRAW_NAME + " (saved.file) - Unsaved Changes - Santulator");
    }

    @Test
    public void testClearDrawName() {
        sessionModel.setDrawName("");
        validate("Untitled - Santulator");
    }

    @Test
    public void testClearDrawNameUnsaved() {
        sessionModel.setDrawName("");
        mainModel.changesSavedProperty().set(false);
        validate("Untitled - Unsaved Changes - Santulator");
    }

    @Test
    public void testSessionFileClearDrawName() {
        mainModel.sessionFileProperty().set(SESSION_FILE);
        sessionModel.setDrawName("");
        validate("Untitled (saved.file) - Santulator");
    }

    @Test
    public void testSessionFileClearDrawNameUnsaved() {
        mainModel.sessionFileProperty().set(SESSION_FILE);
        sessionModel.setDrawName("");
        mainModel.changesSavedProperty().set(false);
        validate("Untitled (saved.file) - Unsaved Changes - Santulator");
    }

    private void validate(final String expected) {
        String actual = mainModel.titleProperty().get();

        assertEquals(expected, actual, "Title");
    }
}
