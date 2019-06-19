/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.validator;

import io.github.santulator.gui.i18n.I18nKey;
import io.github.santulator.gui.i18n.I18nManager;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;

public final class ValidationError {
    private final I18nKey key;

    private final Object[] arguments;

    public ValidationError(final I18nKey key, final Object... arguments) {
        this.key = key;
        this.arguments = Arrays.copyOf(arguments, arguments.length);
    }

    public String getMessage(final I18nManager i18nManager) {
        return i18nManager.text(key, arguments);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ValidationError that = (ValidationError) o;

        return new EqualsBuilder()
            .append(key, that.key)
            .append(arguments, that.arguments)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(key)
            .append(arguments)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("key", key)
            .append("arguments", arguments)
            .toString();
    }
}
