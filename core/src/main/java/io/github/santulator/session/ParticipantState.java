package io.github.santulator.session;

import io.github.santulator.model.ParticipantRole;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.github.santulator.core.CoreTool.listOf;

public class ParticipantState {
    private String name;

    private ParticipantRole role;

    private List<String> exclusions;

    public ParticipantState() {
        this(null, null);
    }

    public ParticipantState(final String name, final ParticipantRole role, final String... exclusions) {
        this(name, role, listOf(exclusions));
    }

    public ParticipantState(final String name, final ParticipantRole role, final List<String> exclusions) {
        this.name = name;
        this.role = role;
        this.exclusions = new ArrayList<>(exclusions);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ParticipantRole getRole() {
        return role;
    }

    public void setRole(final ParticipantRole role) {
        this.role = role;
    }

    public List<String> getExclusions() {
        return Collections.unmodifiableList(exclusions);
    }

    public void setExclusions(final List<String> exclusions) {
        this.exclusions = new ArrayList<>(exclusions);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParticipantState that = (ParticipantState) o;

        return new EqualsBuilder()
            .append(name, that.name)
            .append(role, that.role)
            .append(exclusions, that.exclusions)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(name)
            .append(role)
            .append(exclusions)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("name", name)
            .append("role", role)
            .append("exclusions", exclusions)
            .toString();
    }
}
