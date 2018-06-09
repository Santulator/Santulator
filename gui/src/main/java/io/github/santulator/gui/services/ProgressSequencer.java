package io.github.santulator.gui.services;

import java.util.List;

public interface ProgressSequencer {
    List<ProgressPoint> sequence(int size);
}
