package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.ParticipantModel;
import io.github.santulator.gui.model.SessionModel;
import io.github.santulator.gui.services.UnsavedChangesTool;
import io.github.santulator.gui.view.NoSelectionModel;
import io.github.santulator.gui.view.ParticipantCell;
import io.github.santulator.gui.view.ParticipantSelectionTool;
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

    private ParticipantSelectionTool selectionTool;

    private ParticipantTableTool tableTool;

    public void initialise(final SessionModel model) {
        fieldDrawName.setText(model.getDrawName());
        model.drawNameProperty().bind(fieldDrawName.textProperty());

        fieldPassword.setText(model.getPassword());
        model.passwordProperty().bind(fieldPassword.textProperty());

        selectionTool = new ParticipantSelectionTool(listParticipants);
        selectionTool.requestSelection(0);
        tableTool = new ParticipantTableTool(model.getParticipants(), selectionTool::requestSelection);

        listParticipants.setSelectionModel(new NoSelectionModel<>());
        listParticipants.setItems(model.getParticipants());
        listParticipants.setCellFactory(p -> new ParticipantCell(tableTool::onActionButton, tableTool::onEnterPress, selectionTool));

        UnsavedChangesTool.createBindings(model);
    }
}
