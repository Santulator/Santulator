/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.validator;

import io.github.santulator.gui.model.SessionModel;

import java.util.Optional;

public interface ValidationService {
    Optional<ValidationError> validate(SessionModel model);
}
