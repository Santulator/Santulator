package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.DrawModel;
import javafx.stage.Window;

import java.util.function.Supplier;

public interface DrawController {
    void initialise(final DrawModel drawModel, final Supplier<Window> windowSupplier);
}
