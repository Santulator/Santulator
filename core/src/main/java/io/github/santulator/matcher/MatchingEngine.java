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

public class MatchingEngine {
    public Optional<MatchExtender> findMatch(
        final List<Person> givers, final Collection<Person> receivers, final Set<GiverAssignment> restrictions) {
        MatchExtender matcher = new RootMatcher(restrictions);

        return completeMatch(matcher, givers, receivers);
    }

    private Optional<MatchExtender> completeMatch(
        final MatchExtender matcher, final List<Person> remaining, final Collection<Person> receivers) {
        if (remaining.isEmpty()) {
            return Optional.of(matcher);
        } else {
            Person head = remaining.get(0);
            List<Person> tail = remaining.subList(1, remaining.size());

            for (Person rhs : receivers) {
                GiverAssignment pair = new GiverAssignment(head, rhs);

                if (matcher.isPossibleExtension(pair)) {
                    Optional<MatchExtender> result = completeMatch(new PairMatch(matcher, pair), tail, receivers);

                    if (result.isPresent()) {
                        return result;
                    }
                }
            }

            return Optional.empty();
        }
    }
}
