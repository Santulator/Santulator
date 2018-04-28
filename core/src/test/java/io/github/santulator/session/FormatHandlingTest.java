package io.github.santulator.session;

import io.github.santulator.core.SantaException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FormatHandlingTest {
    private static final String FORMAT_1 = "format1.santa";

    private static final String FORMAT_UNSUPPORTED_VERSION = "format-unsupported-version.santa";

    private static final String FORMAT_UNSUPPORTED_NAME = "format-unsupported-name.santa";

    private static final SessionState EXPECTED_STATE = TestSessionStateTool.buildFullState();

    private final SessionSerialiser target = new SessionSerialiserImpl();

    @ParameterizedTest
    @ValueSource(strings = {FORMAT_UNSUPPORTED_VERSION, FORMAT_UNSUPPORTED_NAME})
    public void testUnsupportedFormat(final String filename) {
        assertThrows(SantaException.class, () -> readState(filename));
    }

    @Test
    public void testFormat1() throws Exception {
        SessionState state = readState(FORMAT_1);

        assertEquals(EXPECTED_STATE, state);
    }

    private SessionState readState(final String name) throws Exception {
        return target.read(getResourceFile(name));
    }

    private Path getResourceFile(final String file) throws Exception {
        return Paths.get(FormatHandlingTest.class.getResource("/" + file).toURI());
    }
}
