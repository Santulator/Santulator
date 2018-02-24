/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.engine;

import io.github.santulator.model.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
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
        Set<Person> people = people(requirements);
        Set<Person> givers = givers(selection);
        Set<Person> receivers = receivers(selection);

        assertAll(
            () -> assertEquals(people, givers, "Givers"),
            () -> assertEquals(people, receivers, "Receivers")
        );
    }

    private Set<Person> people(final DrawRequirements requirements) {
        return new HashSet<>(requirements.getParticipants());
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
