package io.github.santulator.gui.validator;

import io.github.santulator.gui.model.SessionModel;

import java.util.Optional;

public interface ValidationService {
    Optional<ValidationError> validate(SessionModel model);
}
