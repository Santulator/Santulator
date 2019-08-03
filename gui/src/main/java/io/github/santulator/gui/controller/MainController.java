/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.controller;

import io.github.santulator.gui.common.GuiConstants;
import io.github.santulator.gui.model.MainModel;
import io.github.santulator.gui.model.StatusModel;
import io.github.santulator.gui.services.DesktopResourceTool;
import io.github.santulator.gui.services.EnvironmentManager;
import io.github.santulator.gui.status.StatusManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.StatusBar;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainController {
    @FXML
    private MenuItem menuNew;

    @FXML
    private MenuItem menuOpen;

    @FXML
    private MenuItem menuImport;

    @FXML
    private MenuItem menuSave;

    @FXML
    private MenuItem menuSaveAs;

    @FXML
    private MenuItem menuRunDraw;

    @FXML
    private MenuItem menuWebsite;

    @FXML
    private MenuItem menuHowTo;

    @FXML
    private MenuItem menuIssue;

    @FXML
    private MenuItem menuAbout;

    @FXML
    private Button buttonNew;

    @FXML
    private Button buttonOpen;

    @FXML
    private Button buttonImport;

    @FXML
    private Button buttonSave;

    @FXML
    private Button buttonRunDraw;

    @FXML
    private MenuItem menuExit;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private MenuBar menuBar;

    @FXML
    private StatusBar statusBar;

    @FXML
    private Pane maskerPane;

    private final StatusManager statusManager;

    private final MainModel model;

    private final StatusModel statusModel;

    private final EnvironmentManager environmentManager;

    private final DesktopResourceTool desktopResourceTool;

    private final SessionStateHandler sessionStateHandler;

    private final GuiFileHandler guiFileHandler;

    private final ExitRequestHandler exitRequestHandler;

    private final DrawHandler drawHandler;

    private final AboutHandler aboutHandler;

    @Inject
    public MainController(final StatusManager statusManager, final MainModel model, final StatusModel statusModel, final EnvironmentManager environmentManager,
        final DesktopResourceTool desktopResourceTool, final SessionStateHandler sessionStateHandler, final GuiFileHandler guiFileHandler,
        final ExitRequestHandler exitRequestHandler, final DrawHandler drawHandler, final AboutHandler aboutHandler) {
        this.statusManager = statusManager;
        this.model = model;
        this.statusModel = statusModel;
        this.environmentManager = environmentManager;
        this.desktopResourceTool = desktopResourceTool;
        this.sessionStateHandler = sessionStateHandler;
        this.guiFileHandler = guiFileHandler;
        this.exitRequestHandler = exitRequestHandler;
        this.drawHandler = drawHandler;
        this.aboutHandler = aboutHandler;
    }

    public void initialise(final Stage stage) {
        sessionStateHandler.initialise(mainBorderPane);
        model.initialise(sessionStateHandler.addSession());
        drawHandler.initialise(model);

        handler(buttonOpen, menuOpen, guiFileHandler::handleOpenSession);
        handler(buttonImport, menuImport, guiFileHandler::handleImportSession);
        handler(buttonNew, menuNew, guiFileHandler::handleNewSession);
        handler(buttonSave, menuSave, guiFileHandler::handleSave);
        handler(menuSaveAs, guiFileHandler::handleSaveAs);
        handler(buttonRunDraw, menuRunDraw, drawHandler::handleRunDraw);

        menuWebsite.setOnAction(e -> desktopResourceTool.showWebPage(GuiConstants.WEBSITE));
        menuHowTo.setOnAction(e -> desktopResourceTool.showWebPage(GuiConstants.WEBPAGE_HELP));
        menuIssue.setOnAction(e -> desktopResourceTool.showWebPage(GuiConstants.WEBPAGE_ISSUE));
        menuAbout.setOnAction(e -> processAbout());

        menuBar.setUseSystemMenuBar(environmentManager.useSystemMenuBar());

        menuExit.setOnAction(e -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST)));
        menuExit.setVisible(environmentManager.isExitOptionShown());

        prepareStatusInformation();
    }

    private void prepareStatusInformation() {
        statusBar.textProperty().bind(statusModel.textProperty());
        statusBar.progressProperty().bind(statusModel.activityProperty());

        maskerPane.visibleProperty().bind(statusModel.busyProperty());
    }

    private void handler(final MenuItem menuItem, final Runnable action) {
        menuItem.setOnAction(e -> action.run());
    }

    private void handler(final Button button, final MenuItem menuItem, final Runnable action) {
        EventHandler<ActionEvent> handler = e -> action.run();

        button.setOnAction(handler);
        menuItem.setOnAction(handler);
    }

    private void processAbout() {
        if (statusManager.beginAbout()) {
            try {
                aboutHandler.show();
                statusManager.markSuccess();
            } finally {
                statusManager.completeAction();
            }
        }
    }

    public EventHandler<WindowEvent> getCloseRequestHandler() {
        return this::processCloseRequest;
    }

    private void processCloseRequest(final WindowEvent e) {
        if (statusManager.beginExit()) {
            try {
                if (exitRequestHandler.handleExitRequest(e)) {
                    statusManager.markSuccess();
                }
            } finally {
                statusManager.completeAction();
            }
        }
    }
}
