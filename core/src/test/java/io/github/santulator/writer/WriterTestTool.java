/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.writer;

import io.github.santulator.model.GiverAssignment;
import io.github.santulator.model.Person;

public final class WriterTestTool {
    private WriterTestTool() {
        // Prevent instantiation - all methods are static
    }

    public static GiverAssignment assignment(final String from, final String to) {
        Person fromPerson = new Person(from);
        Person toPerson = new Person(to);

        return new GiverAssignment(fromPerson, toPerson);
    }
}
