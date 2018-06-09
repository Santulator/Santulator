package io.github.santulator.gui.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.santulator.core.CoreTool.listOf;

public class ProgressState {
    private static final int MAX_PERCENT = 100;

    private final AtomicInteger currentPercent = new AtomicInteger();

    private final List<ProgressPoint> sequence;

    public ProgressState(final ProgressPoint... sequence) {
        this(listOf(sequence));
    }

    public ProgressState(final List<ProgressPoint> sequence) {
        this.sequence = new ArrayList<>(sequence);
    }

    public int tick(final int size) {
        int lastRead = currentPercent.get();
        int requestedPercent = lastRead + 1;

        if (isFit(size, requestedPercent) && currentPercent.compareAndSet(lastRead, requestedPercent)) {
            return requestedPercent;
        } else {
            return lastRead;
        }
    }

    private boolean isFit(final int size, final int requestedPercent) {
        return requestedPercent <= MAX_PERCENT && sequence.stream()
            .noneMatch(p -> requestedPercent >= p.getPercentage() && size < p.getTarget());
    }

    public boolean isComplete() {
        return currentPercent.get() == MAX_PERCENT;
    }
}
