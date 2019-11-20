/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import io.github.santulator.model.GiverAssignment;
import io.github.santulator.model.Person;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MatchingEngine {
    public Optional<List<GiverAssignment>> findMatch(final List<Person> givers, final Collection<Person> receivers, final Set<GiverAssignment> restrictions) {
        MatchExtender matcher = completeMatch(new RootMatcher(restrictions), givers, receivers);

        if (matcher == null) {
            return Optional.empty();
        } else {
            List<GiverAssignment> assignments = matcher.assignmentStream()
                .collect(Collectors.toUnmodifiableList());

            return Optional.of(assignments);
        }
    }

    private MatchExtender completeMatch(
        final MatchExtender matcher, final List<Person> remaining, final Collection<Person> receivers) {
        if (remaining.isEmpty()) {
            return matcher;
        } else {
            Person head = remaining.get(0);
            List<Person> tail = remaining.subList(1, remaining.size());

            for (Person rhs : receivers) {
                GiverAssignment pair = new GiverAssignment(head, rhs);

                if (matcher.isPossibleExtension(pair)) {
                    MatchExtender result = completeMatch(new PairMatch(matcher, pair), tail, receivers);

                    if (result != null) {
                        return result;
                    }
                }
            }

            return null;
        }
    }
}
