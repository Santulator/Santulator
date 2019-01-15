package io.github.santulator.model;

import io.github.santulator.test.core.AbstractBeanTest;

import java.util.List;

import static io.github.santulator.model.ModelTestConstants.EDITH;
import static io.github.santulator.model.ModelTestConstants.FRED;

public class DrawRequirementsTest extends AbstractBeanTest<DrawRequirements> {
    @Override
    protected DrawRequirements buildPrimary() {
        return new DrawRequirements(List.of(), List.of());
    }

    @Override
    protected DrawRequirements buildSecondary() {
        return new DrawRequirements(List.of(EDITH, FRED), List.of());
    }
}