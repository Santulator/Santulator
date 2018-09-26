package io.github.santulator.gui.controller;

import io.github.santulator.gui.dialogues.ValidationErrorDialogue;
import io.github.santulator.gui.i18n.I18nGuiKey;
import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.gui.model.DrawModel;
import io.github.santulator.gui.model.DrawWizardPage;
import io.github.santulator.gui.model.MainModel;
import io.github.santulator.gui.services.DrawModelTool;
import io.github.santulator.gui.status.StatusManager;
import io.github.santulator.gui.validator.ValidationError;
import io.github.santulator.gui.validator.ValidationService;
import io.github.santulator.gui.view.TrackedWizardPane;
import io.github.santulator.gui.view.ViewFxml;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.controlsfx.dialog.Wizard;
import org.controlsfx.dialog.WizardPane;

import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Provider;

import static io.github.santulator.gui.view.CssTool.applyCss;

public class DrawHandler {
    private final Provider<FXMLLoader> loaderProvider;

    private final I18nManager i18nManager;

    private final StatusManager statusManager;

    private final ValidationService validationService;

    private MainModel mainModel;

    @Inject
    public DrawHandler(final Provider<FXMLLoader> loaderProvider, final I18nManager i18nManager, final StatusManager statusManager,
        final ValidationService validationService) {
        this.loaderProvider = loaderProvider;
        this.i18nManager = i18nManager;
        this.statusManager = statusManager;
        this.validationService = validationService;
    }

    public void initialise(final MainModel mainModel) {
        this.mainModel = mainModel;
    }

    public void handleRunDraw() {
        if (statusManager.beginRunDraw()) {
            if (validate()) {
                runDrawWizard();
            }
            statusManager.completeAction();
        }
    }

    private boolean validate() {
        Optional<ValidationError> validationResult = validationService.validate(mainModel.getSessionModel());

        validationResult.ifPresent(error -> {
            ValidationErrorDialogue dialogue = new ValidationErrorDialogue(error, i18nManager);

            dialogue.showDialogue();
        });

        return !validationResult.isPresent();
    }

    private void runDrawWizard() {
        DrawModel drawModel = new DrawModel(mainModel.getDrawName(), mainModel.getSessionModel().getPassword());
        WizardPane wizardPane1 = buildWizardPane(ViewFxml.DRAW_1, drawModel, DrawWizardPage.RUN_DRAW);
        WizardPane wizardPane2 = buildWizardPane(ViewFxml.DRAW_2, drawModel, DrawWizardPage.SAVE_RESULTS);
        WizardPane wizardPane3 = buildWizardPane(ViewFxml.DRAW_3, drawModel, DrawWizardPage.OPEN_RESULTS);
        Wizard wizard = new Wizard(null, i18nManager.guiText(I18nGuiKey.DRAW_TITLE_BAR));

        DrawModelTool.createBindings(drawModel);
        wizard.invalidProperty().bind(drawModel.blockNextProperty());

        wizard.setFlow(new Wizard.LinearFlow(wizardPane1, wizardPane2, wizardPane3));
        wizard.showAndWait();
    }

    private WizardPane buildWizardPane(final ViewFxml viewFxml, final DrawModel drawModel, final DrawWizardPage page) {
        FXMLLoader loader = loaderProvider.get();
        Node wizardPaneContent = viewFxml.loadNode(loader, i18nManager);
        TrackedWizardPane wizardPane = new TrackedWizardPane(wizardPaneContent, () -> drawModel.setDrawWizardPage(page));
        DrawController controller = loader.getController();

        controller.initialise(drawModel, () -> wizardPane.getScene().getWindow());
        applyCss(wizardPane);

        return wizardPane;
    }
}
