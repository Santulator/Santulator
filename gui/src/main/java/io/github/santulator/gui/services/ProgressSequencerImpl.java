package io.github.santulator.gui.services;

import java.util.Collections;
import java.util.List;
import javax.inject.Singleton;

@Singleton
public class ProgressSequencerImpl implements ProgressSequencer {
    private static final int FINAL_PERCENTAGE = 80;

    @Override
    public List<ProgressPoint> sequence(final int size) {
        return Collections.singletonList(new ProgressPoint(FINAL_PERCENTAGE, 1));
    }
}
