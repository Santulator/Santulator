/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public final class Restriction {
    private final Person fromPerson;

    private final Person toPerson;

    public Restriction(final Person fromPerson, final Person toPerson) {
        this.fromPerson = fromPerson;
        this.toPerson = toPerson;
    }

    public Person getFromPerson() {
        return fromPerson;
    }

    public Person getToPerson() {
        return toPerson;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Restriction that = (Restriction) o;

        return new EqualsBuilder()
                .append(fromPerson, that.fromPerson)
                .append(toPerson, that.toPerson)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(fromPerson)
                .append(toPerson)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("from", fromPerson)
            .append("to", toPerson)
            .toString();
    }
}
