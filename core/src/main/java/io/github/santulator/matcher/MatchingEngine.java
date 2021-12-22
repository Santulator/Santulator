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

    private final Deque<MatcherFrame> stack;

    public MatchingEngine(final List<Person> givers, final List<Person> receivers, final Set<GiverAssignment> restrictions) {
        this.givers = List.copyOf(givers);
        this.receivers = List.copyOf(receivers);
        this.restrictions = Set.copyOf(restrictions);
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
                    .toList();

                return Optional.of(result);
            } else {
                return Optional.empty();
            }
        }
    }

    private boolean buildMatch() {
        MatcherContext context = new MatcherContext(receivers, restrictions);

        push(context);
        while (!stack.isEmpty()) {
            MatcherFrame frame = stack.peek();

            if (frame.selectMatch()) {
                if (stack.size() == maxDepth) {
                    return true;
                } else {
                    push(context);
                }
            } else {
                stack.pop();
            }
        }

        return false;
    }

    private void push(final MatcherContext context) {
        Person lhs = givers.get(stack.size());

        stack.push(new MatcherFrame(context, lhs));
    }
}
