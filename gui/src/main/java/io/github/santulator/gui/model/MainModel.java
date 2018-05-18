/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.nio.file.Path;
import javax.inject.Singleton;

@Singleton
public class MainModel {
    private final SimpleStringProperty title = new SimpleStringProperty();

    private SessionModel sessionModel;

    private final SimpleObjectProperty<Path> sessionFile = new SimpleObjectProperty<>(null);

    private final SimpleBooleanProperty changesSaved = new SimpleBooleanProperty(true);

    private final SimpleStringProperty drawName = new SimpleStringProperty();

    public void initialise(final SessionModel sessionModel) {
        setupSessionModel(sessionModel);
    }

    public void replaceSessionModel(final SessionModel sessionModel) {
        unbindOldSession();
        setupSessionModel(sessionModel);
    }

    private void setupSessionModel(final SessionModel sessionModel) {
        this.sessionModel = sessionModel;
        changesSaved.bindBidirectional(sessionModel.changesSavedProperty());
        sessionFile.bindBidirectional(sessionModel.sessionFileProperty());
        drawName.bind(sessionModel.drawNameProperty());
    }

    private void unbindOldSession() {
        drawName.unbind();
        changesSaved.unbindBidirectional(sessionModel.changesSavedProperty());
        sessionFile.unbindBidirectional(sessionModel.sessionFileProperty());
    }

    public SimpleObjectProperty<Path> sessionFileProperty() {
        return sessionFile;
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(final String title) {
        this.title.set(title);
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

    public void setSessionFile(final Path sessionFile) {
        this.sessionFile.set(sessionFile);
    }

    public boolean hasSessionFile() {
        return sessionFile.getValue() != null;
    }

    public Path getSessionFile() {
        return sessionFile.get();
    }

    public SessionModel getSessionModel() {
        return sessionModel;
    }

    public SimpleStringProperty drawNameProperty() {
        return drawName;
    }

    public String getDrawName() {
        return drawName.get();
    }
}
