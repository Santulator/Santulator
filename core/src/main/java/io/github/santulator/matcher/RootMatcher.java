/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import io.github.santulator.model.GiverAssignment;

import java.util.Set;

public class RootMatcher implements MatchExtender {
    private final Set<GiverAssignment> restrictions;

    public RootMatcher(final Set<GiverAssignment> restrictions) {
        this.restrictions = restrictions;
    }

    @Override
    public boolean isPossibleExtension(final GiverAssignment pair) {
        return !(pair.getFrom().equals(pair.getTo()) || restrictions.contains(pair));
    }
}
