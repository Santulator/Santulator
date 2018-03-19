/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.executable;

import java.nio.file.Path;

public interface SimpleSantaRunner {
    void run(Path input, Path output);
}
