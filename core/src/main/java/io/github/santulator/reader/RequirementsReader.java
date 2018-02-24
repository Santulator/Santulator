/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.reader;

import io.github.santulator.model.DrawRequirements;

import java.io.InputStream;

public interface RequirementsReader {
    DrawRequirements read(String name, InputStream stream);
}
