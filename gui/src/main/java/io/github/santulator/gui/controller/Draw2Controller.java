/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.controller;

import io.github.santulator.core.ThreadPoolTool;
import io.github.santulator.gui.dialogues.DialogueTool;
import io.github.santulator.gui.dialogues.FileDialogue;
import io.github.santulator.gui.dialogues.FileDialogueFactory;
import io.github.santulator.gui.dialogues.FileDialogueType;
import io.github.santulator.gui.i18n.I18nKey;
import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.gui.model.DrawModel;
import io.github.santulator.gui.services.Progressometer;
import io.github.santulator.gui.status.StatusManager;
import io.github.santulator.model.DrawSelection;
import io.github.santulator.writer.DrawSelectionWriter;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.function.Supplier;

import static io.github.santulator.session.FileNameTool.filename;

public class Draw2Controller implements DrawController {
    private static final Logger LOG = LoggerFactory.getLogger(Draw2Controller.class);

    @FXML
    private Label labelDraw2Name;

    @FXML
    private Button buttonDraw2SaveResults;

    @FXML
    private Label labelDraw2SavedDescription;

    @FXML
    private ProgressBar barDraw2Progress;

    private final I18nManager i18nManager;

    private final ThreadPoolTool threadPoolTool;

    private final FileDialogueFactory fileDialogueFactory;

    private final StatusManager statusManager;

    private final DrawSelectionWriter writer;

    private final Progressometer progressometer;

    private final DialogueTool dialogueTool;

    private DrawModel drawModel;

    private Supplier<Window> windowSupplier;

    @Inject
    public Draw2Controller(final I18nManager i18nManager, final ThreadPoolTool threadPoolTool, final FileDialogueFactory fileDialogueFactory,
        final StatusManager statusManager, final DrawSelectionWriter writer, final Progressometer progressometer, final DialogueTool dialogueTool) {
        this.i18nManager = i18nManager;
        this.threadPoolTool = threadPoolTool;
        this.fileDialogueFactory = fileDialogueFactory;
        this.statusManager = statusManager;
        this.writer = writer;
        this.progressometer = progressometer;
        this.dialogueTool = dialogueTool;
    }

    @Override
    public void initialise(final DrawModel drawModel, final Supplier<Window> windowSupplier) {
        this.drawModel = drawModel;
        this.windowSupplier = windowSupplier;
        labelDraw2Name.textProperty().bind(drawModel.drawNameProperty());
        labelDraw2SavedDescription.textProperty().bind(drawModel.completedSaveDescriptionProperty());
        buttonDraw2SaveResults.setOnAction(e -> processSaveResults());
        buttonDraw2SaveResults.disableProperty().bind(drawModel.drawSavedProperty());
        barDraw2Progress.progressProperty().bind(progressometer.progressProperty());
        drawModel.drawSavedProperty().bind(progressometer.completeProperty());
    }

    private void processSaveResults() {
        Path directory = chooseDirectory();

        if (directory != null) {
            statusManager.performAction(directory);
            LOG.info("Running draw in directory '{}'", directory);
            threadPoolTool.guiThreadPool().submit(() -> saveResults(directory));
        }
    }

    private void saveResults(final Path directory) {
        try {
            DrawSelection selection = drawModel.getDrawSelection();
            String password = drawModel.getPassword();

            progressometer.start(selection.getGivers().size());
            writer.writeDrawSelection(selection, directory, password, progressometer::completeTask);
            Platform.runLater(() -> markResultsSaved(directory));
            statusManager.markSuccess();
        } catch (RuntimeException e) {
            progressometer.reset();
            Platform.runLater(() -> dialogueTool.errorOnSaveResults(directory, e));
        }
    }

    private Path chooseDirectory() {
        Window window = windowSupplier.get();
        FileDialogue dialogue = fileDialogueFactory.create(FileDialogueType.RUN_DRAW, window);

        dialogue.showChooser();
        if (dialogue.isFileSelected()) {
            return dialogue.getSelectedFile();
        } else {
            return null;
        }
    }

    private void markResultsSaved(final Path directory) {
        drawModel.setDirectory(directory);
        drawModel.setSavedDrawDescription(i18nManager.text(I18nKey.DRAW2_RESULTS, filename(directory)));
    }
}
