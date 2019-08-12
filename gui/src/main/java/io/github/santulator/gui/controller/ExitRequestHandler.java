/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.controller;

import io.github.santulator.gui.settings.SettingsManager;
import io.github.santulator.gui.settings.WindowSettings;
import io.github.santulator.gui.status.StatusManager;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExitRequestHandler {
    private final GuiFileHandler guiFileHandler;

    private final SettingsManager settingsManager;

    private final StatusManager statusManager;

    private Stage stage;

    @Inject
    public ExitRequestHandler(final GuiFileHandler guiFileHandler, final SettingsManager settingsManager, final StatusManager statusManager) {
        this.guiFileHandler = guiFileHandler;
        this.settingsManager = settingsManager;
        this.statusManager = statusManager;
    }

    public void initialise(final Stage stage) {
        this.stage = stage;
    }

    public void handleExitRequest(final WindowEvent e) {
        if (statusManager.beginExit()) {
            try {
                if (processCloseRequest(e)) {
                    statusManager.markSuccess();
                }
            } finally {
                statusManager.completeAction();
            }
        }
    }


    private boolean processCloseRequest(final WindowEvent e) {
        boolean isContinue = guiFileHandler.unsavedChangesCheck();

        if (isContinue) {
            WindowSettings windowSettings = new WindowSettings();

            windowSettings.setX(stage.getX());
            windowSettings.setY(stage.getY());
            windowSettings.setWidth(stage.getWidth());
            windowSettings.setHeight(stage.getHeight());

            settingsManager.setWindowSettings(windowSettings);
        } else {
            e.consume();
        }

        return isContinue;
    }
}
