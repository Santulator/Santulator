/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import io.github.santulator.model.GiverAssignment;
import io.github.santulator.model.Person;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
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
        Set<Person> remainingReceivers = Set.of();
        MatcherFrame target = new MatcherFrame(PERSON_1, RECEIVERS_NONE);
        boolean isMatch = target.selectMatch(RESTRICTIONS_EMPTY, remainingReceivers);

        assertAll(
            () -> assertFalse(isMatch),
            () -> assertEquals(Set.of(), remainingReceivers),
            () -> assertThrows(IllegalStateException.class, target::getPair)
        );
    }

    @Test
    public void testBeforeFirstSelectMatch() {
        MatcherFrame target = new MatcherFrame(PERSON_1, RECEIVERS_ALL);

        assertThrows(IllegalStateException.class, target::getPair);
    }

    @Test
    public void testFirstSelectMatchWithoutRestrictions() {
        Set<Person> remainingReceivers = new HashSet<>(RECEIVERS_ALL);
        MatcherFrame target = new MatcherFrame(PERSON_1, RECEIVERS_ALL);
        boolean isMatch = target.selectMatch(RESTRICTIONS_EMPTY, remainingReceivers);

        assertAll(
            () -> assertTrue(isMatch),
            () -> assertEquals(Set.of(PERSON_1, PERSON_3), remainingReceivers),
            () -> assertEquals(ASSIGNMENT_1_2, target.getPair())
        );
    }

    @Test
    public void testFirstSelectMatchWithRestriction() {
        Set<Person> remainingReceivers = new HashSet<>(RECEIVERS_ALL);
        MatcherFrame target = new MatcherFrame(PERSON_1, RECEIVERS_ALL);
        boolean isMatch = target.selectMatch(RESTRICTIONS_SINGLE, remainingReceivers);

        assertAll(
            () -> assertTrue(isMatch),
            () -> assertEquals(Set.of(PERSON_1, PERSON_2), remainingReceivers),
            () -> assertEquals(ASSIGNMENT_1_3, target.getPair())
        );
    }

    @Test
    public void testSecondSelectMatch() {
        Set<Person> remainingReceivers = new HashSet<>(RECEIVERS_ALL);
        MatcherFrame target = new MatcherFrame(PERSON_1, RECEIVERS_ALL);

        target.selectMatch(RESTRICTIONS_EMPTY, remainingReceivers);

        boolean isMatch = target.selectMatch(RESTRICTIONS_EMPTY, remainingReceivers);

        assertAll(
            () -> assertTrue(isMatch),
            () -> assertEquals(Set.of(PERSON_1, PERSON_2), remainingReceivers),
            () -> assertEquals(ASSIGNMENT_1_3, target.getPair())
        );
    }

    @Test
    public void testThirdSelectMatch() {
        Set<Person> remainingReceivers = new HashSet<>(RECEIVERS_ALL);
        MatcherFrame target = new MatcherFrame(PERSON_1, RECEIVERS_ALL);

        target.selectMatch(RESTRICTIONS_EMPTY, remainingReceivers);
        target.selectMatch(RESTRICTIONS_EMPTY, remainingReceivers);

        boolean isMatch = target.selectMatch(RESTRICTIONS_EMPTY, remainingReceivers);

        assertAll(
            () -> assertFalse(isMatch),
            () -> assertEquals(Set.of(PERSON_1, PERSON_2, PERSON_3), remainingReceivers),
            () -> assertThrows(IllegalStateException.class, target::getPair)
        );
    }
}