package io.github.santulator.gui.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProgressStateTest {
    private ProgressState target;

    @Test
    public void testEmpty() {
        target = new ProgressState();
        assertFalse(target.isComplete());

        validateIncrement(100, 0, 0);

        validateNoIncrement(100, 0);
        assertTrue(target.isComplete());
    }

    @Test
    public void testPointAtStart() {
        target = new ProgressState(new ProgressPoint(0, 1));
        assertFalse(target.isComplete());

        validateNoIncrement(0, 0);

        validateIncrement(100, 0, 1);

        validateNoIncrement(100, 1);
        assertTrue(target.isComplete());
    }

    @Test
    public void testPointAtStartEnd() {
        target = new ProgressState(new ProgressPoint(100, 1));
        assertFalse(target.isComplete());

        validateIncrement(99, 0, 0);
        validateNoIncrement(99, 0);

        validateIncrement(1, 99, 1);

        validateNoIncrement(100, 1);
        assertTrue(target.isComplete());
    }

    @Test
    public void testThreePoints() {
        target = new ProgressState(
            new ProgressPoint(10, 1),
            new ProgressPoint(20, 2),
            new ProgressPoint(30, 3));
        assertFalse(target.isComplete());

        validateIncrement(9, 0, 0);
        validateNoIncrement(9, 0);

        validateIncrement(10, 9, 1);
        validateNoIncrement(19, 1);

        validateIncrement(10, 19, 2);
        validateNoIncrement(29, 2);

        validateIncrement(71, 29, 3);

        validateNoIncrement(100, 0);
        assertTrue(target.isComplete());
    }

    private void validateIncrement(final int ticks, final int start, final int progress) {
        for (int i = 1; i <= ticks; ++i) {
            int result = target.tick(progress);

            assertEquals(start + i, result);
        }
    }

    private void validateNoIncrement(final int expected, final int progress) {
        int result = target.tick(progress);

        assertEquals(expected, result);
    }
}