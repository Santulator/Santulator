/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import java.util.HashSet;
import java.util.Set;

public class NodePairSet {
    private final Set<MatcherPair<Node>> set = new HashSet<>();

    public NodePairSet add(final Node left, final Node right) {
        set.add(new MatcherPair<>(left, right));

        return this;
    }

    public Set<MatcherPair<Node>> toSet() {
        return new HashSet<>(set);
    }
}
