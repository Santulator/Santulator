/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.controller;

import io.github.santulator.gui.common.ControllerAndView;
import io.github.santulator.gui.model.MainModel;
import io.github.santulator.gui.model.SessionModel;
import io.github.santulator.session.SessionState;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionStateHandler {
    private static final Logger LOG = LoggerFactory.getLogger(SessionStateHandler.class);

    private final MainModel model;

    private BorderPane mainBorderPane;

    private final SessionProvider sessionProvider;

    @Inject
    public SessionStateHandler(final MainModel model, final SessionProvider sessionProvider) {
        this.model = model;
        this.sessionProvider = sessionProvider;
    }

    public void initialise(final BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    public SessionState getSessionState() {
        // TODO Handle real session state here
        LOG.info("TODO - build state for session {}", model);

        return new SessionState();
    }

    public SessionModel addSession(final SessionState state) {
        SessionModelTool sessionTool = new SessionModelTool(state);
        SessionModel sessionModel = sessionTool.buildModel();
        ControllerAndView<SessionController, Node> cav = sessionProvider.get();
        SessionController controller = cav.getController();

        controller.initialise(sessionModel);
        mainBorderPane.setCenter(cav.getView());

        return sessionModel;
    }
}
