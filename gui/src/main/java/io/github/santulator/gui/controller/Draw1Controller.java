package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.DrawModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Draw1Controller implements DrawController {
    @FXML
    private Label labelDraw1Name;

    @Override
    public void initialise(final DrawModel drawModel) {
        labelDraw1Name.textProperty().bind(drawModel.drawNameProperty());
    }
}
