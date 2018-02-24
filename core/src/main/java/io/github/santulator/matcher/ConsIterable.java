/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class ConsIterable<T> implements Iterable<T> {
    private final T head;

    private final ConsIterable<T> tail;

    public ConsIterable() {
        this(null, null);
    }

    public ConsIterable(final T head, final ConsIterable<T> tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public Iterator<T> iterator() {
        return new ConsIterator<>(this);
    }

    public T getHead() {
        return head;
    }

    public ConsIterable<T> getTail() {
        return tail;
    }

    public Stream<T> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    @Override
    public String toString() {
        return stream()
            .map(Object::toString)
            .collect(Collectors.joining(",", "[", "]"));
    }
}
