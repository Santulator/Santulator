/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.engine;

import io.github.santulator.model.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DrawServiceTest {
    private final DrawService target = new DrawServiceImpl();

    @Test
    public void testEmpty() {
        DrawRequirements requirements = new DrawRequirements(emptyList(), emptyList());

        validateSelection(requirements);
    }

    @Test
    public void testRealisticSelection() {
        DrawRequirements requirements = TestDataBuilder.buildDrawRequirements();

        validateSelection(requirements);
    }

    private void validateSelection(final DrawRequirements requirements) {
        DrawSelection selection = target.draw(requirements);
        Set<Person> expectedGivers = participants(requirements, ParticipantRole::isGiver);
        Set<Person> expectedReceivers = participants(requirements, ParticipantRole::isReceiver);
        Set<Person> actualGivers = givers(selection);
        Set<Person> actualReceivers = receivers(selection);

        assertAll(
            () -> assertEquals(expectedGivers, actualGivers, "Givers"),
            () -> assertEquals(expectedReceivers, actualReceivers, "Receivers")
        );
    }

    private Set<Person> participants(final DrawRequirements requirements, final Predicate<ParticipantRole> roleFilter) {
        HashSet<Person> people = new HashSet<>(requirements.getParticipants());

        people.removeIf(p -> !roleFilter.test(p.getRole()));

        return people;
    }

    private Set<Person> givers(final DrawSelection selection) {
        return selection.getGivers().stream()
            .map(GiverAssignment::getFrom)
            .collect(Collectors.toSet());
    }

    private Set<Person> receivers(final DrawSelection selection) {
        return selection.getGivers().stream()
            .map(GiverAssignment::getTo)
            .collect(Collectors.toSet());
    }
}
