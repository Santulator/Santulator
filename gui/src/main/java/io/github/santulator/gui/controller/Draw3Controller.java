package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.DrawModel;
import io.github.santulator.gui.services.DesktopResourceTool;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Window;

import java.util.function.Supplier;
import javax.inject.Inject;

public class Draw3Controller implements DrawController {
    @FXML
    private Label labelDraw3Name;

    @FXML
    private Button buttonDraw3Open;

    private final DesktopResourceTool desktopResourceTool;

    private DrawModel drawModel;

    @Inject
    public Draw3Controller(final DesktopResourceTool desktopResourceTool) {
        this.desktopResourceTool = desktopResourceTool;
    }

    @Override
    public void initialise(final DrawModel drawModel, final Supplier<Window> windowSupplier) {
        this.drawModel = drawModel;
        labelDraw3Name.textProperty().bind(drawModel.drawNameProperty());
        buttonDraw3Open.setOnAction(e -> processOpenResults());
    }

    private void processOpenResults() {
        desktopResourceTool.openPath(drawModel.getDirectory());
        drawModel.setResultsDirectoryOpened(true);
    }
}
