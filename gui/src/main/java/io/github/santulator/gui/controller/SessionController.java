package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.ParticipantModel;
import io.github.santulator.gui.model.SessionModel;
import io.github.santulator.gui.services.UnsavedChangesTool;
import io.github.santulator.gui.view.NoSelectionModel;
import io.github.santulator.gui.view.ParticipantCell;
import io.github.santulator.gui.view.ParticipantTableTool;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class SessionController {
    @FXML
    private TextField fieldDrawName;

    @FXML
    private TextField fieldPassword;

    @FXML
    private ListView<ParticipantModel> listParticipants;

    private ParticipantTableTool tableTool;

    private SessionModel model;

    public void initialise(final SessionModel model) {
        this.model = model;

        fieldDrawName.setText(model.getDrawName());
        model.drawNameProperty().bind(fieldDrawName.textProperty());

        fieldPassword.setText(model.getPassword());
        model.passwordProperty().bind(fieldPassword.textProperty());

        tableTool = new ParticipantTableTool(listParticipants);
        tableTool.requestSelection(0);

        listParticipants.setSelectionModel(new NoSelectionModel<>());
        listParticipants.setItems(model.getParticipants());
        listParticipants.setCellFactory(p -> new ParticipantCell(this::onActionButton, this::onEnterPress, tableTool));

        UnsavedChangesTool.createBindings(model);
    }

    private void onActionButton(final ParticipantModel participant) {
        if (participant.isPlaceholder()) {
            addRow();
        } else {
            model.getParticipants().remove(participant);
        }
    }

    private void onEnterPress(final ParticipantModel participant) {
        List<ParticipantModel> participants = model.getParticipants();
        int index = participants.indexOf(participant);

        if (index == participants.size() - 2) {
            addRow();
        } else {
            tableTool.requestSelection(index + 1);
        }
    }

    private void addRow() {
        List<ParticipantModel> participants = model.getParticipants();
        int index = participants.size() - 1;

        participants.get(index).setPlaceholder(false);
        tableTool.requestSelection(index);
        participants.add(new ParticipantModel());
    }
}
