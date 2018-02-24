/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.engine;

import io.github.santulator.model.DrawRequirements;
import io.github.santulator.model.DrawSelection;

public interface DrawService {
    DrawSelection draw(DrawRequirements requirements);
}
