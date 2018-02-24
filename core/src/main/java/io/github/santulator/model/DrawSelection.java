/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collections;
import java.util.List;

public final class DrawSelection {
    private static final int INITIAL_BUFFER_SIZE = 100;

    private final List<GiverAssignment> givers;

    public DrawSelection(final List<GiverAssignment> givers) {
        this.givers = givers;
    }

    public List<GiverAssignment> getGivers() {
        return Collections.unmodifiableList(givers);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DrawSelection selection = (DrawSelection) o;

        return new EqualsBuilder()
                .append(givers, selection.givers)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(givers)
                .toHashCode();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(INITIAL_BUFFER_SIZE);
        builder.append("DRAW SELECTION");
        for (GiverAssignment giver : givers) {
            builder.append('\n')
                .append(giver.getFrom().getName())
                .append(" -> ")
                .append(giver.getTo().getName());
        }

        return builder.toString();
    }
}
