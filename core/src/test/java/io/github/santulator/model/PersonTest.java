/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

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