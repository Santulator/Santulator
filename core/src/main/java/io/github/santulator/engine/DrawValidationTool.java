/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.engine;

import io.github.santulator.core.SantaException;
import io.github.santulator.model.DrawRequirements;
import io.github.santulator.model.DrawSelection;
import io.github.santulator.model.GiverAssignment;
import io.github.santulator.model.Person;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.*;

public final class DrawValidationTool {
    private DrawValidationTool() {
        // Prevent instantiation - all methods are static
    }

    public static void validate(final DrawRequirements requirements, final DrawSelection selection) {
        validateRestrictions(requirements, selection);
        validateRecipients(requirements, selection);
        validateGivers(requirements, selection);
    }

    private static void validateRestrictions(final DrawRequirements requirements, final DrawSelection selection) {
        Set<GiverAssignment> prohibited = requirements.getRestrictions().stream()
            .map(r -> new GiverAssignment(r.getFromPerson(), r.getToPerson()))
            .collect(toSet());
        Optional<GiverAssignment> badAssignment = selection.getGivers().stream()
            .filter(prohibited::contains)
            .findFirst();

        badAssignment.ifPresent(giverAssignment -> {
            throw new SantaException(String.format("Bad present assignment %s", giverAssignment));
        });
    }

    private static void validateRecipients(final DrawRequirements requirements, final DrawSelection selection) {
        validateSide(requirements, selection, GiverAssignment::getTo, p1 -> p1.getRole().isReceiver(), "receiver");
    }

    private static void validateGivers(final DrawRequirements requirements, final DrawSelection selection) {
        validateSide(requirements, selection, GiverAssignment::getFrom, p1 -> p1.getRole().isGiver(), "giver");
    }

    private static void validateSide(final DrawRequirements requirements, final DrawSelection selection, final Function<GiverAssignment, Person> extractSide,
                                     final Predicate<Person> roleFilter, final String sideName) {
        Set<Person> expected = requirements.getParticipants().stream()
            .filter(roleFilter)
            .collect(toSet());
        Map<Person, Long> appearances = selection.getGivers().stream()
            .collect(groupingBy(extractSide, counting()));

        appearances.keySet().stream()
            .filter(p -> !expected.contains(p))
            .findFirst()
            .ifPresent(p -> {
                throw new SantaException(String.format("Invalid %s '%s'", sideName, p.getName()));
            });
        appearances.entrySet().stream()
            .filter(e -> e.getValue() > 1)
            .map(Map.Entry::getKey)
            .findFirst()
            .ifPresent(p -> {
                throw new SantaException(String.format("Duplicate %s '%s", sideName, p.getName()));
            });
        requirements.getParticipants().stream()
            .filter(roleFilter)
            .filter(key -> !appearances.containsKey(key))
            .findFirst()
            .ifPresent(p -> {
                throw new SantaException(String.format("Missing %s '%s'", sideName, p.getName()));
            });
    }
}
