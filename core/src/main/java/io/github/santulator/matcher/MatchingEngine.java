/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import io.github.santulator.model.GiverAssignment;
import io.github.santulator.model.Person;

import java.util.*;

public class MatchingEngine {
    private final List<Person> givers;

    private final List<Person> receivers;

    private final Set<GiverAssignment> restrictions;

    private final int maxDepth;

    private final Set<Person> remainingReceivers;

    private final List<GiverAssignment> partialResult;

    private int depth = 0;

    public MatchingEngine(final List<Person> givers, final List<Person> receivers, final Set<GiverAssignment> restrictions) {
        this.givers = List.copyOf(givers);
        this.receivers = List.copyOf(receivers);
        this.restrictions = Set.copyOf(restrictions);
        this.remainingReceivers = new HashSet<>(receivers);
        maxDepth = givers.size();
        this.partialResult = new ArrayList<>(maxDepth);
    }

    public Optional<List<GiverAssignment>> findMatch() {
        List<GiverAssignment> result = buildMatch();

        return Optional.ofNullable(result);
    }

    private List<GiverAssignment> buildMatch() {
        if (depth == maxDepth) {
            return partialResult;
        } else {
            Person lhs = givers.get(depth);

            for (Person rhs : receivers) {
                if (!lhs.equals(rhs) && remainingReceivers.contains(rhs)) {
                    List<GiverAssignment> result = buildMatch(lhs, rhs);

                    if (result != null) {
                        return result;
                    }
                }
            }

            return null;
        }
    }

    private List<GiverAssignment> buildMatch(final Person lhs, final Person rhs) {
        GiverAssignment pair = new GiverAssignment(lhs, rhs);

        if (!restrictions.contains(pair)) {
            ++depth;
            partialResult.add(pair);
            remainingReceivers.remove(rhs);

            List<GiverAssignment> result = buildMatch();

            if (result != null) {
                return result;
            }

            --depth;
            remainingReceivers.add(rhs);
            partialResult.remove(depth);
        }

        return null;
    }
}
