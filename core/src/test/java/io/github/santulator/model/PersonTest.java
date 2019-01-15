package io.github.santulator.model;

import io.github.santulator.test.core.AbstractBeanTest;

public class PersonTest extends AbstractBeanTest<Person> {

    @Override
    protected Person buildPrimary() {
        return ModelTestConstants.EDITH;
    }

    @Override
    protected Person buildSecondary() {
        return ModelTestConstants.FRED;
    }
}