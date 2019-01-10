module io.github.santulator.core {
    requires javax.inject;
    requires com.github.librepdf.openpdf;
    requires org.apache.commons.lang3;
    requires jackson.annotations;
    requires slf4j.api;
    requires poi;
    requires com.fasterxml.jackson.databind;
    requires com.google.guice;
    requires java.desktop;

    exports io.github.santulator.model;
    exports io.github.santulator.core;
    exports io.github.santulator.writer;
    exports io.github.santulator.engine;
    exports io.github.santulator.session;
    exports io.github.santulator.matcher;
}