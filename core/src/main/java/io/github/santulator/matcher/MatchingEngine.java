/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import io.github.santulator.model.GiverAssignment;
import io.github.santulator.model.Person;

import java.util.*;

import static java.util.stream.Collectors.toUnmodifiableList;

public class MatchingEngine {
    private final List<Person> givers;

    private final List<Person> receivers;

    private final Set<GiverAssignment> restrictions;

    private final int maxDepth;

    private final Set<Person> remainingReceivers;

    private final Deque<MatcherFrame> stack;

    public MatchingEngine(final List<Person> givers, final List<Person> receivers, final Set<GiverAssignment> restrictions) {
        this.givers = List.copyOf(givers);
        this.receivers = List.copyOf(receivers);
        this.restrictions = Set.copyOf(restrictions);
        this.remainingReceivers = new HashSet<>(receivers);
        this.maxDepth = givers.size();
        this.stack = new ArrayDeque<>(maxDepth);
    }

    public Optional<List<GiverAssignment>> findMatch() {
        if (givers.isEmpty()) {
            return Optional.of(List.of());
        } else {
            stack.clear();
            if (buildMatch()) {
                List<GiverAssignment> result = stack.stream()
                    .map(MatcherFrame::getPair)
                    .collect(toUnmodifiableList());

                return Optional.of(result);
            } else {
                return Optional.empty();
            }
        }
    }

    private boolean buildMatch() {
        push();
        while (!stack.isEmpty()) {
            MatcherFrame frame = stack.peek();

            if (frame.selectMatch(restrictions, remainingReceivers)) {
                if (stack.size() == maxDepth) {
                    return true;
                } else {
                    push();
                }
            } else {
                stack.pop();
            }
        }

        return false;
    }

    private void push() {
        Person lhs = givers.get(stack.size());

        stack.push(new MatcherFrame(lhs, receivers));
    }
}
