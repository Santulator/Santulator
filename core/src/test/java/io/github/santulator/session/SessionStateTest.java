package io.github.santulator.session;

import io.github.santulator.test.AbstractBeanTest;

public class SessionStateTest extends AbstractBeanTest<SessionState> {
    @Override
    protected SessionState buildPrimary() {
        return TestSessionStateTool.buildFullState("Draw 1");
    }

    @Override
    protected SessionState buildSecondary() {
        return TestSessionStateTool.buildFullState("Draw 2");
    }
}
