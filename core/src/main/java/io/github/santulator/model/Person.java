/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public final class Person {
    private final String name;

    private final ParticipantRole role;

    public Person(final String name) {
        this(name, ParticipantRole.BOTH);
    }

    public Person(final String name, final ParticipantRole role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public ParticipantRole getRole() {
        return role;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Person person = (Person) o;

        return new EqualsBuilder()
            .append(name, person.name)
            .append(role, person.role)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(name)
            .append(role)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append(name)
            .append(role)
            .toString();
    }
}
