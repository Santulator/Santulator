package io.github.santulator.session;

import io.github.santulator.core.SantaException;
import io.github.santulator.model.DrawRequirements;
import io.github.santulator.model.Person;
import io.github.santulator.model.Restriction;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class SessionStateTranslatorImpl implements SessionStateTranslator {
    @Override
    public DrawRequirements toRequirements(final SessionState state) {
        List<ParticipantState> inputParticipants = state.getParticipants();
        Map<String, Person> people = buildMap(inputParticipants);
        List<Restriction> restrictions = inputParticipants.stream()
            .flatMap(p -> restrictions(people, p))
            .collect(toList());

        return new DrawRequirements(people.values(), restrictions);
    }

    private Stream<Restriction> restrictions(final Map<String, Person> people, final ParticipantState participant) {
        return participant.getExclusions().stream()
            .map(n -> new Restriction(person(people, participant.getName()), person(people, n)));
    }

    private Person person(final Map<String, Person> people, final String name) {
        Person person = people.get(name);

        if (person == null) {
            throw new SantaException(String.format("Unknown person '%s'", name));
        } else {
            return person;
        }
    }

    private Map<String, Person> buildMap(final List<ParticipantState> participants) {
        Map<String, Person> people = new HashMap<>();

        for (ParticipantState participant : participants) {
            String name = participant.getName();
            if (StringUtils.isBlank(name)) {
                throw new SantaException("Empty participant name");
            } else if (people.containsKey(name)) {
                throw new SantaException(String.format("Duplicate participant '%s'", name));
            } else {
                people.put(name, new Person(name, participant.getRole()));
            }
        }

        return people;
    }
}
