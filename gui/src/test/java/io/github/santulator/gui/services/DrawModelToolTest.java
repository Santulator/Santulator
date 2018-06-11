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

    private final DrawModel model = new DrawModel("Test Draw");

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
        model.setDrawResultDescription(RESULT_DESCRIPTION);
        validate(true, "", "");
    }

    @Test
    public void testDrawPerformedFirstScreen() {
        model.setDrawResultDescription(RESULT_DESCRIPTION);
        model.setDrawPerformed(true);
        validate(false, RESULT_DESCRIPTION, "");
    }

    @Test
    public void testDrawFailed() {
        model.setDrawResultDescription(RESULT_DESCRIPTION);
        model.setDrawFailed(true);
        model.setDrawPerformed(true);
        validate(true, RESULT_DESCRIPTION, "");
    }

    @Test
    public void testDrawPerformedSecondScreen() {
        model.setDrawResultDescription(RESULT_DESCRIPTION);
        model.setDrawPerformed(true);
        model.setDrawWizardPage(DrawWizardPage.SAVE_RESULTS);
        validate(true, RESULT_DESCRIPTION, "");
    }

    @Test
    public void testResultsSavedBeforeComplete() {
        model.setDrawResultDescription(RESULT_DESCRIPTION);
        model.setDrawPerformed(true);
        model.setDirectory(DIRECTORY);
        model.setDrawWizardPage(DrawWizardPage.SAVE_RESULTS);
        model.setSavedDrawDescription(SAVED_DESCRIPTION);
        validate(true, RESULT_DESCRIPTION, "");
    }

    @Test
    public void testResultsSaved() {
        model.setDrawResultDescription(RESULT_DESCRIPTION);
        model.setDrawPerformed(true);
        model.setDirectory(DIRECTORY);
        model.setDrawWizardPage(DrawWizardPage.SAVE_RESULTS);
        model.setDrawSaved(true);
        model.setSavedDrawDescription(SAVED_DESCRIPTION);
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