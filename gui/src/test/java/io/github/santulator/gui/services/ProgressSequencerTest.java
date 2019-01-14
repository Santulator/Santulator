package io.github.santulator.gui.services;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

public class ProgressSequencerTest {
    private final ProgressSequencer target = new ProgressSequencerImpl();

    @Test
    public void testEmpty() {
        validate(0);
    }

    @Test
    public void testSinglePoint() {
        validate(1, new ProgressPoint(80, 1));
    }

    @Test
    public void testTwoPoints() {
        validate(2, new ProgressPoint(50, 1), new ProgressPoint(80, 2));
    }

    @Test
    public void testThreePoints() {
        validate(3, new ProgressPoint(40, 1), new ProgressPoint(60, 2), new ProgressPoint(80, 3));
    }

    @Test
    public void testMaximumDistinctPoints() {
        List<ProgressPoint> expected = IntStream.range(20, 81)
            .mapToObj(i -> new ProgressPoint(i, i - 19))
            .collect(toList());

        validate(61, expected);
    }

    private void validate(final int size, final ProgressPoint... expected) {
        validate(size, List.of(expected));
    }

    private void validate(final int size, final List<ProgressPoint> expected) {
        List<ProgressPoint> result = target.sequence(size);

        assertEquals(expected, result);
    }

    @Test
    public void testThousandPoints() {
        List<ProgressPoint> result = target.sequence(1000);

        assertAll(
            () -> assertEquals(1000, result.size()),
            () -> assertEquals(new ProgressPoint(20, 1), result.get(0)),
            () -> assertEquals(new ProgressPoint(80, 1000), result.get(999)),
            () -> validateSequence(result)
        );
    }

    private void validateSequence(final List<ProgressPoint> result) {
        int previousPercent = Integer.MIN_VALUE;
        int currentTarget = 1;

        for (ProgressPoint point : result) {
            validatePoint(previousPercent, currentTarget, point);
            ++currentTarget;
            previousPercent = point.getPercentage();
        }
    }

    private void validatePoint(final int previousPercent, final int currentTarget, final ProgressPoint point) {
        assertAll(
            () -> assertEquals(currentTarget, point.getTarget()),
            () -> assertTrue(point.getPercentage() >= previousPercent)
        );
    }
}