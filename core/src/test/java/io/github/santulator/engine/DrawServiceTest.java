/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.engine;

import io.github.santulator.model.DrawRequirements;
import io.github.santulator.model.DrawSelection;
import io.github.santulator.model.GiverAssignment;
import io.github.santulator.model.ParticipantRole;
import io.github.santulator.model.Person;
import io.github.santulator.model.RequirementsBuilder;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.github.santulator.model.TestRequirementsTool.REQUIREMENTS;
import static io.github.santulator.model.TestRequirementsTool.person;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DrawServiceTest {
    private final DrawService target = new DrawServiceImpl();

    @Test
    public void testEmpty() {
        DrawRequirements requirements = new RequirementsBuilder()
            .build();

        validateDeterministic(requirements);
    }

    @Test
    public void testSingleGiver() {
        DrawRequirements requirements = new RequirementsBuilder()
            .person("Giver", ParticipantRole.GIVER)
            .person("Receiver", ParticipantRole.RECEIVER)
            .build();

        validateDeterministic(requirements, giver(requirements, "Giver", "Receiver"));
    }

    @Test
    public void testSingleGiftExchange() {
        DrawRequirements requirements = new RequirementsBuilder()
            .person("Person 1", ParticipantRole.BOTH)
            .person("Person 2", ParticipantRole.BOTH)
            .build();

        validateDeterministic(requirements, giver(requirements, "Person 1", "Person 2"), giver(requirements, "Person 2", "Person 1"));
    }

    @Test
    public void testOnlyOneSolution() {
        DrawRequirements requirements = new RequirementsBuilder()
            .person("A", ParticipantRole.BOTH)
            .person("B", ParticipantRole.BOTH)
            .person("C", ParticipantRole.BOTH)
            .person("D", ParticipantRole.BOTH)
            .restrictions("A", "C", "D")
            .restrictions("B", "A", "D")
            .restrictions("C", "A", "B")
            .build();

        validateDeterministic(requirements, giver(requirements, "A", "B"), giver(requirements, "B", "C"), giver(requirements, "C", "D"), giver(requirements, "D", "A"));
    }

    private GiverAssignment giver(final DrawRequirements requirements, final String from, final String to) {
        return new GiverAssignment(person(requirements, from), person(requirements, to));
    }

    private void validateDeterministic(final DrawRequirements requirements, final GiverAssignment... assignments) {
        DrawSelection expected = new DrawSelection(asList(assignments));
        DrawSelection actual = target.draw(requirements);

        assertEquals(expected, actual, "Draw Selection");
    }

    @Test
    public void testRealisticSelection() {
        validateNondeterministic(REQUIREMENTS);
    }

    private void validateNondeterministic(final DrawRequirements requirements) {
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
