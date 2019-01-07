package io.github.santulator.session;

import io.github.santulator.model.ParticipantRole;
import io.github.santulator.test.core.AbstractBeanTest;

public class ParticipantStateTest extends AbstractBeanTest<ParticipantState> {
    @Override
    protected ParticipantState buildPrimary() {
        return new ParticipantState("Albert", ParticipantRole.BOTH);
    }

    @Override
    protected ParticipantState buildSecondary() {
        return new ParticipantState("Beryl", ParticipantRole.BOTH);
    }
}