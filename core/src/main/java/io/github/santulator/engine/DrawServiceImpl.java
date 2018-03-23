/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.engine;

import io.github.santulator.core.SantaException;
import io.github.santulator.matcher.ConsIterable;
import io.github.santulator.matcher.MatcherPair;
import io.github.santulator.matcher.MatchingEngine;
import io.github.santulator.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.inject.Singleton;

import static java.util.stream.Collectors.toList;

@Singleton
public class DrawServiceImpl implements DrawService {
    @Override
    public DrawSelection draw(final DrawRequirements requirements) {
        List<Person> givers = participants(requirements, ParticipantRole::isGiver);
        List<Person> receivers = shuffle(participants(requirements, ParticipantRole::isReceiver));
        Set<MatcherPair<Person>> restrictions = restrictions(requirements);
        MatchingEngine engine = new MatchingEngine();
        ConsIterable<MatcherPair<Person>> match = engine.findMatch(givers, receivers, restrictions);
        DrawSelection selection = selection(match);

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

    private Set<MatcherPair<Person>> restrictions(final DrawRequirements requirements) {
        return requirements.getRestrictions().stream()
            .map(r -> new MatcherPair<>(r.getFromPerson(), r.getToPerson()))
            .collect(Collectors.toSet());
    }

    private DrawSelection selection(final ConsIterable<MatcherPair<Person>> pairs) {
        if (pairs == null) {
            throw new SantaException("Unable to find match");
        } else {
            List<GiverAssignment> givers = pairs.stream()
                .map(p -> new GiverAssignment(p.getLeft(), p.getRight()))
                .collect(toList());

            return new DrawSelection(givers);
        }
    }
}
