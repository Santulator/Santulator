/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.model;

import io.github.santulator.gui.i18n.I18nKey;
import jakarta.inject.Singleton;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

@Singleton
public class StatusModel {
    private final SimpleStringProperty text = new SimpleStringProperty();

    private final SimpleDoubleProperty activity = new SimpleDoubleProperty();

    private final SimpleBooleanProperty busy = new SimpleBooleanProperty();

    private ActionStatus actionStatus = ActionStatus.MAIN;

    private I18nKey secondaryAction;

    public String getText() {
        return text.get();
    }

    public void setText(final String text) {
        this.text.set(text);
    }

    public SimpleStringProperty textProperty() {
        return text;
    }

    public double getActivity() {
        return activity.get();
    }

    public void setActivity(final double activity) {
        this.activity.set(activity);
    }

    public SimpleDoubleProperty activityProperty() {
        return activity;
    }

    public boolean isBusy() {
        return busy.get();
    }

    public void setBusy(final boolean busy) {
        this.busy.set(busy);
    }

    public SimpleBooleanProperty busyProperty() {
        return busy;
    }

    public ActionStatus getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(final ActionStatus actionStatus) {
        this.actionStatus = actionStatus;
    }

    public I18nKey getSecondaryAction() {
        return secondaryAction;
    }

    public void setSecondaryAction(final I18nKey secondaryAction) {
        this.secondaryAction = secondaryAction;
    }
}
