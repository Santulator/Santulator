/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.view;

import io.github.santulator.core.SantaException;
import io.github.santulator.gui.i18n.I18nManager;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public enum ViewFxml {
    MAIN("main.fxml"),
    SESSION("session.fxml"),
    DRAW_1("draw-1.fxml"),
    DRAW_2("draw-2.fxml"),
    DRAW_3("draw-3.fxml"),
    ABOUT("about.fxml");

    private static final String RESOURCE_BUNDLE = "bundles/santulator-gui-messages";

    private final String name;

    ViewFxml(final String name) {
        this.name = name;
    }

    public <T> T loadNode(final FXMLLoader loader, final I18nManager i18nManager) {
        try {
            loader.setLocation(getClass().getResource("/" + name));
            loader.setResources(i18nManager.guiBundle());

            return loader.load();
        } catch (final IOException e) {
            throw new SantaException(String.format("Unable to load FXML '%s'", name), e);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
