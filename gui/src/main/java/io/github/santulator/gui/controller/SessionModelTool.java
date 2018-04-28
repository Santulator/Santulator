/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.SessionModel;
import io.github.santulator.session.SessionState;

import javax.inject.Singleton;

@Singleton
public class SessionModelTool {
    public SessionModel buildGuiModel(final SessionState state) {
        SessionModel model = new SessionModel();

        model.setDrawName(state.getDrawName());
        model.setPassword(state.getPassword());

        return model;
    }

    public SessionState buildFileModel(final SessionModel model) {
        SessionState state = new SessionState();

        state.setDrawName(model.getDrawName());
        state.setPassword(model.getPassword());

        return state;
    }
}
