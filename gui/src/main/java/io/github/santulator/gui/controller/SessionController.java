package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.SessionModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SessionController {
    @FXML
    private TextField fieldDrawName;

    @FXML
    private TextField fieldPassword;

    public void initialise(final SessionModel sessionModel) {
        sessionModel.drawNameProperty().bind(fieldDrawName.textProperty());
        sessionModel.passwordProperty().bind(fieldPassword.textProperty());
    }
}
