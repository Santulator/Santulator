/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.SessionModel;
import io.github.santulator.session.SessionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionModelTool {
    private static final Logger LOG = LoggerFactory.getLogger(SessionModelTool.class);

    private final SessionState state;

    public SessionModelTool(final SessionState state) {
        this.state = state;
    }

    public SessionModel buildModel() {
        // TODO Add session model construction
        LOG.info("TODO - build model for session {}", state);

        return new SessionModel();
    }
}
