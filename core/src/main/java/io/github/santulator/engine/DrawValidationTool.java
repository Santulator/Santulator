/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.engine;

import io.github.santulator.core.SantaException;
import io.github.santulator.model.DrawRequirements;
import io.github.santulator.model.DrawSelection;
import io.github.santulator.model.GiverAssignment;
import io.github.santulator.model.Person;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public final class DrawValidationTool {
    private DrawValidationTool() {
        // Prevent instantiation - all methods are static
    }

    public static void validate(final DrawRequirements requirements, final DrawSelection selection) {
        validateGiverAssignments(requirements, selection);
        validatePresentsForAll(requirements, selection);
        validateGiverSetSize(selection);
    }

    private static void validateGiverAssignments(final DrawRequirements requirements, final DrawSelection selection) {
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

    private static void validatePresentsForAll(final DrawRequirements requirements, final DrawSelection selection) {
        Set<Person> recipients = selection.getGivers().stream()
            .map(GiverAssignment::getTo)
            .collect(toSet());
        Set<Person> remaining = new LinkedHashSet<>(requirements.getParticipants());

        remaining.removeAll(recipients);

        if (!remaining.isEmpty()) {
            throw new SantaException(String.format("No present for %s", remaining.iterator().next()));
        }
    }

    private static void validateGiverSetSize(final DrawSelection selection) {
        List<Person> givers = selection.getGivers().stream()
            .map(GiverAssignment::getFrom)
            .collect(toList());
        Set<Person> giverSet = new HashSet<>(givers);

        if (givers.size() != giverSet.size()) {
            throw new SantaException("The number of present givers is incorrect");
        }
    }
}
