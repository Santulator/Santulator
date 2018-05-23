package io.github.santulator.gui.model;

import javafx.beans.property.SimpleStringProperty;

public class DrawModel {
    private final SimpleStringProperty drawName;

    public DrawModel(final String drawName) {
        this.drawName = new SimpleStringProperty(drawName);
    }

    public SimpleStringProperty drawNameProperty() {
        return drawName;
    }
}
