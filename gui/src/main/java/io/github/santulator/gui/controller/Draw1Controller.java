package io.github.santulator.gui.controller;

import io.github.santulator.core.GuiTaskHandler;
import io.github.santulator.core.SantaException;
import io.github.santulator.engine.DrawService;
import io.github.santulator.gui.model.DrawModel;
import io.github.santulator.gui.model.MainModel;
import io.github.santulator.gui.services.Progressometer;
import io.github.santulator.gui.services.SessionModelTool;
import io.github.santulator.model.DrawRequirements;
import io.github.santulator.model.DrawSelection;
import io.github.santulator.session.SessionState;
import io.github.santulator.session.SessionStateTranslator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;
import javax.inject.Inject;

public class Draw1Controller implements DrawController {
    private static final String TEMPLATE_FAILURE = "Draw failed: %s";

    private static final String TEMPLATE_SUCCESS = "Draw complete, %d gifts will be given";

    private static final Logger LOG = LoggerFactory.getLogger(Draw1Controller.class);

    @FXML
    private Label labelDraw1Name;

    @FXML
    private Button buttonDraw1RunDraw;

    @FXML
    private Label labelDraw1Result;

    @FXML
    private ProgressBar barDraw1Progress;

    private final GuiTaskHandler guiTaskHandler;

    private final DrawService drawService;

    private final SessionStateTranslator translator;

    private final SessionModelTool sessionModelTool;

    private final Progressometer progressometer;

    private final MainModel mainModel;

    private DrawModel drawModel;

    @Inject
    public Draw1Controller(final GuiTaskHandler guiTaskHandler, final DrawService drawService, final SessionStateTranslator translator,
        final SessionModelTool sessionModelTool, final Progressometer progressometer, final MainModel mainModel) {
        this.guiTaskHandler = guiTaskHandler;
        this.drawService = drawService;
        this.translator = translator;
        this.sessionModelTool = sessionModelTool;
        this.mainModel = mainModel;
        this.progressometer = progressometer;
    }

    @Override
    public void initialise(final DrawModel drawModel, final Supplier<Window> windowSupplier) {
        this.drawModel = drawModel;
        labelDraw1Name.textProperty().bind(drawModel.drawNameProperty());
        labelDraw1Result.textProperty().bind(drawModel.completedDrawDescriptionProperty());
        buttonDraw1RunDraw.setOnAction(e -> runDraw());
        buttonDraw1RunDraw.disableProperty().bind(drawModel.drawStartedProperty());
        barDraw1Progress.progressProperty().bind(progressometer.progressProperty());
        drawModel.drawPerformedProperty().bind(progressometer.completeProperty());
    }

    private void runDraw() {
        drawModel.setDrawStarted(true);
        progressometer.start(1);
        guiTaskHandler.executeInBackground(this::runDrawAsynchronous);
    }

    private void runDrawAsynchronous() {
        try {
            SessionState state = sessionModelTool.buildFileModel(mainModel.getSessionModel());
            DrawRequirements requirements = translator.toRequirements(state);
            DrawSelection selection = drawService.draw(requirements);

            guiTaskHandler.executeOnGuiThread(() -> reportDrawSuccess(selection));
        } catch (final SantaException e) {
            LOG.debug("Draw failed", e);
            guiTaskHandler.executeOnGuiThread(() -> reportDrawFailure(e));
        } finally {
            progressometer.completeTask();
        }
    }

    private void reportDrawSuccess(final DrawSelection selection) {
        drawModel.setDrawSelection(selection);
        drawModel.setDrawResultDescription(String.format(TEMPLATE_SUCCESS, selection.getGivers().size()));
    }

    private void reportDrawFailure(final SantaException e) {
        drawModel.setDrawFailed(true);
        drawModel.setDrawResultDescription(String.format(TEMPLATE_FAILURE, e.getMessage()));
    }
}
