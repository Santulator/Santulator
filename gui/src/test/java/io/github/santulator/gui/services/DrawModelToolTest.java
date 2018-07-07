package io.github.santulator.gui.services;

import io.github.santulator.gui.model.DrawModel;
import io.github.santulator.gui.model.DrawWizardPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DrawModelToolTest {
    private static final String RESULT_DESCRIPTION = "RESULT_DESCRIPTION";

    private static final String SAVED_DESCRIPTION = "SAVED_DESCRIPTION";

    private static final Path DIRECTORY = Paths.get("test");

    private final DrawModel model = new DrawModel("Test Draw", "Secret Password");

    @BeforeEach
    public void setUp() {
        DrawModelTool.createBindings(model);
    }

    @Test
    public void testInitial() {
        validate(true, "", "");
    }

    @Test
    public void testDescriptionBeforeDrawComplete() {
        prepareDescriptionBeforeDrawComplete();
        validate(true, "", "");
    }

    private void prepareDescriptionBeforeDrawComplete() {
        model.setDrawResultDescription(RESULT_DESCRIPTION);
    }

    @Test
    public void testDrawPerformedFirstScreen() {
        prepareDrawPerformedFirstScreen();
        validate(false, RESULT_DESCRIPTION, "");
    }

    private void prepareDrawPerformedFirstScreen() {
        prepareDescriptionBeforeDrawComplete();
        model.setDrawPerformed(true);
    }

    @Test
    public void testDrawFailed() {
        prepareDescriptionBeforeDrawComplete();
        model.setDrawFailed(true);
        model.setDrawPerformed(true);
        validate(true, RESULT_DESCRIPTION, "");
    }

    @Test
    public void testDrawPerformedSecondScreen() {
        prepareDrawPerformedSecondScreen();
        validate(true, RESULT_DESCRIPTION, "");
    }

    private void prepareDrawPerformedSecondScreen() {
        prepareDrawPerformedFirstScreen();
        model.setDrawWizardPage(DrawWizardPage.SAVE_RESULTS);
    }

    @Test
    public void testResultsSavedBeforeComplete() {
        prepareResultsSavedBeforeComplete();
        validate(true, RESULT_DESCRIPTION, "");
    }

    private void prepareResultsSavedBeforeComplete() {
        prepareDrawPerformedSecondScreen();
        model.setDirectory(DIRECTORY);
        model.setSavedDrawDescription(SAVED_DESCRIPTION);
    }

    @Test
    public void testResultsSaved() {
        prepareResultsSaved();
        validate(false, RESULT_DESCRIPTION, SAVED_DESCRIPTION);
    }

    private void prepareResultsSaved() {
        prepareResultsSavedBeforeComplete();
        model.setDrawSaved(true);
    }

    @Test
    public void testResultsDirectoryNotOpened() {
        prepareResultsDirectoryNotOpened();
        validate(true, RESULT_DESCRIPTION, SAVED_DESCRIPTION);
    }

    private void prepareResultsDirectoryNotOpened() {
        prepareResultsSaved();
        model.setDrawWizardPage(DrawWizardPage.OPEN_RESULTS);
    }

    @Test
    public void testResultsDirectoryOpened() {
        prepareResultsDirectoryNotOpened();
        model.setResultsDirectoryOpened(true);
        validate(false, RESULT_DESCRIPTION, SAVED_DESCRIPTION);
    }

    private void validate(final boolean isNextBlocked, final String drawDescription, final String saveDescription) {
        assertAll(
            () -> assertEquals(isNextBlocked, model.isBlockNext()),
            () -> assertEquals(drawDescription, model.getCompletedDrawDescription()),
            () -> assertEquals(saveDescription, model.getCompletedSaveDescription())
        );
    }
}