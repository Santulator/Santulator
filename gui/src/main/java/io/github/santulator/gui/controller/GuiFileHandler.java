/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.controller;

import io.github.santulator.core.GuiTaskHandler;
import io.github.santulator.gui.dialogues.*;
import io.github.santulator.gui.model.MainModel;
import io.github.santulator.gui.model.SessionModel;
import io.github.santulator.gui.services.GuiDrawService;
import io.github.santulator.gui.status.GuiTask;
import io.github.santulator.gui.status.StatusManager;
import io.github.santulator.session.FileNameTool;
import io.github.santulator.session.SessionSerialiser;
import io.github.santulator.session.SessionState;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GuiFileHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GuiFileHandler.class);

    private final FileDialogueFactory fileDialogueFactory;

    private final SessionSerialiser sessionSerialiser;

    private final StatusManager statusManager;

    private final MainModel model;

    private final SessionStateHandler sessionStateHandler;

    private final GuiTaskHandler guiTaskHandler;

    private final GuiDrawService drawService;

    private Stage stage;

    @Inject
    public GuiFileHandler(final FileDialogueFactory fileDialogueFactory, final SessionSerialiser sessionSerialiser, final StatusManager statusManager, final MainModel model,
        final SessionStateHandler sessionStateHandler, final GuiTaskHandler guiTaskHandler, final GuiDrawService drawService) {
        this.fileDialogueFactory = fileDialogueFactory;
        this.sessionSerialiser = sessionSerialiser;
        this.statusManager = statusManager;
        this.model = model;
        this.sessionStateHandler = sessionStateHandler;
        this.guiTaskHandler = guiTaskHandler;
        this.drawService = drawService;
    }

    public void initialise(final Stage stage) {
        this.stage = stage;
    }

    public void handleOpenSession() {
        if (statusManager.beginOpenSession()) {
            guiTaskHandler.pauseThenExecuteOnGuiThread(this::processOpenSession);
        }
    }

    private void processOpenSession() {
        Path file = checkUnsavedChangesAndChooseFile(FileDialogueType.OPEN_SESSION);

        if (file == null) {
            statusManager.completeAction();
        } else {
            statusManager.performAction(file);
            LOG.info("Opening session file '{}'", file);

            GuiTask<SessionState> task = new GuiTask<>(
                guiTaskHandler,
                statusManager,
                () -> sessionSerialiser.read(file),
                this::finishOpen,
                e -> FileErrorTool.open(file, e));

            guiTaskHandler.executeInBackground(task);
        }
    }

    private void finishOpen(final SessionState state) {
        SessionModel sessionModel = sessionStateHandler.addSession(state);

        // TODO Add in the missing session file
        model.replaceSessionModel(sessionModel, null);
    }

    public void handleNewSession() {
        if (statusManager.beginNewSession()) {
            guiTaskHandler.pauseThenExecuteOnGuiThread(this::processNewSession);
        }
    }

    private void processNewSession() {
        try {
            if (unsavedChangesCheck()) {
                LOG.info("New session started");
                finishNew();
                statusManager.markSuccess();
            }
        } finally {
            statusManager.completeAction();
        }
    }

    private void finishNew() {
        SessionModel sessionModel = sessionStateHandler.addSession();

        model.replaceSessionModel(sessionModel, null);
    }

    public void handleSave() {
        if (model.hasSessionFile()) {
            if (statusManager.beginSaveSession()) {
                guiTaskHandler.pauseThenExecuteOnGuiThread(this::processSave);
            }
        } else {
            handleSaveAs();
        }
    }

    public void handleSaveAs() {
        if (statusManager.beginSaveSession()) {
            guiTaskHandler.pauseThenExecuteOnGuiThread(this::processSaveAs);
        }
    }

    private void processSaveAs() {
        Path file = chooseFile(FileDialogueType.SAVE_SESSION);

        if (file == null) {
            statusManager.completeAction();
        } else {
            file = FileNameTool.ensureSessionFileHasSuffix(file);
            model.setSessionFile(file);
            processSave();
        }
    }

    private void processSave() {
        Path file = model.getSessionFile();

        statusManager.performAction(file);
        LOG.info("Saving file '{}'", file);

        SessionState sessionState = buildSessionState();
        GuiTask<Boolean> task = new GuiTask<>(
            guiTaskHandler,
            statusManager,
            () -> saveFile(file, sessionState),
            b -> model.setChangesSaved(true),
            e -> FileErrorTool.save(file, e)
        );

        guiTaskHandler.executeInBackground(task);
    }

    public void handleRunDraw() {
        if (statusManager.beginRunDraw()) {
            guiTaskHandler.pauseThenExecuteOnGuiThread(this::processRunDraw);
        }
    }

    private void processRunDraw() {
        Path directory = chooseFile(FileDialogueType.RUN_DRAW);

        if (directory == null) {
            statusManager.completeAction();
        } else {
            statusManager.performAction(directory);
            LOG.info("Running draw in directory '{}'", directory);

            Runnable task = new GuiTask<>(
                guiTaskHandler,
                statusManager,
                () -> runDraw(directory),
                e -> FileErrorTool.saveResults(directory, e));

            guiTaskHandler.executeInBackground(task);
        }
    }

    private boolean runDraw(final Path directory) {
        drawService.draw(directory);

        return true;
    }

    private boolean saveFile(final Path file, final SessionState sessionState) {
        sessionSerialiser.write(file, sessionState);

        return true;
    }

    private Path checkUnsavedChangesAndChooseFile(final FileDialogueType type) {
        if (unsavedChangesCheck()) {
            return chooseFile(type);
        } else {
            return null;
        }
    }

    private Path chooseFile(final FileDialogueType type) {
        FileDialogue dialogue = fileDialogueFactory.create(type, stage);

        dialogue.showChooser();
        if (dialogue.isFileSelected()) {
            return dialogue.getSelectedFile();
        } else {
            return null;
        }
    }

    public boolean unsavedChangesCheck() {
        if (model.isChangesSaved()) {
            return true;
        } else {
            UnsavedChangesDialogue dialogue = new UnsavedChangesDialogue(model.getSessionFile());

            dialogue.showDialogue();

            switch (dialogue.getUserResponse()) {
                case SAVE:
                    return saveChanges();
                case DISCARD:
                    return true;
                default:
                    return false;
            }
        }
    }

    private boolean saveChanges() {
        if (model.hasSessionFile()) {
            saveChangesInternal();

            return true;
        } else {
            return saveChangesAs();
        }
    }

    private boolean saveChangesAs() {
        Path file = chooseFile(FileDialogueType.SAVE_SESSION);

        if (file == null) {
            return false;
        } else {
            file = FileNameTool.ensureSessionFileHasSuffix(file);

            model.setSessionFile(file);

            return saveChangesInternal();
        }
    }

    private boolean saveChangesInternal() {
        Path file = model.getSessionFile();

        try {
            LOG.info("Saving file '{}'", file);
            sessionSerialiser.write(file, buildSessionState());
            model.setChangesSaved(true);

            return true;
        } catch (final RuntimeException e) {
            FileErrorTool.save(file, e);

            return false;
        }
    }

    private SessionState buildSessionState() {
        SessionModel sessionModel = model.getSessionModel();

        return sessionStateHandler.getSessionState(sessionModel);
    }
}
