/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.writer;

import io.github.santulator.model.DrawSelection;

import java.nio.file.Path;

public interface DrawSelectionWriter {
    void writeDrawSelection(DrawSelection selection, Path output);

    void writeDrawSelection(DrawSelection selection, Path output, String password, final Runnable onWriteComplete);
}
