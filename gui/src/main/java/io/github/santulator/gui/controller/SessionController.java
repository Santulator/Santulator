package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.ParticipantModel;
import io.github.santulator.gui.model.SessionModel;
import io.github.santulator.gui.services.UnsavedChangesTool;
import io.github.santulator.gui.view.NoSelectionModel;
import io.github.santulator.gui.view.ParticipantCell;
import io.github.santulator.gui.view.ParticipantSelectionTool;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SessionController {
    @FXML
    private TextField fieldDrawName;

    @FXML
    private TextField fieldPassword;

    @FXML
    private ListView<ParticipantModel> listParticipants;

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
        listParticipants.setCellFactory(p -> new ParticipantCell(tableTool::onActionButton, tableTool::onEnterPress, selectionTool));

        UnsavedChangesTool.createBindings(model);
    }
}
