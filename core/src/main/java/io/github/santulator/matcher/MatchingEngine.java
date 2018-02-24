/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MatchingEngine {
    public <T> ConsIterable<MatcherPair<T>> findMatch(
            final List<T> leftHandSides, final Collection<T> rightHandSides,
            final Set<MatcherPair<T>> restrictions) {
        PairMatcher<T> matcher = new RootMatcher<>(restrictions);
        ConsIterable<MatcherPair<T>> empty = new ConsIterable<>();

        return completeMatch(matcher, empty, leftHandSides, rightHandSides);
    }

    private <T> ConsIterable<MatcherPair<T>> completeMatch(
            final PairMatcher<T> matcher, final ConsIterable<MatcherPair<T>> partialMatch,
            final List<T> remaining, final Collection<T> rightHandSides) {
        if (remaining.isEmpty()) {
            return partialMatch;
        } else {
            T head = remaining.get(0);
            List<T> tail = remaining.subList(1, remaining.size());

            for (T rhs : rightHandSides) {
                MatcherPair<T> pair = new MatcherPair<>(head, rhs);

                if (!head.equals(rhs) && matcher.isPossibleMatch(pair)) {
                    ConsIterable<MatcherPair<T>> result = completeMatch(
                        new PartialMatcher<>(matcher, pair),
                        new ConsIterable<>(pair, partialMatch),
                        tail, rightHandSides);

                    if (result != null) {
                        return result;
                    }
                }
            }

            return null;
        }
    }
}
