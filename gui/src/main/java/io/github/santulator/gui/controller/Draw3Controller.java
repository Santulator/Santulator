package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.DrawModel;
import io.github.santulator.gui.services.DesktopResourceTool;
import io.github.santulator.gui.view.IconTool;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Window;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.glyphfont.FontAwesome;

import java.util.function.Supplier;
import javax.inject.Inject;

public class Draw3Controller implements DrawController {
    private static final String PASSWORD_TEMPLATE = "Don’t forget that you will need to use the secret password ‘%s' to open the draw results files.";

    @FXML
    private Label labelDraw3Name;

    @FXML
    private Button buttonDraw3Open;

    @FXML
    private Label passwordNotice;

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

        if (StringUtils.isNotBlank(drawModel.getPassword())) {
            String message = String.format(PASSWORD_TEMPLATE, drawModel.getPassword());

            passwordNotice.setText(message);
            passwordNotice.setGraphic(IconTool.icon(FontAwesome.Glyph.LOCK));
        }
    }

    private void processOpenResults() {
        desktopResourceTool.openPath(drawModel.getDirectory());
        drawModel.setResultsDirectoryOpened(true);
    }
}
