/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.services;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class ProgressPoint {
    private final int percentage;

    private final int target;

    public ProgressPoint(final int percentage, final int target) {
        this.percentage = percentage;
        this.target = target;
    }

    public int getPercentage() {
        return percentage;
    }

    public int getTarget() {
        return target;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProgressPoint that = (ProgressPoint) o;

        return new EqualsBuilder()
            .append(percentage, that.percentage)
            .append(target, that.target)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(percentage)
            .append(target)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("percentage", percentage)
            .append("target", target)
            .toString();
    }
}
