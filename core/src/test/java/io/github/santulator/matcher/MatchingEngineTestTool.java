/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import io.github.santulator.model.GiverAssignment;
import io.github.santulator.model.Person;

import java.util.*;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchingEngineTestTool {
    private List<Person> participants;

    private Set<GiverAssignment> restrictions;

    private final Set<Set<GiverAssignment>> expectations = new HashSet<>();

    public void setParticipants(final Person... participants) {
        this.participants = List.of(participants);
    }

    public void setRestrictions(final PairSetTool restrictions) {
        this.restrictions = restrictions.toSet();
    }

    public void setEmptyExpectations() {
        expectations.add(null);
    }

    public void addExpectation(final PairSetTool expectation) {
        expectations.add(expectation.toSet());
    }

    public void performValidation() {
        List<List<Person>> permutations = generatePermutations();
        Set<Set<GiverAssignment>> matches = permutations.stream()
            .map(list -> matchSet(participants, list, restrictions))
            .collect(toSet());

        assertEquals(expectations, matches, "Match set");
    }

    private Set<GiverAssignment> matchSet(final List<Person> givers, final List<Person> receivers, final Set<GiverAssignment> restrictions) {
        MatchingEngine engine = new MatchingEngine(givers, receivers, restrictions);

        return engine.findMatch()
            .map(Set::copyOf)
            .orElse(null);
    }

    private List<List<Person>> generatePermutations() {
        List<List<Person>> result = new ArrayList<>();

        addPermutations(result, new ArrayList<>(), new HashSet<>(participants));

        return result;
    }

    private void addPermutations(final List<List<Person>> accumulator, final List<Person> current, final Set<Person> remaining) {
        if (remaining.isEmpty()) {
            accumulator.add(current);
        } else {
            for (Person person : remaining) {
                List<Person> nextCurrent = new ArrayList<>(current);
                Set<Person> nextRemaining = new HashSet<>(remaining);

                nextCurrent.add(person);
                nextRemaining.remove(person);
                addPermutations(accumulator, nextCurrent, nextRemaining);
            }
        }
    }
}
