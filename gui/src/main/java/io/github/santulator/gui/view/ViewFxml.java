/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.view;

import io.github.santulator.core.SantaException;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public enum ViewFxml {
    MAIN("main.fxml"),
    SESSION("session.fxml"),
    DRAW_1("draw-1.fxml"),
    DRAW_2("draw-2.fxml");

    private final String name;

    ViewFxml(final String name) {
        this.name = name;
    }

    public <T> T loadNode(final FXMLLoader loader) {
        try {
            loader.setLocation(getClass().getResource("/" + name));

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
