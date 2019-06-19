/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.services;

import java.util.List;

public interface ProgressSequencer {
    List<ProgressPoint> sequence(int size);
}
