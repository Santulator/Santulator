package io.github.santulator.model;

import io.github.santulator.test.AbstractBeanTest;

import static io.github.santulator.model.TestDataBuilder.*;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class DrawRequirementsTest extends AbstractBeanTest<DrawRequirements> {
    private static final Restriction RESTRICTION = new Restriction(EDITH, FRED);

    @Override
    protected DrawRequirements buildPrimary() {
        return new DrawRequirements(PEOPLE, emptyList());
    }

    @Override
    protected DrawRequirements buildSecondary() {
        return new DrawRequirements(PEOPLE, singletonList(RESTRICTION));
    }
}