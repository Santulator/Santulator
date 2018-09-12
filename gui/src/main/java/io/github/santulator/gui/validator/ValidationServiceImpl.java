package io.github.santulator.gui.validator;

import io.github.santulator.gui.i18n.I18nGuiKey;
import io.github.santulator.gui.model.SessionModel;

import java.util.Optional;
import javax.inject.Singleton;

@Singleton
public class ValidationServiceImpl implements ValidationService {
    @Override
    public Optional<ValidationError> validate(final SessionModel model) {
        long size = model.getParticipants().stream()
            .filter(p -> !(p.isPlaceholder() || p.getName().isEmpty()))
            .count();

        if (size <= 1) {
            return Optional.of(new ValidationError(I18nGuiKey.VALIDATION_COUNT, size));
        } else {
            return Optional.empty();
        }
    }
}
