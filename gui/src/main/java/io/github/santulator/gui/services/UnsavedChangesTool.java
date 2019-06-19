/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.services;

import io.github.santulator.gui.model.ParticipantModel;
import io.github.santulator.gui.model.SessionModel;
import javafx.collections.ListChangeListener;

public final class UnsavedChangesTool {
    private UnsavedChangesTool() {
        // Prevent instantiation - all methods are static
    }

    public static void createBindings(final SessionModel model) {
        model.drawNameProperty().addListener((o, old, v) -> markUnsavedChanges(model));
        model.passwordProperty().addListener((o, old, v) -> markUnsavedChanges(model));
        model.getParticipants().addListener((ListChangeListener<ParticipantModel>) c -> markUnsavedChanges(model));
    }

    private static void markUnsavedChanges(final SessionModel model) {
        model.setChangesSaved(false);
    }
}
