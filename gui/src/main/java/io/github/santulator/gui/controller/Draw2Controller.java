package io.github.santulator.gui.controller;

import io.github.santulator.core.GuiTaskHandler;
import io.github.santulator.gui.dialogues.FileDialogue;
import io.github.santulator.gui.dialogues.FileDialogueFactory;
import io.github.santulator.gui.dialogues.FileDialogueType;
import io.github.santulator.gui.dialogues.FileErrorTool;
import io.github.santulator.gui.model.DrawModel;
import io.github.santulator.gui.services.GuiDrawService;
import io.github.santulator.gui.status.GuiTask;
import io.github.santulator.gui.status.StatusManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Window;
import org.controlsfx.dialog.WizardPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import javax.inject.Inject;

public class Draw2Controller implements DrawController {
    private static final Logger LOG = LoggerFactory.getLogger(Draw2Controller.class);

    private final FileDialogueFactory fileDialogueFactory;

    private final StatusManager statusManager;

    private final GuiTaskHandler guiTaskHandler;

    private final GuiDrawService drawService;

    @FXML
    private WizardPane paneDraw2;

    @FXML
    private Label labelDraw2Name;

    @FXML
    private Button buttonDraw2SaveResults;

    @Inject
    public Draw2Controller(final FileDialogueFactory fileDialogueFactory, final StatusManager statusManager, final GuiTaskHandler guiTaskHandler, final GuiDrawService drawService) {
        this.fileDialogueFactory = fileDialogueFactory;
        this.statusManager = statusManager;
        this.guiTaskHandler = guiTaskHandler;
        this.drawService = drawService;
    }

    @Override
    public void initialise(final DrawModel drawModel) {
        labelDraw2Name.textProperty().bind(drawModel.drawNameProperty());
        buttonDraw2SaveResults.setOnAction(e -> processRunDraw());
    }

    private void processRunDraw() {
        Path directory = chooseFile();

        if (directory != null) {
            statusManager.performAction(directory);
            LOG.info("Running draw in directory '{}'", directory);

            Runnable task = new GuiTask<>(
                guiTaskHandler,
                statusManager,
                () -> runDraw(directory),
                e -> FileErrorTool.saveResults(directory, e),
                false);

            guiTaskHandler.executeInBackground(task);
        }
    }

    private boolean runDraw(final Path directory) {
        drawService.draw(directory);

        return true;
    }

    private Path chooseFile() {
        Window window = paneDraw2.getScene().getWindow();
        FileDialogue dialogue = fileDialogueFactory.create(FileDialogueType.RUN_DRAW, window);

        dialogue.showChooser();
        if (dialogue.isFileSelected()) {
            return dialogue.getSelectedFile();
        } else {
            return null;
        }
    }
}
