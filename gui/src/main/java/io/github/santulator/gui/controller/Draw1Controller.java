/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.controller;

import io.github.santulator.core.SantaException;
import io.github.santulator.engine.DrawService;
import io.github.santulator.gui.common.GuiTaskHandler;
import io.github.santulator.gui.i18n.I18nKey;
import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.gui.model.DrawModel;
import io.github.santulator.gui.model.MainModel;
import io.github.santulator.gui.services.Progressometer;
import io.github.santulator.gui.services.SessionModelTool;
import io.github.santulator.model.DrawRequirements;
import io.github.santulator.model.DrawSelection;
import io.github.santulator.model.SessionState;
import io.github.santulator.session.SessionStateTranslator;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

import static io.github.santulator.gui.i18n.I18nKey.DRAW1_FAILURE;
import static io.github.santulator.gui.i18n.I18nKey.DRAW1_SUCCESS;

public class Draw1Controller implements DrawController {
    private static final Logger LOG = LoggerFactory.getLogger(Draw1Controller.class);

    @FXML
    private Label labelDraw1Name;

    @FXML
    private Button buttonDraw1RunDraw;

    @FXML
    private Label labelDraw1Result;

    @FXML
    private ProgressBar barDraw1Progress;

    private final I18nManager i18nManager;

    private final GuiTaskHandler guiTaskHandler;

    private final DrawService drawService;

    private final SessionStateTranslator translator;

    private final SessionModelTool sessionModelTool;

    private final Progressometer progressometer;

    private final MainModel mainModel;

    private DrawModel drawModel;

    @Inject
    public Draw1Controller(final I18nManager i18nManager, final GuiTaskHandler guiTaskHandler, final DrawService drawService,
        final SessionStateTranslator translator, final SessionModelTool sessionModelTool, final Progressometer progressometer, final MainModel mainModel) {
        this.i18nManager = i18nManager;
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
        applyDescription(DRAW1_SUCCESS, selection.getGivers().size());
    }

    private void reportDrawFailure(final SantaException e) {
        drawModel.setDrawFailed(true);
        applyDescription(DRAW1_FAILURE, e.getMessage());
    }

    private void applyDescription(final I18nKey key, final Object argument) {
        String text = i18nManager.text(key, argument);

        drawModel.setDrawResultDescription(text);
    }
}
