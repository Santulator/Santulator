/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.dialogues;

import io.github.santulator.gui.i18n.I18nKey;
import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.gui.validator.ValidationError;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import static io.github.santulator.gui.view.CssTool.applyCss;

public class ValidationErrorDialogue {
    private static final String ALERT_ID = "validationError";

    private final ValidationError error;

    private final I18nManager i18nManager;

    public ValidationErrorDialogue(final ValidationError error, final I18nManager i18nManager) {
        this.error = error;
        this.i18nManager = i18nManager;
    }

    public void showDialogue() {
        Alert alert = new Alert(AlertType.ERROR);
        String message = error.getMessage(i18nManager);

        alert.setTitle(i18nManager.text(I18nKey.ERROR_DRAW));
        alert.setHeaderText(message);
        alert.getDialogPane().setId(ALERT_ID);
        applyCss(alert);

        alert.showAndWait();
    }
}
