package io.github.santulator.model;

import io.github.santulator.test.AbstractBeanTest;

import static io.github.santulator.model.TestRequirementsTool.EDITH;
import static io.github.santulator.model.TestRequirementsTool.FRED;

public class RestrictionTest extends AbstractBeanTest<Restriction> {
    @Override
    protected Restriction buildPrimary() {
        return new Restriction(EDITH, FRED);
    }

    @Override
    protected Restriction buildSecondary() {
        return new Restriction(FRED, EDITH);
    }
}