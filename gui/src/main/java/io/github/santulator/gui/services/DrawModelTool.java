/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.services;

import io.github.santulator.gui.model.DrawModel;
import io.github.santulator.gui.model.DrawWizardPage;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import static javafx.beans.binding.Bindings.equal;
import static javafx.beans.binding.Bindings.when;

public final class DrawModelTool {
    private DrawModelTool() {
        // Prevent instantiation - all methods are static
    }

    public static void createBindings(final DrawModel model) {
        SimpleBooleanProperty drawPerformed = model.drawPerformedProperty();
        SimpleBooleanProperty drawSaved = model.drawSavedProperty();
        SimpleBooleanProperty drawFailed = model.drawFailedProperty();
        SimpleObjectProperty<DrawWizardPage> drawWizardPage = model.drawWizardPageProperty();

        BooleanBinding nextAllowed = when(equal(drawWizardPage, DrawWizardPage.RUN_DRAW))
            .then(drawPerformed)
            .otherwise(when(equal(drawWizardPage, DrawWizardPage.SAVE_RESULTS))
                .then(drawSaved)
                .otherwise(model.resultsDirectoryOpenedProperty()))
            .and(drawFailed.not());
        model.blockNextProperty().bind(nextAllowed.not());

        StringBinding completedDrawDescription = when(model.drawPerformedProperty())
            .then(model.drawResultDescriptionProperty())
            .otherwise("");
        model.completedDrawDescriptionProperty().bind(completedDrawDescription);

        StringBinding completedSaveDescription = when(model.drawSavedProperty())
            .then(model.savedDrawDescriptionProperty())
            .otherwise("");
        model.completedSaveDescriptionProperty().bind(completedSaveDescription);
    }
}
