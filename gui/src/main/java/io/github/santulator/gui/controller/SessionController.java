/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.controller;

import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.gui.model.ParticipantModel;
import io.github.santulator.gui.model.SessionModel;
import io.github.santulator.gui.services.ParticipantTableTool;
import io.github.santulator.gui.services.UnsavedChangesTool;
import io.github.santulator.gui.view.NoSelectionModel;
import io.github.santulator.gui.view.ParticipantCell;
import io.github.santulator.gui.view.ParticipantSelectionTool;
import jakarta.inject.Inject;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SessionController {
    private final I18nManager i18nManager;

    @FXML
    private TextField fieldDrawName;

    @FXML
    private TextField fieldPassword;

    @FXML
    private ListView<ParticipantModel> listParticipants;

    @Inject
    public SessionController(final I18nManager i18nManager) {
        this.i18nManager = i18nManager;
    }

    public void initialise(final SessionModel model) {
        ParticipantSelectionTool selectionTool = new ParticipantSelectionTool(listParticipants);
        ObservableList<ParticipantModel> participants = model.getParticipants();
        ParticipantTableTool tableTool = new ParticipantTableTool(participants, selectionTool::requestSelection);

        tableTool.initialise();

        fieldDrawName.setText(model.getDrawName());
        model.drawNameProperty().bind(fieldDrawName.textProperty());

        fieldPassword.setText(model.getPassword());
        model.passwordProperty().bind(fieldPassword.textProperty());

        selectionTool.requestSelection(0);

        listParticipants.setSelectionModel(new NoSelectionModel<>());
        listParticipants.setItems(participants);
        listParticipants.setCellFactory(p -> new ParticipantCell(i18nManager, tableTool::onActionButton, tableTool::onEnterPress, selectionTool));

        UnsavedChangesTool.createBindings(model);
    }
}
