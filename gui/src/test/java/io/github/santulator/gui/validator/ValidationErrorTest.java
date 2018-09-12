package io.github.santulator.gui.validator;

import io.github.santulator.test.AbstractBeanTest;

import static io.github.santulator.gui.i18n.I18nGuiKey.VALIDATION_COUNT;

public class ValidationErrorTest extends AbstractBeanTest<ValidationError> {
    @Override
    protected ValidationError buildPrimary() {
        return new ValidationError(VALIDATION_COUNT, 0);
    }

    @Override
    protected ValidationError buildSecondary() {
        return new ValidationError(VALIDATION_COUNT, 1);
    }
}