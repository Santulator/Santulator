/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.session;

import io.github.santulator.model.SessionState;
import io.github.santulator.test.core.AbstractBeanTest;
import io.github.santulator.test.session.TestSessionStateTool;

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
