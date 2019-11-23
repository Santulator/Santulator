/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.validator;

import io.github.santulator.core.CoreConstants;
import io.github.santulator.gui.model.ParticipantModel;
import io.github.santulator.gui.model.SessionModel;
import io.github.santulator.matcher.MatchingEngine;
import io.github.santulator.model.GiverAssignment;
import io.github.santulator.model.ParticipantRole;
import io.github.santulator.model.Person;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.inject.Singleton;

import static io.github.santulator.gui.i18n.I18nKey.*;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

@Singleton
public class ValidationServiceImpl implements ValidationService {
    private static final List<Function<List<ParticipantModel>, ValidationError>> VALIDATORS = List.of(
        ValidationServiceImpl::checkInitialState,
        ValidationServiceImpl::checkParticipantCount,
        ValidationServiceImpl::checkEmptyName,
        ValidationServiceImpl::checkDuplicateParticipant,
        ValidationServiceImpl::checkRoleCounts,
        ValidationServiceImpl::checkUnknownExclusion,
        ValidationServiceImpl::checkRepeatExclusion,
        ValidationServiceImpl::checkSelfExclusion,
        ValidationServiceImpl::checkDrawPossible
    );

    @Override
    public Optional<ValidationError> validate(final SessionModel model) {
        List<ParticipantModel> participants = model.getParticipants();

        return VALIDATORS.stream()
            .map(v -> v.apply(participants))
            .filter(Objects::nonNull)
            .findFirst();
    }

    private static ValidationError checkInitialState(final List<ParticipantModel> participants) {
        if (participants.size() == 2 && isEmptyName(participants.get(0))) {
            return new ValidationError(VALIDATION_COUNT, 0);
        } else {
            return null;
        }
    }

    private static ValidationError checkParticipantCount(final List<ParticipantModel> participants) {
        long size = participantsStream(participants)
            .count();

        if (size <= 1) {
            return new ValidationError(VALIDATION_COUNT, size);
        } else {
            return null;
        }
    }

    private static ValidationError checkEmptyName(final List<ParticipantModel> participants) {
        return participantsStream(participants)
            .filter(ValidationServiceImpl::isEmptyName)
            .map(p -> new ValidationError(VALIDATION_NAME, p.getRowNumber()))
            .findFirst()
            .orElse(null);
    }

    private static boolean isEmptyName(final ParticipantModel participant) {
        return participant.getName().isEmpty();
    }

    private static ValidationError checkDuplicateParticipant(final List<ParticipantModel> participants) {
        Map<String, List<ParticipantModel>> map = participants.stream()
            .collect(groupingBy(participantModel -> participantModel.getName().toLowerCase(CoreConstants.LOCALE)));

        return map.values().stream()
            .filter(v -> v.size() >= 2)
            .map(ValidationServiceImpl::duplicateError)
            .findFirst()
            .orElse(null);
    }

    private static ValidationError duplicateError(final List<ParticipantModel> duplicates) {
        ParticipantModel first = duplicates.get(0);
        ParticipantModel second = duplicates.get(1);

        return new ValidationError(VALIDATION_DUPLICATE, first.getName(), first.getRowNumber(), second.getRowNumber());
    }

    private static ValidationError checkRoleCounts(final List<ParticipantModel> participants) {
        long givers = roleCount(participants, ParticipantRole::isGiver);
        long receivers = roleCount(participants, ParticipantRole::isReceiver);

        if (givers > receivers) {
            return new ValidationError(VALIDATION_MORE_GIVERS, givers, receivers);
        } else if (receivers > givers) {
            return new ValidationError(VALIDATION_MORE_RECEIVERS, receivers, givers);
        } else {
            return null;
        }
    }

    private static long roleCount(final List<ParticipantModel> participants, final Predicate<ParticipantRole> rolePredicate) {
        return participantsStream(participants)
            .map(ParticipantModel::getRole)
            .filter(rolePredicate)
            .count();
    }

    private static ValidationError checkUnknownExclusion(final List<ParticipantModel> participants) {
        Set<String> names = participantsStream(participants)
            .map(ParticipantModel::getName)
            .collect(toSet());

        return participantsStream(participants)
            .map(p -> checkUnknownExclusion(names, p))
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }

    private static ValidationError checkUnknownExclusion(final Set<String> names, final ParticipantModel participant) {
        return participant.getExclusions().stream()
            .filter(o -> !names.contains(o))
            .findFirst()
            .map(n -> new ValidationError(VALIDATION_EXCLUSION_UNKNOWN, participant.getName(), participant.getRowNumber(), n))
            .orElse(null);
    }

    private static ValidationError checkRepeatExclusion(final List<ParticipantModel> participants) {
        return participantsStream(participants)
            .map(ValidationServiceImpl::checkRepeatExclusion)
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }

    private static ValidationError checkRepeatExclusion(final ParticipantModel participant) {
        Map<String, Long> counts = participant.getExclusions().stream()
            .collect(groupingBy(identity(), counting()));

        return counts.entrySet().stream()
            .filter(e -> e.getValue() > 1)
            .map(Map.Entry::getKey)
            .findFirst()
            .map(n -> new ValidationError(VALIDATION_EXCLUSION_REPEAT, participant.getRowNumber(), n))
            .orElse(null);
    }

    private static ValidationError checkSelfExclusion(final List<ParticipantModel> participants) {
        return participantsStream(participants)
            .filter(p -> p.getExclusions().contains(p.getName()))
            .findFirst()
            .map(p -> new ValidationError(VALIDATION_EXCLUSION_SELF, p.getRowNumber(), p.getName()))
            .orElse(null);
    }

    private static ValidationError checkDrawPossible(final List<ParticipantModel> participants) {
        Map<String, Person> people = participantsStream(participants)
            .map(p -> new Person(p.getName(), p.getRole()))
            .collect(toMap(Person::getName, identity()));
        List<Person> givers = peopleByRole(people, ParticipantRole::isGiver);
        List<Person> receivers = peopleByRole(people, ParticipantRole::isReceiver);
        Set<GiverAssignment> restrictions = participantsStream(participants)
            .flatMap(p -> restrictions(people, p))
            .collect(toSet());
        MatchingEngine engine = new MatchingEngine(givers, receivers, restrictions);
        Optional<?> match = engine.findMatch();

        if (match.isPresent()) {
            return null;
        } else {
            return new ValidationError(VALIDATION_DRAW_IMPOSSIBLE);
        }
    }

    private static List<Person> peopleByRole(final Map<String, Person> people, final Predicate<ParticipantRole> rolePredicate) {
        return people.values().stream()
            .filter(p -> rolePredicate.test(p.getRole()))
            .collect(toList());
    }

    private static Stream<GiverAssignment> restrictions(final Map<String, Person> people, final ParticipantModel participant) {
        return participant.getExclusions().stream()
            .map(e -> new GiverAssignment(people.get(participant.getName()), people.get(e)));
    }

    private static Stream<ParticipantModel> participantsStream(final List<ParticipantModel> participants) {
        return participants.stream()
            .filter(p -> !p.isPlaceholder());
    }
}
