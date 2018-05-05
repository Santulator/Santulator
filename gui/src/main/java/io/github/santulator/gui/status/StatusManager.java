/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.status;

import java.nio.file.Path;

public interface StatusManager {
    boolean beginNewSession();

    boolean beginOpenSession();

    boolean beginSaveSession();

    boolean beginRunDraw();

    boolean beginExit();

    boolean beginAbout();

    void performAction(Path file);

    void markSuccess();

    void completeAction();
}
