/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public final class GiverAssignment {
    private final Person from;

    private final Person to;

    public GiverAssignment(final Person from, final Person to) {
        this.from = from;
        this.to = to;
    }

    public Person getTo() {
        return to;
    }

    public Person getFrom() {
        return from;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GiverAssignment that = (GiverAssignment) o;

        return new EqualsBuilder()
            .append(from, that.from)
            .append(to, that.to)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(from)
            .append(to)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("from", from)
            .append("to", to)
            .toString();
    }
}
