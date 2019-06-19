/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.session;

import io.github.santulator.model.ParticipantState;

import java.nio.file.Path;
import java.util.List;

public interface SessionImporter {
    List<ParticipantState> importParticipants(Path file);
}
