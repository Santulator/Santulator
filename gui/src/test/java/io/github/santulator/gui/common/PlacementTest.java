package io.github.santulator.gui.common;

import io.github.santulator.test.AbstractBeanTest;

public class PlacementTest extends AbstractBeanTest<Placement> {
    @Override
    protected Placement buildPrimary() {
        return new Placement(1, 2, 3,4);
    }

    @Override
    protected Placement buildSecondary() {
        return new Placement(1, 2);
    }
}
