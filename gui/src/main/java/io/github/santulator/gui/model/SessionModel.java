package io.github.santulator.gui.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.file.Path;
import java.util.List;

import static java.util.Collections.singletonList;

public class SessionModel {
    public static final String DEFAULT_DRAW_NAME = "My Secret Santa Draw";

    public static final String DEFAULT_PASSWORD = "christmas";

    private final SimpleBooleanProperty changesSaved = new SimpleBooleanProperty(true);

    private final SimpleStringProperty drawName = new SimpleStringProperty(DEFAULT_DRAW_NAME);

    private final SimpleStringProperty password = new SimpleStringProperty(DEFAULT_PASSWORD);

    private final SimpleStringProperty directory = new SimpleStringProperty();

    private final ObservableList<ParticipantModel> participants = FXCollections.observableArrayList(ParticipantModel.PROPERTY_EXTRACTOR);

    private final SimpleObjectProperty<Path> sessionFile = new SimpleObjectProperty<>(null);

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

    public Path getSessionFile() {
        return sessionFile.get();
    }

    public void setSessionFile(final Path sessionFile) {
        this.sessionFile.set(sessionFile);
    }

    public SimpleObjectProperty<Path> sessionFileProperty() {
        return sessionFile;
    }
}
