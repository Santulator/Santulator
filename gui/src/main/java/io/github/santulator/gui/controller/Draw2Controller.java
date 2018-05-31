package io.github.santulator.gui.controller;

import io.github.santulator.core.GuiTaskHandler;
import io.github.santulator.gui.dialogues.FileDialogue;
import io.github.santulator.gui.dialogues.FileDialogueFactory;
import io.github.santulator.gui.dialogues.FileDialogueType;
import io.github.santulator.gui.dialogues.FileErrorTool;
import io.github.santulator.gui.model.DrawModel;
import io.github.santulator.gui.model.MainModel;
import io.github.santulator.gui.status.GuiTask;
import io.github.santulator.gui.status.StatusManager;
import io.github.santulator.model.DrawSelection;
import io.github.santulator.writer.DrawSelectionWriter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.function.Supplier;
import javax.inject.Inject;

import static io.github.santulator.session.FileNameTool.filename;

public class Draw2Controller implements DrawController {
    private static final String TEMPLATE_RESULTS_SAVED = "Draw results saved to directory '%s'.";

    @FXML
    private Label labelDraw2Name;

    @FXML
    private Button buttonDraw2SaveResults;

    @FXML
    private Label labelDraw2SavedDescription;

    private static final Logger LOG = LoggerFactory.getLogger(Draw2Controller.class);

    private final FileDialogueFactory fileDialogueFactory;

    private final StatusManager statusManager;

    private final GuiTaskHandler guiTaskHandler;

    private final DrawSelectionWriter writer;

    private final MainModel mainModel;

    private DrawModel drawModel;

    private Supplier<Window> windowSupplier;

    @Inject
    public Draw2Controller(final FileDialogueFactory fileDialogueFactory, final StatusManager statusManager, final GuiTaskHandler guiTaskHandler,
        final DrawSelectionWriter writer, final MainModel mainModel) {
        this.fileDialogueFactory = fileDialogueFactory;
        this.statusManager = statusManager;
        this.guiTaskHandler = guiTaskHandler;
        this.writer = writer;
        this.mainModel = mainModel;
    }

    @Override
    public void initialise(final DrawModel drawModel, final Supplier<Window> windowSupplier) {
        this.drawModel = drawModel;
        this.windowSupplier = windowSupplier;
        labelDraw2Name.textProperty().bind(drawModel.drawNameProperty());
        labelDraw2SavedDescription.textProperty().bind(drawModel.savedDrawDescriptionProperty());
        buttonDraw2SaveResults.setOnAction(e -> processSaveResults());
        buttonDraw2SaveResults.disableProperty().bind(drawModel.drawSavedProperty());
    }

    private void processSaveResults() {
        Path directory = chooseDirectory();

        if (directory != null) {
            statusManager.performAction(directory);
            LOG.info("Running draw in directory '{}'", directory);

            Runnable task = new GuiTask<>(
                guiTaskHandler,
                statusManager,
                () -> saveResults(directory),
                this::markResultsSaved,
                e -> FileErrorTool.saveResults(directory, e),
                false);

            guiTaskHandler.executeInBackground(task);
        }
    }

    private Path saveResults(final Path directory) {
        DrawSelection selection = drawModel.getDrawSelection();

        writer.writeDrawSelection(selection, directory, mainModel.getSessionModel().getPassword());

        return directory;
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
