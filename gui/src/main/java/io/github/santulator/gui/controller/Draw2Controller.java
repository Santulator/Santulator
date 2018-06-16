package io.github.santulator.gui.controller;

import io.github.santulator.core.ThreadPoolTool;
import io.github.santulator.gui.dialogues.FileDialogue;
import io.github.santulator.gui.dialogues.FileDialogueFactory;
import io.github.santulator.gui.dialogues.FileDialogueType;
import io.github.santulator.gui.dialogues.FileErrorTool;
import io.github.santulator.gui.model.DrawModel;
import io.github.santulator.gui.model.MainModel;
import io.github.santulator.gui.services.Progressometer;
import io.github.santulator.gui.status.StatusManager;
import io.github.santulator.model.DrawSelection;
import io.github.santulator.writer.DrawSelectionWriter;
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
import javax.inject.Inject;

import static io.github.santulator.session.FileNameTool.filename;

public class Draw2Controller implements DrawController {
    private static final String TEMPLATE_RESULTS_SAVED = "Results saved in directory '%s'";

    private static final Logger LOG = LoggerFactory.getLogger(Draw2Controller.class);

    @FXML
    private Label labelDraw2Name;

    @FXML
    private Button buttonDraw2SaveResults;

    @FXML
    private Label labelDraw2SavedDescription;

    @FXML
    private ProgressBar barDraw2Progress;

    private final ThreadPoolTool threadPoolTool;

    private final FileDialogueFactory fileDialogueFactory;

    private final StatusManager statusManager;

    private final DrawSelectionWriter writer;

    private final Progressometer progressometer;

    private final MainModel mainModel;

    private DrawModel drawModel;

    private Supplier<Window> windowSupplier;

    @Inject
    public Draw2Controller(final ThreadPoolTool threadPoolTool, final FileDialogueFactory fileDialogueFactory, final StatusManager statusManager,
        final DrawSelectionWriter writer, final Progressometer progressometer, final MainModel mainModel) {
        this.threadPoolTool = threadPoolTool;
        this.fileDialogueFactory = fileDialogueFactory;
        this.statusManager = statusManager;
        this.writer = writer;
        this.progressometer = progressometer;
        this.mainModel = mainModel;
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
            String password = mainModel.getSessionModel().getPassword();

            progressometer.start(selection.getGivers().size());
            writer.writeDrawSelection(selection, directory, password, progressometer::completeTask);
            Platform.runLater(() -> markResultsSaved(directory));
            statusManager.markSuccess();
        } catch (RuntimeException e) {
            FileErrorTool.saveResults(directory, e);
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
        drawModel.setSavedDrawDescription(String.format(TEMPLATE_RESULTS_SAVED, filename(directory)));
    }
}
