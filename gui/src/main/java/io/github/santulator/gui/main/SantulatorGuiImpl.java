/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.main;

import io.github.santulator.gui.common.Placement;
import io.github.santulator.gui.controller.ExitRequestHandler;
import io.github.santulator.gui.controller.GuiFileHandler;
import io.github.santulator.gui.controller.MainController;
import io.github.santulator.gui.controller.TitleHandler;
import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.gui.model.MainModel;
import io.github.santulator.gui.services.ExternalEventBroker;
import io.github.santulator.gui.services.PlacementManager;
import io.github.santulator.gui.view.ViewFxml;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SantulatorGuiImpl implements SantulatorGui {
    private static final Logger LOG = LoggerFactory.getLogger(SantulatorGuiImpl.class);

    private static final int NANOS_PER_MILLI = 1_000_000;

    private final FXMLLoader mainLoader;

    private final I18nManager i18nManager;

    private final PlacementManager placementManager;

    private final GuiFileHandler guiFileHandler;

    private final MainController mainController;

    private final TitleHandler titleHandler;

    private final MainModel model;

    private final ExitRequestHandler exitRequestHandler;

    private final ExternalEventBroker externalEventBroker;

    @Inject
    public SantulatorGuiImpl(final FXMLLoader mainLoader, final I18nManager i18nManager, final PlacementManager placementManager, final GuiFileHandler guiFileHandler,
        final MainController mainController, final TitleHandler titleHandler, final MainModel model, final ExitRequestHandler exitRequestHandler,
        final ExternalEventBroker externalEventBroker) {
        this.mainLoader = mainLoader;
        this.i18nManager = i18nManager;
        this.placementManager = placementManager;
        this.guiFileHandler = guiFileHandler;
        this.mainController = mainController;
        this.titleHandler = titleHandler;
        this.model = model;
        this.exitRequestHandler = exitRequestHandler;
        this.externalEventBroker = externalEventBroker;
    }

    @Override
    public void start(final Stage stage, final long startupTimestampNanos) {
        i18nManager.initialise();

        Parent root = ViewFxml.MAIN.loadNode(mainLoader, i18nManager);

        initialise(stage);

        Scene scene = new Scene(root);

        stage.setOnCloseRequest(exitRequestHandler::handleExitRequest);

        Placement placement = placementManager.getMainWindow();

        stage.setScene(scene);
        stage.setWidth(placement.getWidth());
        stage.setHeight(placement.getHeight());
        if (placement.isPositioned()) {
            stage.setX(placement.getX());
            stage.setY(placement.getY());
        }
        stage.show();
        Platform.runLater(() -> logStartup(startupTimestampNanos));

        externalEventBroker.markGuiOpen(this::handleOpenSession);
    }

    private void initialise(final Stage stage) {
        mainController.initialise(stage);
        guiFileHandler.initialise(stage);
        exitRequestHandler.initialise(stage);
        titleHandler.initialise();

        stage.titleProperty().bind(model.titleProperty());
    }

    private void logStartup(final long startupTimestampNanos) {
        long currentTimestampNanos = System.nanoTime();
        long startupMillis = (currentTimestampNanos - startupTimestampNanos) / NANOS_PER_MILLI;
        String startupTimeText = String.format("%,d", startupMillis);

        LOG.info("User interface started ({} ms)", startupTimeText);
    }

    private void handleOpenSession(final Path file) {
        Platform.runLater(() -> guiFileHandler.handleOpenSession(file));
    }
}
