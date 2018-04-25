/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.controller;

import io.github.santulator.gui.common.ControllerAndView;
import io.github.santulator.gui.view.ViewFxml;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class SessionProvider implements Provider<ControllerAndView<SessionController, Node>> {
    private final Provider<FXMLLoader> loaderProvider;

    @Inject
    public SessionProvider(final Provider<FXMLLoader> loaderProvider) {
        this.loaderProvider = loaderProvider;
    }

    @Override
    public ControllerAndView<SessionController, Node> get() {
        FXMLLoader loader = loaderProvider.get();
        Node root = ViewFxml.SESSION.loadNode(loader);
        SessionController controller = loader.getController();

        return new ControllerAndView<>(controller, root);
    }
}
