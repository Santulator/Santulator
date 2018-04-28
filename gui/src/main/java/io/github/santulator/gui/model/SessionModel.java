package io.github.santulator.gui.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class SessionModel {
    private final SimpleBooleanProperty changesSaved = new SimpleBooleanProperty(true);

    private final SimpleStringProperty drawName = new SimpleStringProperty();

    private final SimpleStringProperty password = new SimpleStringProperty();

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
}
