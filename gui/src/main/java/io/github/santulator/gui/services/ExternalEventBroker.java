/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.services;

import java.nio.file.Path;
import java.util.function.Consumer;

public interface ExternalEventBroker {
    void openFile(Path file);

    void markGuiOpen(Consumer<Path> fileProcessor);
}
