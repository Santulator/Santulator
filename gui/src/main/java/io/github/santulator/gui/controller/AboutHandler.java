/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.controller;

import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.gui.view.ViewFxml;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@Singleton
public class AboutHandler {
    private final Provider<FXMLLoader> loaderProvider;

    private final I18nManager i18nManager;

    @Inject
    public AboutHandler(final Provider<FXMLLoader> loaderProvider, final I18nManager i18nManager) {
        this.loaderProvider = loaderProvider;
        this.i18nManager = i18nManager;
    }

    public void show() {
        Stage stage = new Stage();
        FXMLLoader loader = loaderProvider.get();
        Parent root = ViewFxml.ABOUT.loadNode(loader, i18nManager);
        AboutController controller = loader.getController();

        controller.initialise(stage);
        setupStage(stage, root);
    }

    private void setupStage(final Stage stage, final Parent root) {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}
