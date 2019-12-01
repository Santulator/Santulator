/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import io.github.santulator.model.GiverAssignment;
import io.github.santulator.model.Person;

import java.util.OptionalInt;

public class MatcherFrame {
    private final MatcherContext context;

    private final Person lhs;

    private GiverAssignment pair;

    private int receiverIndex = MatcherContext.NO_RECEIVER_ALLOCATED;

    public MatcherFrame(final MatcherContext context, final Person lhs) {
        this.context = context;
        this.lhs = lhs;
    }

    public boolean selectMatch() {
        OptionalInt receiver = context.allocateNextReceiver(receiverIndex);

        while (receiver.isPresent()) {
            receiverIndex = receiver.getAsInt();

            Person rhs = context.lookupReceiver(receiverIndex);

            if (!lhs.equals(rhs)) {
                pair = new GiverAssignment(lhs, rhs);

                if (context.isPossibleMatch(pair)) {
                    return true;
                }
            }
            receiver = context.allocateNextReceiver(receiverIndex);
        }
        pair = null;

        return false;
    }

    public GiverAssignment getPair() {
        if (pair == null) {
            throw new IllegalStateException("Stack frame does not contain a match");
        } else {
            return pair;
        }
    }
}
