package io.github.santulator.model;

import io.github.santulator.test.core.AbstractBeanTest;

import static io.github.santulator.model.ModelTestConstants.EDITH;
import static io.github.santulator.model.ModelTestConstants.FRED;

public class GiverAssignmentTest extends AbstractBeanTest<GiverAssignment> {
    @Override
    protected GiverAssignment buildPrimary() {
        return new GiverAssignment(EDITH, FRED);
    }

    @Override
    protected GiverAssignment buildSecondary() {
        return new GiverAssignment(FRED, EDITH);
    }
}