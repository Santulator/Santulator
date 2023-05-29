/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.DrawModel;
import javafx.stage.Window;

import java.util.function.Supplier;

public interface DrawController {
    void initialise(DrawModel drawModel, Supplier<Window> windowSupplier);
}
