package io.github.santulator.model;

import io.github.santulator.test.core.AbstractBeanTest;

import static io.github.santulator.model.ModelTestConstants.EDITH;
import static io.github.santulator.model.ModelTestConstants.FRED;

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