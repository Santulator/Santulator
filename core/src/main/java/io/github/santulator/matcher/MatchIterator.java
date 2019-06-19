/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import io.github.santulator.core.SantaException;
import io.github.santulator.model.GiverAssignment;

import java.util.Iterator;

public class MatchIterator implements Iterator<GiverAssignment> {
    private MatchExtender current;

    public MatchIterator(final MatchExtender current) {
        this.current = current;
    }

    @Override
    public boolean hasNext() {
        return current instanceof PairMatch;
    }

    @Override
    public GiverAssignment next() {
        if (current instanceof PairMatch) {
            PairMatch match = (PairMatch) current;
            GiverAssignment result = match.getPair();

            current = match.getParent();

            return result;
        } else {
            throw new SantaException("Illegal match iterator use");
        }
    }
}
