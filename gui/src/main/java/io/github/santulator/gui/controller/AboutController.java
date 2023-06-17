/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.controller;

import io.github.santulator.gui.common.BuildInfo;
import io.github.santulator.gui.common.GuiConstants;
import io.github.santulator.gui.i18n.I18nKey;
import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.gui.services.DesktopResourceTool;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AboutController {
    private final I18nManager i18nManager;

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
    public AboutController(final I18nManager i18nManager, final DesktopResourceTool desktopResourceTool) {
        this.i18nManager = i18nManager;
        this.desktopResourceTool = desktopResourceTool;
    }

    public void initialise(final Stage stage) {
        buttonClose.setOnAction(e -> stage.close());
        linkWebsite.setOnAction(e -> desktopResourceTool.showWebPage(I18nKey.LINK_MAIN));
        linkTwitter.setOnAction(e -> desktopResourceTool.showWebPage(GuiConstants.TWITTER));
        linkGithub.setOnAction(e -> desktopResourceTool.showWebPage(GuiConstants.GITHUB));
        linkGithubStar.setOnAction(e -> desktopResourceTool.showWebPage(GuiConstants.GITHUB));

        labelVersion.setText(i18nManager.text(I18nKey.ABOUT_VERSION, BuildInfo.version()));
    }
}
