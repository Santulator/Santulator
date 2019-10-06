/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import io.github.santulator.model.GiverAssignment;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface MatchExtender {
    boolean isPossibleExtension(GiverAssignment pair);

    default Stream<GiverAssignment> assignmentStream() {
        MatchSpliterator spliterator = new MatchSpliterator(this);

        return StreamSupport.stream(spliterator, false);
    }
}
