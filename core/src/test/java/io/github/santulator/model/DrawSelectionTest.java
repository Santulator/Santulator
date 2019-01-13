package io.github.santulator.model;

import io.github.santulator.test.core.AbstractBeanTest;

import java.util.List;

import static io.github.santulator.test.model.TestRequirementsTool.EDITH;
import static io.github.santulator.test.model.TestRequirementsTool.FRED;

public class DrawSelectionTest extends AbstractBeanTest<DrawSelection> {
    private static final GiverAssignment GIVER_ASSIGNMENT_1 = new GiverAssignment(EDITH, FRED);

    private static final GiverAssignment GIVER_ASSIGNMENT_2 = new GiverAssignment(FRED, EDITH);

    @Override
    protected DrawSelection buildPrimary() {
        return new DrawSelection(List.of(GIVER_ASSIGNMENT_1));
    }

    @Override
    protected DrawSelection buildSecondary() {
        return new DrawSelection(List.of(GIVER_ASSIGNMENT_2));
    }
}