package io.github.santulator.gui.services;

import io.github.santulator.gui.model.DrawModel;
import io.github.santulator.gui.model.DrawWizardPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DrawModelToolTest {
    private static final String RESULT_DESCRIPTION = "RESULT_DESCRIPTION";

    private static final Path DIRECTORY = Paths.get("test");

    private final DrawModel model = new DrawModel("Test Draw");

    @BeforeEach
    public void setUp() {
        DrawModelTool.createBindings(model);
    }

    @Test
    public void testInitial() {
        validate(false, true, "");
    }

    @Test
    public void testDesriptionBeforeDrawComplete() {
        model.setDrawResultDescription(RESULT_DESCRIPTION);
        validate(false, true, "");
    }

    @Test
    public void testDrawPerformedFirstScreen() {
        model.setDrawResultDescription(RESULT_DESCRIPTION);
        model.setDrawPerformed(true);
        validate(false, false, RESULT_DESCRIPTION);
    }

    @Test
    public void testDrawFailed() {
        model.setDrawResultDescription(RESULT_DESCRIPTION);
        model.setDrawFailed(true);
        model.setDrawPerformed(true);
        validate(false, true, RESULT_DESCRIPTION);
    }

    @Test
    public void testDrawPerformedSecondScreen() {
        model.setDrawResultDescription(RESULT_DESCRIPTION);
        model.setDrawPerformed(true);
        model.setDrawWizardPage(DrawWizardPage.SAVE_RESULTS);
        validate(false, true, RESULT_DESCRIPTION);
    }

    @Test
    public void testResultsSaved() {
        model.setDrawResultDescription(RESULT_DESCRIPTION);
        model.setDrawPerformed(true);
        model.setDirectory(DIRECTORY);
        model.setDrawWizardPage(DrawWizardPage.SAVE_RESULTS);
        validate(true, false, RESULT_DESCRIPTION);
    }

    private void validate(final boolean isDrawSaved, final boolean isNextBlocked, final String description) {
        assertEquals(isDrawSaved, model.isDrawSaved());
        assertEquals(isNextBlocked, model.isBlockNext());
        assertEquals(description, model.getCompletedDrawDescription());
    }
}