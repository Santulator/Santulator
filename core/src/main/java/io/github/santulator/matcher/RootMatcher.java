/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import java.util.Set;

public class RootMatcher<T> implements PairMatcher<T> {
    private final Set<MatcherPair<T>> restrictions;

    public RootMatcher(final Set<MatcherPair<T>> restrictions) {
        this.restrictions = restrictions;
    }

    @Override
    public boolean isPossibleMatch(final MatcherPair<T> pair) {
        return !restrictions.contains(pair);
    }
}
