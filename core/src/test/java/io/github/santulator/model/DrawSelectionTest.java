package io.github.santulator.model;

import io.github.santulator.test.AbstractBeanTest;

import static io.github.santulator.model.TestRequirementsTool.EDITH;
import static io.github.santulator.model.TestRequirementsTool.FRED;
import static java.util.Collections.singletonList;

public class DrawSelectionTest extends AbstractBeanTest<DrawSelection> {
    private static final GiverAssignment GIVER_ASSIGNMENT_1 = new GiverAssignment(EDITH, FRED);

    private static final GiverAssignment GIVER_ASSIGNMENT_2 = new GiverAssignment(FRED, EDITH);

    @Override
    protected DrawSelection buildPrimary() {
        return new DrawSelection(singletonList(GIVER_ASSIGNMENT_1));
    }

    @Override
    protected DrawSelection buildSecondary() {
        return new DrawSelection(singletonList(GIVER_ASSIGNMENT_2));
    }
}