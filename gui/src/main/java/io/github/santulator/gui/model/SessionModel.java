package io.github.santulator.gui.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

import static java.util.Collections.singletonList;

public class SessionModel {
    private final SimpleBooleanProperty changesSaved = new SimpleBooleanProperty(true);

    private final SimpleStringProperty drawName = new SimpleStringProperty();

    private final SimpleStringProperty password = new SimpleStringProperty();

    private final SimpleStringProperty directory = new SimpleStringProperty();

    private final ObservableList<ParticipantModel> participants = FXCollections.observableArrayList(ParticipantModel.PROPERTY_EXTRACTOR);

    public SessionModel() {
        this(singletonList(new ParticipantModel(false)));
    }

    public SessionModel(final List<ParticipantModel> participants) {
        this.participants.addAll(participants);
        this.participants.add(new ParticipantModel());
    }

    public SimpleBooleanProperty changesSavedProperty() {
        return changesSaved;
    }

    public void setChangesSaved(final boolean changesSaved) {
        this.changesSaved.set(changesSaved);
    }

    public boolean isChangesSaved() {
        return changesSaved.get();
    }

    public SimpleStringProperty drawNameProperty() {
        return drawName;
    }

    public String getDrawName() {
        return drawName.get();
    }

    public void setDrawName(final String drawName) {
        this.drawName.set(drawName);
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(final String password) {
        this.password.set(password);
    }

    public String getDirectory() {
        return directory.get();
    }

    public void setDirectory(final String directory) {
        this.directory.set(directory);
    }

    public ObservableList<ParticipantModel> getParticipants() {
        return participants;
    }
}
