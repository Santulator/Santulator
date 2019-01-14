package io.github.santulator.gui.model;

import io.github.santulator.gui.i18n.I18nManager;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.file.Path;
import java.util.List;

import static io.github.santulator.gui.i18n.I18nKey.SESSION_DEFAULT_DRAW;
import static io.github.santulator.gui.i18n.I18nKey.SESSION_DEFAULT_PASSWORD;

public class SessionModel {
    private final SimpleBooleanProperty changesSaved = new SimpleBooleanProperty(true);

    private final SimpleStringProperty drawName;

    private final SimpleStringProperty password;

    private final ObservableList<ParticipantModel> participants = FXCollections.observableArrayList(ParticipantModel.PROPERTY_EXTRACTOR);

    private final SimpleObjectProperty<Path> sessionFile = new SimpleObjectProperty<>(null);

    public SessionModel(final I18nManager i18nManager) {
        this(i18nManager, List.of(new ParticipantModel(false)));
    }

    public SessionModel(final I18nManager i18nManager, final List<ParticipantModel> participants) {
        this.participants.addAll(participants);
        this.participants.add(new ParticipantModel());
        this.drawName = new SimpleStringProperty(i18nManager.text(SESSION_DEFAULT_DRAW));
        this.password = new SimpleStringProperty(i18nManager.text(SESSION_DEFAULT_PASSWORD));
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
