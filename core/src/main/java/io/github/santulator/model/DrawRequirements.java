/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DrawRequirements {
    private final List<Person> participants;

    private final List<Restriction> restrictions;

    public DrawRequirements(final List<Person> participants, final List<Restriction> restrictions) {
        this.participants = new ArrayList<>(participants);
        this.restrictions = new ArrayList<>(restrictions);
    }

    public List<Person> getParticipants() {
        return Collections.unmodifiableList(participants);
    }

    public List<Restriction> getRestrictions() {
        return Collections.unmodifiableList(restrictions);
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
