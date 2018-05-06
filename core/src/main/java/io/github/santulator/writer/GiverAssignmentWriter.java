/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.writer;

import io.github.santulator.model.GiverAssignment;

import java.io.OutputStream;

public interface GiverAssignmentWriter {
    void writeGiverAssignment(String name, GiverAssignment assignment, OutputStream out, String password);

    String getFormatSuffix();
}
