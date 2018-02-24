/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import java.util.Iterator;
import java.util.NoSuchElementException;

final class ConsIterator<T> implements Iterator<T> {
    private ConsIterable<T> position;

    public ConsIterator(final ConsIterable<T> position) {
        this.position = position;
    }

    @Override
    public boolean hasNext() {
        return position.getTail() != null;
    }

    @Override
    public T next() {
        if (hasNext()) {
            T result = position.getHead();

            position = position.getTail();

            return result;
        } else {
            throw new NoSuchElementException();
        }
    }
}
