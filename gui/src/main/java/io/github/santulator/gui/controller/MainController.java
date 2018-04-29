package io.github.santulator.gui.controller;

import io.github.santulator.gui.common.GuiConstants;
import io.github.santulator.gui.model.MainModel;
import io.github.santulator.gui.model.StatusModel;
import io.github.santulator.gui.services.EnvironmentManager;
import io.github.santulator.gui.services.WebPageTool;
import io.github.santulator.session.SessionState;
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
    private MenuItem menuSave;

    @FXML
    private MenuItem menuSaveAs;

    @FXML
    private MenuItem menuWebsite;

    @FXML
    private MenuItem menuHowTo;

    @FXML
    private MenuItem menuIssue;

    @FXML
    private Button buttonNew;

    @FXML
    private Button buttonOpen;

    @FXML
    private Button buttonSave;

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

    private final MainModel model;

    private final StatusModel statusModel;

    private final EnvironmentManager environmentManager;

    private final WebPageTool webPageTool;

    private final SessionStateHandler sessionStateHandler;

    private final GuiFileHandler guiFileHandler;

    @Inject
    public MainController(final MainModel model, final StatusModel statusModel, final EnvironmentManager environmentManager, final WebPageTool webPageTool,
                          final SessionStateHandler sessionStateHandler, final GuiFileHandler guiFileHandler) {
        this.model = model;
        this.statusModel = statusModel;
        this.environmentManager = environmentManager;
        this.webPageTool = webPageTool;
        this.sessionStateHandler = sessionStateHandler;
        this.guiFileHandler = guiFileHandler;
    }

    public void initialise(final Stage stage) {
        sessionStateHandler.initialise(mainBorderPane);
        model.initialise(sessionStateHandler.addSession(new SessionState()));

        handler(buttonOpen, menuOpen, guiFileHandler::handleOpenSession);
        handler(buttonNew, menuNew, guiFileHandler::handleNewSession);
        handler(buttonSave, menuSave, guiFileHandler::handleSave);
        handler(menuSaveAs, guiFileHandler::handleSaveAs);

        menuWebsite.setOnAction(e -> webPageTool.showWebPage(GuiConstants.WEBSITE));
        menuHowTo.setOnAction(e -> webPageTool.showWebPage(GuiConstants.WEBPAGE_HELP));
        menuIssue.setOnAction(e -> webPageTool.showWebPage(GuiConstants.WEBPAGE_ISSUE));

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
}
