/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import io.github.santulator.model.GiverAssignment;
import io.github.santulator.model.Person;

import java.util.HashSet;
import java.util.Set;

public class PairSetTool {
    private final Set<GiverAssignment> set = new HashSet<>();

    public PairSetTool add(final Person from, final Person to) {
        set.add(new GiverAssignment(from, to));

        return this;
    }

    public Set<GiverAssignment> toSet() {
        return Set.copyOf(set);
    }
}
