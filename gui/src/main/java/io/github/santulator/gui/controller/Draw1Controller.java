package io.github.santulator.gui.controller;

import io.github.santulator.engine.DrawService;
import io.github.santulator.gui.model.DrawModel;
import io.github.santulator.gui.model.MainModel;
import io.github.santulator.gui.services.SessionModelTool;
import io.github.santulator.model.DrawRequirements;
import io.github.santulator.model.DrawSelection;
import io.github.santulator.session.SessionState;
import io.github.santulator.session.SessionStateTranslator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Window;

import java.util.function.Supplier;
import javax.inject.Inject;

public class Draw1Controller implements DrawController {
    @FXML
    private Label labelDraw1Name;

    @FXML
    private Button buttonDraw1RunDraw;

    private final DrawService drawService;

    private final SessionStateTranslator translator;

    private final SessionModelTool sessionModelTool;

    private final MainModel mainModel;

    private DrawModel drawModel;

    @Inject
    public Draw1Controller(final DrawService drawService, final SessionStateTranslator translator, final SessionModelTool sessionModelTool, final MainModel mainModel) {
        this.drawService = drawService;
        this.translator = translator;
        this.sessionModelTool = sessionModelTool;
        this.mainModel = mainModel;
    }

    @Override
    public void initialise(final DrawModel drawModel, final Supplier<Window> windowSupplier) {
        this.drawModel = drawModel;
        labelDraw1Name.textProperty().bind(drawModel.drawNameProperty());
        buttonDraw1RunDraw.setOnAction(e -> runDraw());
        buttonDraw1RunDraw.disableProperty().bind(drawModel.drawPerformedProperty());
    }

    private void runDraw() {
        SessionState state = sessionModelTool.buildFileModel(mainModel.getSessionModel());
        DrawRequirements requirements = translator.toRequirements(state);
        DrawSelection selection = drawService.draw(requirements);

        drawModel.setDrawSelection(selection);
    }
}
