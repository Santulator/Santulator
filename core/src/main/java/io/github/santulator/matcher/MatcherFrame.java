/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import io.github.santulator.model.GiverAssignment;
import io.github.santulator.model.Person;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MatcherFrame {
    private final Person lhs;

    private Person rhs;

    private GiverAssignment pair;

    private final Iterator<Person> iterator;

    public MatcherFrame(final Person lhs, final List<Person> receivers) {
        this.lhs = lhs;
        this.iterator = List.copyOf(receivers).iterator();
    }

    public boolean selectMatch(final Set<GiverAssignment> restrictions, final Set<Person> remainingReceivers) {
        if (rhs != null) {
            remainingReceivers.add(rhs);
        }
        while (iterator.hasNext()) {
            rhs = iterator.next();
            if (!lhs.equals(rhs) && remainingReceivers.contains(rhs)) {
                pair = new GiverAssignment(lhs, rhs);
                if (!restrictions.contains(pair)) {
                    remainingReceivers.remove(rhs);

                    return true;
                }
            }
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
