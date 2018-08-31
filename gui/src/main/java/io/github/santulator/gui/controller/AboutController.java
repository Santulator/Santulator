package io.github.santulator.gui.controller;

import io.github.santulator.gui.common.BuildInfo;
import io.github.santulator.gui.common.GuiConstants;
import io.github.santulator.gui.services.DesktopResourceTool;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.inject.Inject;

public class AboutController {
    private final DesktopResourceTool desktopResourceTool;
    @FXML
    private Button buttonClose;

    @FXML
    private Hyperlink linkWebsite;

    @FXML
    private Hyperlink linkTwitter;

    @FXML
    private Hyperlink linkGithub;

    @FXML
    private Hyperlink linkGithubStar;

    @FXML
    private Label labelVersion;

    @Inject
    public AboutController(final DesktopResourceTool desktopResourceTool) {
        this.desktopResourceTool = desktopResourceTool;
    }

    public void initialise(final Stage stage) {
        buttonClose.setOnAction(e -> stage.close());
        linkWebsite.setOnAction(e -> desktopResourceTool.showWebPage(GuiConstants.WEBSITE));
        linkTwitter.setOnAction(e -> desktopResourceTool.showWebPage(GuiConstants.TWITTER));
        linkGithub.setOnAction(e -> desktopResourceTool.showWebPage(GuiConstants.GITHUB));
        linkGithubStar.setOnAction(e -> desktopResourceTool.showWebPage(GuiConstants.GITHUB));
        setupVersion();
    }

    private void setupVersion() {
        String template = labelVersion.getText();
        String text = String.format(template, BuildInfo.version());

        labelVersion.setText(text);
    }
}
