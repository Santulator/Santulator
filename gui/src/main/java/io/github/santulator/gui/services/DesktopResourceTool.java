/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.services;

import java.nio.file.Path;

public interface DesktopResourceTool {
    void showWebPage(final String page);

    void openPath(final Path path);
}
