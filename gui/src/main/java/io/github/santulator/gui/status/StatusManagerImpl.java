/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.status;

import io.github.santulator.gui.i18n.I18nKey;
import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.gui.model.ActionStatus;
import io.github.santulator.gui.model.StatusModel;
import io.github.santulator.session.FileNameTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;

import static io.github.santulator.gui.i18n.I18nKey.*;

@Singleton
public class StatusManagerImpl implements StatusManager {
    private static final Logger LOG = LoggerFactory.getLogger(StatusManagerImpl.class);

    private static final int ACTIVITY_ON = -1;

    private static final int ACTIVITY_OFF = 0;

    private final I18nManager i18nManager;

    private StatusModel model;

    @Inject
    public StatusManagerImpl(final I18nManager i18nManager) {
        this.i18nManager = i18nManager;
    }

    @Inject
    public void setStatusModel(final StatusModel model) {
        this.model = model;
    }

    @Override
    public boolean beginNewSession() {
        return beginSecondary(ACTION_NEW);
    }

    @Override
    public boolean beginOpenSession() {
        return beginSecondary(ACTION_OPEN);
    }

    @Override
    public boolean beginImportSession() {
        return beginSecondary(ACTION_IMPORT);
    }

    @Override
    public boolean beginSaveSession() {
        return beginSecondary(ACTION_SAVE);
    }

    @Override
    public boolean beginRunDraw() {
        return beginSecondary(ACTION_RUN);
    }

    @Override
    public boolean beginAbout() {
        return beginSecondary(ACTION_ABOUT);
    }

    private boolean beginSecondary(final I18nKey key) {
        Optional<ActionStatus> transition = model.getActionStatus().beginSecondary();

        transition.ifPresent(s -> begin(key, s));

        return transition.isPresent();
    }

    @Override
    public boolean beginExit() {
        Optional<ActionStatus> transition = model.getActionStatus().beginExit();

        transition.ifPresent(s -> begin(ACTION_EXIT, s));

        return transition.isPresent();
    }

    private void begin(final I18nKey key, final ActionStatus actionStatus) {
        LOG.debug("Begin: {}", key);
        transition(key, actionStatus);
    }

    @Override
    public void performAction(final Path file) {
        I18nKey key = model.getSecondaryAction();

        LOG.debug("Perform: {}", key);

        model.setText(String.format("%s: '%s'...", i18nManager.text(key), FileNameTool.filename(file)));
    }

    @Override
    public void markSuccess() {
        LOG.debug("Success: {}", getActionKey());
    }

    @Override
    public void completeAction() {
        ActionStatus newStatus = model.getActionStatus().complete()
            .orElseThrow(() -> new IllegalStateException("No action to complete"));

        LOG.debug("Complete: {}", getActionKey());
        transition(model.getSecondaryAction(), newStatus);
    }

    private void transition(final I18nKey key, final ActionStatus status) {
        boolean isBusy = status.isBusy();

        model.setBusy(isBusy);
        model.setActionStatus(status);
        model.setText(text(key, status));
        model.setActivity(activityValue(isBusy));
        if (status == ActionStatus.SECONDARY) {
            model.setSecondaryAction(key);
        } else if (status == ActionStatus.MAIN) {
            model.setSecondaryAction(null);
        }
    }

    private double activityValue(final boolean isBusy) {
        if (isBusy) {
            return ACTIVITY_ON;
        } else {
            return ACTIVITY_OFF;
        }
    }

    private String text(final I18nKey key, final ActionStatus status) {
        if (status == ActionStatus.MAIN) {
            return "";
        } else if (status == ActionStatus.SECONDARY) {
            return i18nManager.text(key);
        } else {
            return i18nManager.text(ACTION_EXIT);
        }
    }

    private I18nKey getActionKey() {
        if (model.getActionStatus() == ActionStatus.SECONDARY) {
            return model.getSecondaryAction();
        } else {
            return ACTION_EXIT;
        }
    }
}
