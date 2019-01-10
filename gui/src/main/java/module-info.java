module io.github.santulator.gui {
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires org.apache.commons.lang3;
    requires controlsfx;
    requires javax.inject;
    requires com.google.guice;
    requires slf4j.api;
    requires ignite.guice;
    requires java.desktop;

    requires io.github.santulator.core;

    opens io.github.santulator.gui.main to com.google.guice;
    exports io.github.santulator.gui.main to javafx.graphics;
    opens io.github.santulator.gui.controller to javafx.fxml;
    exports io.github.santulator.gui.controller to com.google.guice;
    exports io.github.santulator.gui.settings to com.fasterxml.jackson.databind, com.google.guice;
    exports io.github.santulator.gui.model to com.google.guice;
    exports io.github.santulator.gui.services to com.google.guice;
    exports io.github.santulator.gui.common to com.google.guice;
    exports io.github.santulator.gui.status to com.google.guice;
    exports io.github.santulator.gui.i18n to com.google.guice;
    exports io.github.santulator.gui.validator to com.google.guice;
}