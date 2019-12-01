/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import io.github.santulator.model.GiverAssignment;
import io.github.santulator.model.Person;

import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MatcherContext {
    public static final int NO_RECEIVER_ALLOCATED = -1;

    private final List<Person> receivers;

    private final boolean[] satisfiedReceivers;

    private final Set<GiverAssignment> restrictions;

    public MatcherContext(final List<Person> receivers, final Set<GiverAssignment> restrictions) {
        this.receivers = List.copyOf(receivers);
        this.satisfiedReceivers = new boolean[receivers.size()];
        this.restrictions = Set.copyOf(restrictions);
    }

    public OptionalInt allocateNextReceiver(final int previousIndex) {
        if (previousIndex != NO_RECEIVER_ALLOCATED) {
            satisfiedReceivers[previousIndex] = false;
        }

        for (int i = previousIndex + 1; i < satisfiedReceivers.length; i++) {
            if (!satisfiedReceivers[i]) {
                satisfiedReceivers[i] = true;

                return OptionalInt.of(i);
            }
        }

        return OptionalInt.empty();
    }

    public Person lookupReceiver(final int index) {
        return receivers.get(index);
    }

    public Set<Person> getRemainingReceivers() {
        return IntStream.range(0, satisfiedReceivers.length)
            .filter(i -> !satisfiedReceivers[i])
            .mapToObj(receivers::get)
            .collect(Collectors.toUnmodifiableSet());
    }

    public boolean isPossibleMatch(final GiverAssignment pair) {
        return !restrictions.contains(pair);
    }
}
