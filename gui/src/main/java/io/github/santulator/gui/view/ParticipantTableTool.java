package io.github.santulator.gui.view;

import javafx.application.Platform;
import javafx.scene.control.TextField;

public class ParticipantTableTool {
    private int requested = 0;

    public void registerField(final TextField field, final int index) {
        if (index == requested) {
            field.focusedProperty().addListener((old, o, v) -> onFocus());
            Platform.runLater(() -> field.requestFocus());
        }
    }

    private void onFocus() {
        requested = Integer.MIN_VALUE;
    }
}
