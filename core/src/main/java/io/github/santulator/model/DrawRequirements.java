/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collection;
import java.util.Set;

public final class DrawRequirements {
    private final Set<Person> participants;

    private final Set<Restriction> restrictions;

    public DrawRequirements(final Collection<Person> participants, final Collection<Restriction> restrictions) {
        this.participants = Set.copyOf(participants);
        this.restrictions = Set.copyOf(restrictions);
    }

    public Set<Person> getParticipants() {
        return participants;
    }

    public Set<Restriction> getRestrictions() {
        return restrictions;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DrawRequirements that = (DrawRequirements) o;

        return new EqualsBuilder()
                .append(participants, that.participants)
                .append(restrictions, that.restrictions)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(participants)
                .append(restrictions)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append(participants)
            .append(restrictions)
            .toString();
    }
}
