/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.engine;

import io.github.santulator.core.SantaException;
import io.github.santulator.matcher.MatchingEngine;
import io.github.santulator.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import javax.inject.Singleton;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Singleton
public class DrawServiceImpl implements DrawService {
    @Override
    public DrawSelection draw(final DrawRequirements requirements) {
        List<Person> givers = participants(requirements, ParticipantRole::isGiver);
        List<Person> receivers = shuffle(participants(requirements, ParticipantRole::isReceiver));
        Set<GiverAssignment> restrictions = restrictions(requirements);
        MatchingEngine engine = new MatchingEngine();
        DrawSelection selection = engine.findMatch(givers, receivers, restrictions)
            .map(DrawSelection::new)
            .orElseThrow(() -> new SantaException("Unable to find match"));

        DrawValidationTool.validate(requirements, selection);

        return selection;
    }

    private List<Person> participants(final DrawRequirements requirements, final Predicate<ParticipantRole> roleFilter) {
        return requirements.getParticipants().stream()
            .filter(p -> roleFilter.test(p.getRole()))
            .collect(toList());
    }

    private <T> List<T> shuffle(final List<T> original) {
        List<T> result = new ArrayList<>(original);

        Collections.shuffle(result);

        return result;
    }

    private Set<GiverAssignment> restrictions(final DrawRequirements requirements) {
        return requirements.getRestrictions().stream()
            .map(r -> new GiverAssignment(r.getFromPerson(), r.getToPerson()))
            .collect(toSet());
    }

}
