package io.github.santulator.session;

import java.nio.file.Path;
import java.util.List;

public interface SessionImporter {
    List<ParticipantState> importParticipants(Path file);
}
