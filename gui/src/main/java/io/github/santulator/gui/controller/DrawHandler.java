package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.DrawModel;
import io.github.santulator.gui.model.MainModel;
import io.github.santulator.gui.status.StatusManager;
import io.github.santulator.gui.view.ViewFxml;
import javafx.fxml.FXMLLoader;
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
        WizardPane wizardPane1 = buildWizardPane(ViewFxml.DRAW_1);
        WizardPane wizardPane2 = buildWizardPane(ViewFxml.DRAW_2);

        Wizard wizard = new Wizard();

        wizard.setFlow(new Wizard.LinearFlow(wizardPane1, wizardPane2));
        wizard.showAndWait();
    }

    private WizardPane buildWizardPane(final ViewFxml viewFxml) {
        FXMLLoader loader = loaderProvider.get();
        WizardPane wizardPane = viewFxml.loadNode(loader);
        DrawController controller = loader.getController();
        DrawModel drawModel = new DrawModel(mainModel.getDrawName());

        controller.initialise(drawModel);

        return wizardPane;
    }
}
