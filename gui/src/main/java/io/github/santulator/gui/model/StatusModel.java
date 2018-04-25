/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.inject.Singleton;

@Singleton
public class StatusModel {
    private final SimpleStringProperty text = new SimpleStringProperty();

    private final SimpleDoubleProperty activity = new SimpleDoubleProperty();

    private final SimpleBooleanProperty busy = new SimpleBooleanProperty();

    public SimpleStringProperty textProperty() {
        return text;
    }

    public SimpleDoubleProperty activityProperty() {
        return activity;
    }

    public SimpleBooleanProperty busyProperty() {
        return busy;
    }
}
