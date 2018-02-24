package io.github.santulator.model;

import io.github.santulator.test.AbstractBeanTest;

import static io.github.santulator.model.TestDataBuilder.EDITH;
import static io.github.santulator.model.TestDataBuilder.FRED;

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