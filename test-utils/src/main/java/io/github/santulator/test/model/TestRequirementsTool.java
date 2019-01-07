/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.test.model;

import io.github.santulator.core.SantaException;
import io.github.santulator.model.DrawRequirements;
import io.github.santulator.model.Person;

import static io.github.santulator.model.ParticipantRole.*;

public final class TestRequirementsTool {
    public static final DrawRequirements REQUIREMENTS = new RequirementsBuilder()
        .person("Albert", GIVER)
        .person("Beryl",  BOTH)
        .person("Carla",  BOTH)
        .person("David",  BOTH)
        .person("Edith",  BOTH)
        .person("Fred",   BOTH)
        .person("Gina",   BOTH)
        .person("Harry",  BOTH)
        .person("Iris",   BOTH)
        .person("John",   BOTH)
        .person("Kate",   RECEIVER)
        .mutualRestriction("Albert", "Beryl")
        .mutualRestriction("Carla",  "David")
        .mutualRestriction("Edith",  "Fred")
        .mutualRestriction("Gina",   "Harry")
        .mutualRestriction("Iris",   "John")
        .build();

    public static final Person EDITH = person(REQUIREMENTS, "Edith");

    public static final Person FRED = person(REQUIREMENTS, "Fred");

    private TestRequirementsTool() {
        // Prevent instantiation - all members are static
    }

    public static Person person(final DrawRequirements requirements, final String name) {
        return requirements.getParticipants().stream()
            .filter(p -> p.getName().equals(name))
            .findFirst()
            .orElseThrow(() -> new SantaException(String.format("Person %s doesn't exist", name)));
    }
}
