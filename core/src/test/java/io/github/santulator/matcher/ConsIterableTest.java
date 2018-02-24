/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import org.junit.jupiter.api.Test;

import java.util.*;

import static io.github.santulator.core.CoreTool.listOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConsIterableTest {
    private static final String VALUE_FIRST = "FIRST";

    private static final String VALUE_SECOND = "SECOND";

    @Test
    public void testEmpty() {
        ConsIterable<String> target = new ConsIterable<>();

        ConsiteratorTestTool.validate(target);
    }

    @Test
    public void testSingleElement() {
        ConsIterable<String> target = new ConsIterable<>();
        target = new ConsIterable<>(VALUE_FIRST, target);

        ConsiteratorTestTool.validate(target, VALUE_FIRST);
    }

    @Test
    public void testTwoElements() {
        ConsIterable<String> target = new ConsIterable<>();
        target = new ConsIterable<>(VALUE_SECOND, target);
        target = new ConsIterable<>(VALUE_FIRST, target);

        ConsiteratorTestTool.validate(target, VALUE_FIRST, VALUE_SECOND);
    }

    @Test
    public void testIteratorRemove() {
        ConsIterable<String> cons = new ConsIterable<>();
        Iterator<String> i = cons.iterator();

        assertThrows(UnsupportedOperationException.class, i::remove);
    }

    @Test
    public void testIteratorIllegalNext() {
        ConsIterable<String> cons = new ConsIterable<>();
        Iterator<String> i = cons.iterator();

        assertThrows(NoSuchElementException.class, i::next);
    }

    @Test
    public void testToStringEmpty() {
        validateToString("[]");
    }

    @Test
    public void testToStringSingleValue() {
        validateToString("[FIRST]", VALUE_FIRST);
    }

    @Test
    public void testToStringTwoValues() {
        validateToString("[FIRST,SECOND]", VALUE_FIRST, VALUE_SECOND);
    }

    private void validateToString(final String expected, final String... values) {
        List<String> reverse = new ArrayList<>(listOf(values));
        ConsIterable<String> target = new ConsIterable<>();

        Collections.reverse(reverse);
        for (String v : reverse) {
            target = new ConsIterable<>(v, target);
        }
        assertEquals(expected, target.toString(), "Content");
    }
}
