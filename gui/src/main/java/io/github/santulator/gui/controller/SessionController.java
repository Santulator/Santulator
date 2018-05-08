package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.ParticipantModel;
import io.github.santulator.gui.model.SessionModel;
import io.github.santulator.gui.view.ParticipantCell;
import io.github.santulator.gui.view.ParticipantTableTool;
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

    private SessionModel model;

    public void initialise(final SessionModel model) {
        this.model = model;

        fieldDrawName.setText(model.getDrawName());
        model.drawNameProperty().bind(fieldDrawName.textProperty());

        fieldPassword.setText(model.getPassword());
        model.passwordProperty().bind(fieldPassword.textProperty());

        ParticipantTableTool tool = new ParticipantTableTool();

        listParticipants.setItems(model.getParticipants());
        listParticipants.setCellFactory(p -> new ParticipantCell(this::onActionButton, tool));
    }

    private void onActionButton(final ParticipantModel participant) {
        if (participant.isPlaceholder()) {
            participant.setPlaceholder(false);
            model.getParticipants().add(new ParticipantModel());
        } else {
            model.getParticipants().remove(participant);
        }
    }
}
