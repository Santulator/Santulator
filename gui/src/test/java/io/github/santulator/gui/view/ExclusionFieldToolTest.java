/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.view;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.github.santulator.gui.view.ExclusionFieldTool.updateExclusions;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExclusionFieldToolTest {
    @Test
    public void testEmpty() {
        validate(List.of(), 0, "");
    }

    @Test
    public void testRemoveFirst() {
        validate(List.of("A"), 0, "");
    }

    @Test
    public void testAddFirst() {
        validate(List.of(), 0, "A", "A");
    }

    @Test
    public void testAddSecond() {
        validate(List.of("A"), 1, "B", "A", "B");
    }

    @Test
    public void testRemoveFirstOfTwo() {
        validate(List.of("A", "B"), 0, "", "", "B");
    }

    @Test
    public void testRemoveSecond() {
        validate(List.of("A", "B"), 1, "", "A");
    }

    @Test
    public void testAddOutsideRange() {
        validate(List.of(), 2, "A", "", "", "A");
    }

    @Test
    public void testCascadeDelete() {
        validate(List.of("", "A", "", "B"), 3, "", "", "A");
    }

    @Test
    public void testReplace() {
        validate(List.of("A", "B"), 1, "C", "A", "C");
    }

    private void validate(final List<String> initial, final int index, final String value, final String... expected) {
        List<String> exclusions = new ArrayList<>(initial);

        updateExclusions(exclusions, index, value);

        assertEquals(List.of(expected), exclusions);
    }
}