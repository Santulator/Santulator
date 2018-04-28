package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.SessionModel;
import io.github.santulator.session.SessionState;
import org.junit.jupiter.api.Test;

import static io.github.santulator.session.TestSessionStateTool.*;
import static org.junit.jupiter.api.Assertions.*;

public class SessionModelToolTest {
    private final SessionModelTool target = new SessionModelTool();

    @Test
    public void testBuildGuiModel() {
        SessionState input = buildSimpleState();
        SessionModel result = target.buildGuiModel(input);

        assertAll(
            () -> assertTrue(result.isChangesSaved()),
            () -> assertEquals(DRAW_NAME, result.getDrawName()),
            () -> assertEquals(PASSWORD, result.getPassword())
        );
    }

    @Test
    public void testBuildFileModel() {
        SessionModel input = new SessionModel();

        input.setDrawName(DRAW_NAME);
        input.setPassword(PASSWORD);

        SessionState result = target.buildFileModel(input);

        assertEquals(buildSimpleState(), result);
    }
}