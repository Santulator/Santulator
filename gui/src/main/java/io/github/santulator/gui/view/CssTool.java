/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.view;

import javafx.scene.Parent;
import javafx.scene.control.Alert;

public final class CssTool {
    private static final String MAIN_CSS = "main.css";

    private CssTool() {
        // Prevent instantiation - all methods are static
    }

    public static void applyCss(final Alert alert) {
        applyCss(alert.getDialogPane());
    }

    public static void applyCss(final Parent node) {
        node.getStylesheets().add(CssTool.class.getResource("/" + MAIN_CSS).toExternalForm());
    }
}
