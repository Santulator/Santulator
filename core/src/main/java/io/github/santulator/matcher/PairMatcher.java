/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

public interface PairMatcher<T> {
    boolean isPossibleMatch(MatcherPair<T> pair);
}
