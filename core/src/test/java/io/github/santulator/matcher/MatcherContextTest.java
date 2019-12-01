/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import io.github.santulator.model.GiverAssignment;
import io.github.santulator.model.Person;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.OptionalInt;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MatcherContextTest {
    private static final Person PERSON_1 = new Person("PERSON_1");

    private static final Person PERSON_2 = new Person("PERSON_2");

    private static final Person PERSON_3 = new Person("PERSON_3");

    private static final List<Person> RECEIVERS = List.of(PERSON_1, PERSON_2, PERSON_3);

    private static final GiverAssignment ASSIGNMENT_1_2 = new GiverAssignment(PERSON_1, PERSON_2);

    private static final GiverAssignment ASSIGNMENT_1_3 = new GiverAssignment(PERSON_1, PERSON_3);

    private static final Set<GiverAssignment> RESTRICTIONS = Set.of(ASSIGNMENT_1_2);

    private final MatcherContext target = new MatcherContext(RECEIVERS, RESTRICTIONS);

    @Test
    public void testLookupReceiver() {
        assertAll(
            () -> assertEquals(PERSON_1, target.lookupReceiver(0)),
            () -> assertEquals(PERSON_2, target.lookupReceiver(1)),
            () -> assertEquals(PERSON_3, target.lookupReceiver(2))
        );
    }

    @Test
    public void testIsPossibleMatch() {
        assertAll(
            () -> assertFalse(target.isPossibleMatch(ASSIGNMENT_1_2)),
            () -> assertTrue(target.isPossibleMatch(ASSIGNMENT_1_3))
        );
    }

    @Test
    public void testInitialReceivers() {
        assertEquals(Set.of(PERSON_1, PERSON_2, PERSON_3), target.getRemainingReceivers());
    }

    @Test
    public void testAllocateFirst() {
        OptionalInt result = target.allocateNextReceiver(MatcherContext.NO_RECEIVER_ALLOCATED);

        assertAll(
            () -> assertEquals(0, result.orElseThrow()),
            () -> assertEquals(Set.of(PERSON_2, PERSON_3), target.getRemainingReceivers())
        );
    }

    @Test
    public void testAllocateSecond() {
        OptionalInt first = target.allocateNextReceiver(MatcherContext.NO_RECEIVER_ALLOCATED);
        OptionalInt result = target.allocateNextReceiver(first.orElseThrow());

        assertAll(
            () -> assertEquals(1, result.orElseThrow()),
            () -> assertEquals(Set.of(PERSON_1, PERSON_3), target.getRemainingReceivers())
        );
    }

    @Test
    public void testAllocateThird() {
        OptionalInt first = target.allocateNextReceiver(MatcherContext.NO_RECEIVER_ALLOCATED);
        OptionalInt second = target.allocateNextReceiver(first.orElseThrow());
        OptionalInt result = target.allocateNextReceiver(second.orElseThrow());

        assertAll(
            () -> assertEquals(2, result.orElseThrow()),
            () -> assertEquals(Set.of(PERSON_1, PERSON_2), target.getRemainingReceivers())
        );
    }

    @Test
    public void testAllocateFourth() {
        OptionalInt first = target.allocateNextReceiver(MatcherContext.NO_RECEIVER_ALLOCATED);
        OptionalInt second = target.allocateNextReceiver(first.orElseThrow());
        OptionalInt third = target.allocateNextReceiver(second.orElseThrow());
        OptionalInt result = target.allocateNextReceiver(third.orElseThrow());

        assertAll(
            () -> assertFalse(result.isPresent()),
            () -> assertEquals(Set.of(PERSON_1, PERSON_2, PERSON_3), target.getRemainingReceivers())
        );
    }

    @Test
    public void testAllocateAllForward() {
        OptionalInt first = target.allocateNextReceiver(MatcherContext.NO_RECEIVER_ALLOCATED);
        OptionalInt second = target.allocateNextReceiver(MatcherContext.NO_RECEIVER_ALLOCATED);
        OptionalInt third = target.allocateNextReceiver(MatcherContext.NO_RECEIVER_ALLOCATED);

        assertAll(
            () -> assertEquals(0, first.orElseThrow()),
            () -> assertEquals(1, second.orElseThrow()),
            () -> assertEquals(2, third.orElseThrow()),
            () -> assertTrue(target.getRemainingReceivers().isEmpty())
        );
    }

    @Test
    public void testAllocateAllReverse() {
        target.allocateNextReceiver(MatcherContext.NO_RECEIVER_ALLOCATED);
        target.allocateNextReceiver(0);

        OptionalInt third = target.allocateNextReceiver(1);

        target.allocateNextReceiver(MatcherContext.NO_RECEIVER_ALLOCATED);

        OptionalInt second = target.allocateNextReceiver(0);

        OptionalInt first = target.allocateNextReceiver(MatcherContext.NO_RECEIVER_ALLOCATED);

        assertAll(
            () -> assertEquals(0, first.orElseThrow()),
            () -> assertEquals(1, second.orElseThrow()),
            () -> assertEquals(2, third.orElseThrow()),
            () -> assertTrue(target.getRemainingReceivers().isEmpty())
        );
    }
}