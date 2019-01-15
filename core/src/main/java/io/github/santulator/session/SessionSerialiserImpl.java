package io.github.santulator.session;

import io.github.santulator.core.FileTool;
import io.github.santulator.core.SantaException;
import io.github.santulator.model.SessionState;

import java.nio.file.Path;

import static io.github.santulator.model.SessionFormatVersion.LATEST_VERSION;

public class SessionSerialiserImpl implements SessionSerialiser {
    @Override
    public void write(final Path file, final SessionState state) {
        FileTool.writeAsJson(file, state, "Unable to save file '%s'");
    }

    @Override
    public SessionState read(final Path file) {
        SessionState state = FileTool.readFromJson(SessionState.class, file, "Unable to load file '%s'");
        int version = state.getFormatVersion();

        if (version < 1 || version > LATEST_VERSION) {
            throw new SantaException("This file was created with a newer version of Santulator.  Please upgrade and try again.");
        } else if (SessionState.FORMAT_NAME.equals(state.getFormatName())) {
            return state;
        } else {
            throw new SantaException("This file does not contain a Santulator session.");
        }
    }
}
