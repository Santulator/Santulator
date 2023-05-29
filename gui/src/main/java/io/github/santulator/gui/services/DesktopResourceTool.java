/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.services;

import io.github.santulator.gui.i18n.I18nKey;

import java.nio.file.Path;

public interface DesktopResourceTool {
    void showWebPage(String page);

    void showWebPage(I18nKey key);

    void openPath(Path path);
}
