package io.github.santulator.gui.validator;

import io.github.santulator.gui.model.ParticipantModel;
import io.github.santulator.gui.model.SessionModel;
import io.github.santulator.model.ParticipantRole;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.inject.Singleton;

import static io.github.santulator.gui.i18n.I18nGuiKey.*;

@Singleton
public class ValidationServiceImpl implements ValidationService {
    private static final List<Function<List<ParticipantModel>, ValidationError>> VALIDATORS = Arrays.asList(
        ValidationServiceImpl::checkInitialState,
        ValidationServiceImpl::checkParticipantCount,
        ValidationServiceImpl::checkEmptyName,
        ValidationServiceImpl::checkDuplicateParticipant,
        ValidationServiceImpl::checkRoleCounts,
        ValidationServiceImpl::checkUnknownExclusion
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
        if (participants.size() == 2 && isEmptyName(participants, 0) && participants.get(1).isPlaceholder()) {
            return new ValidationError(VALIDATION_COUNT, 0);
        } else {
            return null;
        }
    }

    private static ValidationError checkParticipantCount(final List<ParticipantModel> participants) {
        long size = participants.stream()
            .filter(p -> !p.isPlaceholder())
            .count();

        if (size <= 1) {
            return new ValidationError(VALIDATION_COUNT, size);
        } else {
            return null;
        }
    }

    private static ValidationError checkEmptyName(final List<ParticipantModel> participants) {
        return participants.stream()
            .filter(ValidationServiceImpl::isEmptyName)
            .map(p -> new ValidationError(VALIDATION_NAME, p.getRowNumber()))
            .findFirst()
            .orElse(null);
    }

    private static boolean isEmptyName(final List<ParticipantModel> participants, final int index) {
        ParticipantModel participant = participants.get(index);

        return isEmptyName(participant);
    }

    private static boolean isEmptyName(final ParticipantModel participant) {
        return !participant.isPlaceholder() && participant.getName().isEmpty();
    }

    private static ValidationError checkDuplicateParticipant(final List<ParticipantModel> participants) {
        Map<String, List<ParticipantModel>> map = participants.stream()
            .collect(Collectors.groupingBy(ParticipantModel::getName));

        return map.entrySet().stream()
            .filter(e -> e.getValue().size() >= 2)
            .map(ValidationServiceImpl::duplicateError)
            .findFirst()
            .orElse(null);
    }

    private static ValidationError duplicateError(final Map.Entry<String, List<ParticipantModel>> e) {
        String name = e.getKey();
        List<ParticipantModel> duplicates = e.getValue();
        ParticipantModel first = duplicates.get(0);
        ParticipantModel second = duplicates.get(1);

        return new ValidationError(VALIDATION_DUPLICATE, name, first.getRowNumber(), second.getRowNumber());
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
        return participants.stream()
            .filter(p -> !p.isPlaceholder())
            .map(ParticipantModel::getRole)
            .filter(rolePredicate)
            .count();
    }

    private static ValidationError checkUnknownExclusion(final List<ParticipantModel> participants) {
        Set<String> names = participants.stream()
            .filter(p -> !p.isPlaceholder())
            .map(ParticipantModel::getName)
            .collect(Collectors.toSet());

        return participants.stream()
            .filter(p -> !p.isPlaceholder())
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
}
