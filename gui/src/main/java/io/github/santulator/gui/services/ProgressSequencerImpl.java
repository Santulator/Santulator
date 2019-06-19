/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.services;

import java.util.List;
import java.util.stream.IntStream;
import javax.inject.Singleton;

import static java.util.stream.Collectors.toList;

@Singleton
public class ProgressSequencerImpl implements ProgressSequencer {
    private static final int MINIMUM_PERCENTAGE = 20;

    private static final int FINAL_PERCENTAGE = 80;

    @Override
    public List<ProgressPoint> sequence(final int size) {
        return IntStream.range(0, size)
            .mapToObj(i -> point(i, size))
            .collect(toList());
    }

    private ProgressPoint point(final int index, final int size) {
        int target = index + 1;
        int percent = (FINAL_PERCENTAGE - MINIMUM_PERCENTAGE) * target / size + MINIMUM_PERCENTAGE;

        return new ProgressPoint(percent, target);
    }
}
