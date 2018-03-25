package io.github.santulator.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RequirementsBuilder {
    private final Map<String, Person> people = new HashMap<>();

    private final Set<Restriction> restrictions = new HashSet<>();

    public RequirementsBuilder person(final String name, final ParticipantRole role) {
        people.put(name, new Person(name, role));

        return this;
    }

    public RequirementsBuilder mutualRestriction(final String first, final String second) {
        restrictions(first, second);
        restrictions(second, first);

        return this;
    }

    public RequirementsBuilder restrictions(final String fromName, final String... toNames) {
        for (String toName : toNames) {
            restrictions.add(new Restriction(people.get(fromName), people.get(toName)));
        }

        return this;
    }

    public DrawRequirements build() {
        return new DrawRequirements(people.values(), restrictions);
    }
}
