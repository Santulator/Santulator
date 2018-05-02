package io.github.santulator.session;

import io.github.santulator.model.DrawRequirements;

public interface SessionStateTranslator {
    DrawRequirements toRequirements(SessionState state);
}
