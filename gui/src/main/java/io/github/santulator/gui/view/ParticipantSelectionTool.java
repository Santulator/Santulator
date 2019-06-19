/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.view;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.IdentityHashMap;
import java.util.Map;

public class ParticipantSelectionTool {
    private static final int NO_SELECTION = Integer.MIN_VALUE;

    private final ChangeListener<Boolean> listener = (old, o, v) -> onFocus();

    private final Map<TextField, Integer> fields = new IdentityHashMap<>();

    private int requested = NO_SELECTION;

    private final ListView<?> listView;

    public ParticipantSelectionTool(final ListView<?> listView) {
        this.listView = listView;
    }

    public void requestSelection(final int index) {
        requested = index;
        listView.refresh();
    }

    public void registerField(final TextField field, final int index) {
        Integer old = fields.put(field, index);

        if (old == null) {
            field.focusedProperty().addListener(listener);
        }
        if (index == requested) {
            Platform.runLater(field::requestFocus);
        }
    }

    private void onFocus() {
        requested = NO_SELECTION;
    }
}
