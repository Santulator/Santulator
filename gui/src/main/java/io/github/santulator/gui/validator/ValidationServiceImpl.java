package io.github.santulator.gui.validator;

import io.github.santulator.gui.i18n.I18nGuiKey;
import io.github.santulator.gui.model.ParticipantModel;
import io.github.santulator.gui.model.SessionModel;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;
import javax.inject.Singleton;

@Singleton
public class ValidationServiceImpl implements ValidationService {
    private static final List<Function<SessionModel, ValidationError>> VALIDATORS = Arrays.asList(
        ValidationServiceImpl::checkInitialState,
        ValidationServiceImpl::checkParticipantCount,
        ValidationServiceImpl::checkEmptyName
    );

    @Override
    public Optional<ValidationError> validate(final SessionModel model) {
        return VALIDATORS.stream()
            .map(v -> v.apply(model))
            .filter(Objects::nonNull)
            .findFirst();
    }

    private static ValidationError checkInitialState(final SessionModel model) {
        List<ParticipantModel> participants = model.getParticipants();

        if (participants.size() == 2 && isEmptyName(participants, 0) && participants.get(1).isPlaceholder()) {
            return new ValidationError(I18nGuiKey.VALIDATION_COUNT, 0);
        } else {
            return null;
        }
    }

    private static ValidationError checkParticipantCount(final SessionModel model) {
        long size = model.getParticipants().stream()
            .filter(p -> !p.isPlaceholder())
            .count();

        if (size <= 1) {
            return new ValidationError(I18nGuiKey.VALIDATION_COUNT, size);
        } else {
            return null;
        }
    }

    private static ValidationError checkEmptyName(final SessionModel model) {
        List<ParticipantModel> participants = model.getParticipants();

        return IntStream.range(0, participants.size())
            .filter(i -> isEmptyName(participants, i))
            .mapToObj(i -> new ValidationError(I18nGuiKey.VALIDATION_NAME, i + 1))
            .findFirst()
            .orElse(null);
    }

    private static boolean isEmptyName(final List<ParticipantModel> participants, final int index) {
        ParticipantModel participant = participants.get(index);

        return !participant.isPlaceholder() && participant.getName().isEmpty();
    }
}
