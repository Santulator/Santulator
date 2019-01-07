/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.settings;

import io.github.santulator.test.core.AbstractBeanTest;

public class WindowSettingsTest extends AbstractBeanTest<WindowSettings> {
    @Override
    protected WindowSettings buildPrimary() {
        return new WindowSettings();
    }

    @Override
    protected WindowSettings buildSecondary() {
        WindowSettings settings = buildPrimary();

        settings.setX(settings.getX() + 1);

        return settings;
    }
}
