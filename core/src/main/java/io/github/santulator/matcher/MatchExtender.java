/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import io.github.santulator.model.GiverAssignment;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface MatchExtender {
    boolean isPossibleExtension(GiverAssignment pair);

    default Stream<GiverAssignment> assignmentStream() {
        Iterator<GiverAssignment> iterator = new MatchIterator(this);
        Iterable<GiverAssignment> iterable = () -> iterator;

        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
