package io.github.santulator.gui.main;

import javafx.stage.Stage;

import javax.inject.Singleton;

@Singleton
public class SantulatorGuiImpl implements SantulatorGui {
    @Override
    public void start(final Stage stage) {
        stage.show();
    }
}
