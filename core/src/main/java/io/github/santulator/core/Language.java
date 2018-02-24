package io.github.santulator.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum Language {
    ENGLISH("/english.properties"), SPANISH("/spanish.properties");

    private final String filename;

    Language(final String filename) {
        this.filename = filename;
    }

    public Properties loadProperties() {
        try (InputStream in = Language.class.getResourceAsStream(filename)) {
            Properties properties = new Properties();

            properties.load(in);

            return properties;
        } catch (IOException e) {
            throw new SantaException(String.format("Unable to load properties file '%s'", filename), e);
        }
    }
}
