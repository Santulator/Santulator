/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import io.github.santulator.model.GiverAssignment;

public class PairMatch implements MatchExtender {
    private final MatchExtender parent;

    private final GiverAssignment pair;

    public PairMatch(final MatchExtender parent, final GiverAssignment pair) {
        this.parent = parent;
        this.pair = pair;
    }

    @Override
    public boolean isPossibleExtension(final GiverAssignment pair) {
        return !this.pair.getTo().equals(pair.getTo()) && parent.isPossibleExtension(pair);
    }

    public GiverAssignment getPair() {
        return pair;
    }

    public MatchExtender getParent() {
        return parent;
    }
}
