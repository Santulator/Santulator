package io.github.santulator.gui.view;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.github.santulator.core.CoreTool.listOf;
import static io.github.santulator.gui.view.ExclusionFieldTool.updateExclusions;
import static org.junit.jupiter.api.Assertions.*;

public class ExclusionFieldToolTest {
    @Test
    public void testEmpty() {
        validate(listOf(), 0, "");
    }

    @Test
    public void testRemoveFirst() {
        validate(listOf("A"), 0, "");
    }

    @Test
    public void testAddFirst() {
        validate(listOf(), 0, "A", "A");
    }

    @Test
    public void testAddSecond() {
        validate(listOf("A"), 1, "B", "A", "B");
    }

    @Test
    public void testRemoveFirstOfTwo() {
        validate(listOf("A", "B"), 0, "", "", "B");
    }

    @Test
    public void testRemoveSecond() {
        validate(listOf("A", "B"), 1, "", "A");
    }

    @Test
    public void testAddOutsideRange() {
        validate(listOf(), 2, "A", "", "", "A");
    }

    @Test
    public void testCascadeDelete() {
        validate(listOf("", "A", "", "B"), 3, "", "", "A");
    }

    @Test
    public void testReplace() {
        validate(listOf("A", "B"), 1, "C", "A", "C");
    }

    private void validate(final List<String> initial, final int index, final String value, final String... expected) {
        List<String> exclusions = new ArrayList<>(initial);

        updateExclusions(exclusions, index, value);

        assertEquals(listOf(expected), exclusions);
    }
}