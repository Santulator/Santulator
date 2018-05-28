package io.github.santulator.gui.services;

import io.github.santulator.gui.model.DrawModel;
import io.github.santulator.gui.model.DrawWizardPage;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;

import static javafx.beans.binding.Bindings.*;

public final class DrawModelTool {
    private DrawModelTool() {
        // Prevent instantiation - all methods are static
    }

    public static void createBindings(final DrawModel model) {
        SimpleBooleanProperty drawPerformed = model.drawPerformedProperty();
        SimpleBooleanProperty drawSaved = model.drawSavedProperty();

        drawPerformed.bind(isNotNull(model.drawSelectionProperty()));
        drawSaved.bind(isNotNull(model.directoryProperty()));

        BooleanBinding nextAllowed = when(equal(model.drawWizardPageProperty(), DrawWizardPage.RUN_DRAW))
            .then(drawPerformed)
            .otherwise(drawSaved);
        model.blockNextProperty().bind(nextAllowed.not());
    }
}
