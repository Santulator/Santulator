/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.controller;

import io.github.santulator.gui.common.ControllerAndView;
import io.github.santulator.gui.model.SessionModel;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionStateHandler {
    private final SessionProvider sessionProvider;

    private BorderPane mainBorderPane;

    @Inject
    public SessionStateHandler(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    public void initialise(final BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    public SessionModel addSession() {
        SessionModel model = new SessionModel();

        addSession(model);

        return model;
    }

    public void addSession(final SessionModel sessionModel) {
        ControllerAndView<SessionController, Node> cav = sessionProvider.get();
        SessionController controller = cav.getController();

        controller.initialise(sessionModel);
        mainBorderPane.setCenter(cav.getView());
    }
}
