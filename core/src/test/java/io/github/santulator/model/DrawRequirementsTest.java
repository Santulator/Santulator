package io.github.santulator.model;

import io.github.santulator.test.core.AbstractBeanTest;
import io.github.santulator.test.model.RequirementsBuilder;

public class DrawRequirementsTest extends AbstractBeanTest<DrawRequirements> {
    @Override
    protected DrawRequirements buildPrimary() {
        return new RequirementsBuilder()
            .build();
    }

    @Override
    protected DrawRequirements buildSecondary() {
        return new RequirementsBuilder()
            .person("Edith", ParticipantRole.BOTH)
            .person("Fred", ParticipantRole.BOTH)
            .build();
    }
}