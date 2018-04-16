package io.github.santulator.gui.controller;

import io.github.santulator.gui.common.GuiConstants;
import io.github.santulator.gui.services.EnvironmentManager;
import io.github.santulator.gui.services.WebPageTool;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainController {
    @FXML
    private MenuItem menuWebsite;

    @FXML
    private MenuItem menuHowTo;

    @FXML
    private MenuItem menuIssue;

    @FXML
    private MenuItem menuExit;

    @FXML
    private MenuBar menuBar;

    private final EnvironmentManager environmentManager;

    private final WebPageTool webPageTool;

    @Inject
    public MainController(final EnvironmentManager environmentManager, final WebPageTool webPageTool) {
        this.environmentManager = environmentManager;
        this.webPageTool = webPageTool;
    }

    public void initialise(final Stage stage) {
        menuWebsite.setOnAction(e -> webPageTool.showWebPage(GuiConstants.WEBSITE));
        menuHowTo.setOnAction(e -> webPageTool.showWebPage(GuiConstants.WEBPAGE_HELP));
        menuIssue.setOnAction(e -> webPageTool.showWebPage(GuiConstants.WEBPAGE_ISSUE));

        menuBar.setUseSystemMenuBar(environmentManager.useSystemMenuBar());

        menuExit.setOnAction(e -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST)));
        menuExit.setVisible(environmentManager.isExitOptionShown());
    }
}
