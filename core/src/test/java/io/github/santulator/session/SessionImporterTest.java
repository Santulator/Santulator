package io.github.santulator.session;

import io.github.santulator.core.SantaException;
import io.github.santulator.model.ParticipantRole;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static io.github.santulator.core.ExcelToolTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SessionImporterTest {
    private final List<ParticipantState> expectedContent = List.of(
        new ParticipantState("A1", ParticipantRole.BOTH),
        new ParticipantState("A4", ParticipantRole.BOTH)
    );

    private final
    SessionImporter target = new SessionImporterImpl();

    @Test
    public void testUnreadable() {
        assertThrows(SantaException.class, () -> load(FILE_UNREADABLE));
    }

    @Test
    public void testZeroBytes() {
        assertThrows(SantaException.class, () -> load(FILE_ZERO_BYTES));
    }

    @Test
    public void testEmpty() {
        assertThrows(SantaException.class, () -> load(FILE_EMPTY));
    }

    @Test
    public void testContent() throws Exception {
        List<ParticipantState> content = load(FILE_TEST);

        assertEquals(expectedContent, content);
    }

    private List<ParticipantState> load(final String name) throws Exception {
        Path file = getResourceFile(name);

        return target.importParticipants(file);
    }

    private Path getResourceFile(final String file) throws Exception {
        return Paths.get(SessionImporterTest.class.getResource(file).toURI());
    }
}