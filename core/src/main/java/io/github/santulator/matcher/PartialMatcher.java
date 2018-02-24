/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

public class PartialMatcher<T> implements PairMatcher<T> {
    private final PairMatcher<T> parent;

    private final MatcherPair<T> pair;

    public PartialMatcher(final PairMatcher<T> parent, final MatcherPair<T> pair) {
        this.parent = parent;
        this.pair = pair;
    }

    @Override
    public boolean isPossibleMatch(final MatcherPair<T> pair) {
        return !this.pair.getRight().equals(pair.getRight()) && parent.isPossibleMatch(pair);
    }
}
