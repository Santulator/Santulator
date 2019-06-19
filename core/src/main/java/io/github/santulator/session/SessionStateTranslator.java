/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.session;

import io.github.santulator.model.DrawRequirements;
import io.github.santulator.model.SessionState;

public interface SessionStateTranslator {
    DrawRequirements toRequirements(SessionState state);
}
