/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import io.github.santulator.model.GiverAssignment;
import io.github.santulator.model.Person;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MatcherFrameTest {
    private static final Person PERSON_1 = new Person("PERSON_1");

    private static final Person PERSON_2 = new Person("PERSON_2");

    private static final Person PERSON_3 = new Person("PERSON_3");

    private static final List<Person> RECEIVERS_NONE = List.of();

    private static final List<Person> RECEIVERS_ALL = List.of(PERSON_1, PERSON_2, PERSON_3);

    private static final Set<GiverAssignment> RESTRICTIONS_EMPTY = Set.of();

    private static final GiverAssignment ASSIGNMENT_1_2 = new GiverAssignment(PERSON_1, PERSON_2);

    private static final GiverAssignment ASSIGNMENT_1_3 = new GiverAssignment(PERSON_1, PERSON_3);

    private static final Set<GiverAssignment> RESTRICTIONS_SINGLE = Set.of(ASSIGNMENT_1_2);

    @Test
    public void testEmpty() {
        MatcherContext context = new MatcherContext(RECEIVERS_NONE, RESTRICTIONS_EMPTY);
        MatcherFrame target = new MatcherFrame(context, PERSON_1);
        boolean isMatch = target.selectMatch();

        assertAll(
            () -> assertFalse(isMatch),
            () -> assertEquals(Set.of(), context.getRemainingReceivers()),
            () -> assertThrows(IllegalStateException.class, target::getPair)
        );
    }

    @Test
    public void testBeforeFirstSelectMatch() {
        MatcherContext context = new MatcherContext(RECEIVERS_ALL, RESTRICTIONS_EMPTY);
        MatcherFrame target = new MatcherFrame(context, PERSON_1);

        assertThrows(IllegalStateException.class, target::getPair);
    }

    @Test
    public void testFirstSelectMatchWithoutRestrictions() {
        MatcherContext context = new MatcherContext(RECEIVERS_ALL, RESTRICTIONS_EMPTY);
        MatcherFrame target = new MatcherFrame(context, PERSON_1);
        boolean isMatch = target.selectMatch();

        assertAll(
            () -> assertTrue(isMatch),
            () -> assertEquals(Set.of(PERSON_1, PERSON_3), context.getRemainingReceivers()),
            () -> assertEquals(ASSIGNMENT_1_2, target.getPair())
        );
    }

    @Test
    public void testFirstSelectMatchWithRestriction() {
        MatcherContext context = new MatcherContext(RECEIVERS_ALL, RESTRICTIONS_SINGLE);
        MatcherFrame target = new MatcherFrame(context, PERSON_1);
        boolean isMatch = target.selectMatch();

        assertAll(
            () -> assertTrue(isMatch),
            () -> assertEquals(Set.of(PERSON_1, PERSON_2), context.getRemainingReceivers()),
            () -> assertEquals(ASSIGNMENT_1_3, target.getPair())
        );
    }

    @Test
    public void testSecondSelectMatch() {
        MatcherContext context = new MatcherContext(RECEIVERS_ALL, RESTRICTIONS_EMPTY);
        MatcherFrame target = new MatcherFrame(context, PERSON_1);

        target.selectMatch();

        boolean isMatch = target.selectMatch();

        assertAll(
            () -> assertTrue(isMatch),
            () -> assertEquals(Set.of(PERSON_1, PERSON_2), context.getRemainingReceivers()),
            () -> assertEquals(ASSIGNMENT_1_3, target.getPair())
        );
    }

    @Test
    public void testThirdSelectMatch() {
        MatcherContext context = new MatcherContext(RECEIVERS_ALL, RESTRICTIONS_EMPTY);
        MatcherFrame target = new MatcherFrame(context, PERSON_1);

        target.selectMatch();
        target.selectMatch();

        boolean isMatch = target.selectMatch();

        assertAll(
            () -> assertFalse(isMatch),
            () -> assertEquals(Set.of(PERSON_1, PERSON_2, PERSON_3), context.getRemainingReceivers()),
            () -> assertThrows(IllegalStateException.class, target::getPair)
        );
    }
}