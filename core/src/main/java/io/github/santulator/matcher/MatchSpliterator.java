/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import io.github.santulator.model.GiverAssignment;

import java.util.Spliterator;
import java.util.function.Consumer;

public class MatchSpliterator implements Spliterator<GiverAssignment> {
    private MatchExtender current;

    public MatchSpliterator(final MatchExtender current) {
        this.current = current;
    }

    @Override
    public boolean tryAdvance(final Consumer<? super GiverAssignment> action) {
        boolean isAdvancing = current instanceof PairMatch;

        if (isAdvancing) {
            PairMatch match = (PairMatch) current;

            action.accept(match.getPair());
            current = match.getParent();
        }

        return isAdvancing;
    }

    @Override
    public Spliterator<GiverAssignment> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return Long.MAX_VALUE;
    }

    @Override
    public int characteristics() {
        return 0;
    }
}
