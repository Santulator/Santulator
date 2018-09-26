/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.status;

import io.github.santulator.gui.i18n.I18nKey;
import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.gui.model.StatusModel;
import io.github.santulator.session.FileNameTool;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.inject.Inject;
import javax.inject.Singleton;

import static io.github.santulator.gui.i18n.I18nKey.*;
import static javafx.beans.binding.Bindings.when;

@Singleton
public class StatusManagerImpl implements StatusManager {
    private static final Logger LOG = LoggerFactory.getLogger(StatusManagerImpl.class);

    private final I18nManager i18nManager;

    private I18nKey currentAction;

    private final SimpleBooleanProperty busy = new SimpleBooleanProperty();

    private final SimpleStringProperty actionDescription = new SimpleStringProperty();

    private final AtomicBoolean gatekeeper = new AtomicBoolean();

    @Inject
    public StatusManagerImpl(final I18nManager i18nManager) {
        this.i18nManager = i18nManager;
    }

    @Inject
    public void setStatusModel(final StatusModel model) {
        model.textProperty().bind(when(busy).then(actionDescription).otherwise(""));
        model.busyProperty().bind(busy);
        model.activityProperty().bind(when(busy).then(-1).otherwise(0));
    }

    @Override
    public boolean beginNewSession() {
        return begin(ACTION_NEW);
    }

    @Override
    public boolean beginOpenSession() {
        return begin(ACTION_OPEN);
    }

    @Override
    public boolean beginSaveSession() {
        return begin(ACTION_SAVE);
    }

    @Override
    public boolean beginRunDraw() {
        return begin(ACTION_RUN);
    }

    @Override
    public boolean beginExit() {
        return begin(ACTION_EXIT);
    }

    @Override
    public boolean beginAbout() {
        return begin(ACTION_ABOUT);
    }

    private boolean begin(final I18nKey key) {
        if (gatekeeper.compareAndSet(false, true)) {
            currentAction = key;
            LOG.debug("Begin: {}", currentAction);
            actionDescription.setValue(i18nManager.text(key));
            busy.setValue(true);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void performAction(final Path file) {
        LOG.debug("Perform: {}", currentAction);
        actionDescription.setValue(String.format("%s: '%s'...", i18nManager.text(currentAction), FileNameTool.filename(file)));
    }

    @Override
    public void markSuccess() {
        LOG.debug("Success: {}", currentAction);
    }

    @Override
    public void completeAction() {
        LOG.debug("Complete: {}", currentAction);
        busy.setValue(false);
        gatekeeper.set(false);
    }
}
