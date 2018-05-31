package io.github.santulator.gui.services;

import io.github.santulator.gui.model.DrawModel;
import io.github.santulator.gui.model.DrawWizardPage;
import io.github.santulator.model.DrawSelection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DrawModelToolTest {
    private static final DrawSelection DRAW_SELECTION = new DrawSelection(Collections.emptyList());

    private static final Path DIRECTORY = Paths.get("test");

    private final DrawModel model = new DrawModel("Test Draw");

    @BeforeEach
    public void setUp() {
        DrawModelTool.createBindings(model);
    }

    @Test
    public void testInitial() {
        validate(false, false, true);
    }

    @Test
    public void testDrawPerformedFirstScreen() {
        model.setDrawSelection(DRAW_SELECTION);
        validate(true, false, false);
    }

    @Test
    public void testDrawFailed() {
        model.setDrawFailed(true);
        validate(true, false, true);
    }

    @Test
    public void testDrawPerformedSecondScreen() {
        model.setDrawSelection(DRAW_SELECTION);
        model.setDrawWizardPage(DrawWizardPage.SAVE_RESULTS);
        validate(true, false, true);
    }

    @Test
    public void testResultsSaved() {
        model.setDrawSelection(DRAW_SELECTION);
        model.setDirectory(DIRECTORY);
        model.setDrawWizardPage(DrawWizardPage.SAVE_RESULTS);
        validate(true, true, false);
    }

    private void validate(final boolean isDrawPerformed, final boolean isDrawSaved, final boolean isNextBlocked) {
        assertEquals(isDrawPerformed, model.isDrawPerformed());
        assertEquals(isDrawSaved, model.isDrawSaved());
        assertEquals(isNextBlocked, model.isBlockNext());
    }
}