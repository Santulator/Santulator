package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.DrawModel;
import io.github.santulator.gui.model.DrawWizardPage;
import io.github.santulator.gui.model.MainModel;
import io.github.santulator.gui.services.DrawModelTool;
import io.github.santulator.gui.status.StatusManager;
import io.github.santulator.gui.view.TrackedWizardPane;
import io.github.santulator.gui.view.ViewFxml;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.controlsfx.dialog.Wizard;
import org.controlsfx.dialog.WizardPane;

import javax.inject.Inject;
import javax.inject.Provider;

public class DrawHandler {
    private final Provider<FXMLLoader> loaderProvider;

    private final StatusManager statusManager;

    private MainModel mainModel;

    @Inject
    public DrawHandler(final Provider<FXMLLoader> loaderProvider, final StatusManager statusManager) {
        this.loaderProvider = loaderProvider;
        this.statusManager = statusManager;
    }

    public void initialise(final MainModel mainModel) {
        this.mainModel = mainModel;
    }

    public void handleRunDraw() {
        if (statusManager.beginRunDraw()) {
            runDrawWizard();
            statusManager.completeAction();
        }
    }

    private void runDrawWizard() {
        DrawModel drawModel = new DrawModel(mainModel.getDrawName());
        WizardPane wizardPane1 = buildWizardPane(ViewFxml.DRAW_1, drawModel, DrawWizardPage.RUN_DRAW);
        WizardPane wizardPane2 = buildWizardPane(ViewFxml.DRAW_2, drawModel, DrawWizardPage.SAVE_RESULTS);
        WizardPane wizardPane3 = buildWizardPane(ViewFxml.DRAW_3, drawModel, DrawWizardPage.OPEN_RESULTS);
        Wizard wizard = new Wizard();

        DrawModelTool.createBindings(drawModel);
        wizard.invalidProperty().bind(drawModel.blockNextProperty());

        wizard.setFlow(new Wizard.LinearFlow(wizardPane1, wizardPane2, wizardPane3));
        wizard.showAndWait();
    }

    private WizardPane buildWizardPane(final ViewFxml viewFxml, final DrawModel drawModel, final DrawWizardPage page) {
        FXMLLoader loader = loaderProvider.get();
        Node wizardPaneContent = viewFxml.loadNode(loader);
        TrackedWizardPane wizardPane = new TrackedWizardPane(wizardPaneContent, () -> drawModel.setDrawWizardPage(page));
        DrawController controller = loader.getController();

        controller.initialise(drawModel, () -> wizardPane.getScene().getWindow());

        return wizardPane;
    }
}
