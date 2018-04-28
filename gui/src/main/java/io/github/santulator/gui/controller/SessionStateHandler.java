/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.controller;

import io.github.santulator.gui.common.ControllerAndView;
import io.github.santulator.gui.model.SessionModel;
import io.github.santulator.session.SessionState;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionStateHandler {
    private final SessionProvider sessionProvider;

    private final SessionModelTool sessionModelTool;

    private BorderPane mainBorderPane;

    @Inject
    public SessionStateHandler(final SessionProvider sessionProvider, final SessionModelTool sessionModelTool) {
        this.sessionProvider = sessionProvider;
        this.sessionModelTool = sessionModelTool;
    }

    public void initialise(final BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    public SessionState getSessionState(final SessionModel model) {
        return sessionModelTool.buildFileModel(model);
    }

    public SessionModel addSession(final SessionState state) {
        SessionModel sessionModel = sessionModelTool.buildGuiModel(state);
        ControllerAndView<SessionController, Node> cav = sessionProvider.get();
        SessionController controller = cav.getController();

        controller.initialise(sessionModel);
        mainBorderPane.setCenter(cav.getView());

        return sessionModel;
    }
}
