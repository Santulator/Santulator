package io.github.santulator.model;

import io.github.santulator.test.AbstractBeanTest;

import static io.github.santulator.model.TestDataBuilder.EDITH;
import static io.github.santulator.model.TestDataBuilder.FRED;

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