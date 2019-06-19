/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.session;

import io.github.santulator.core.SantaException;
import io.github.santulator.model.SessionState;
import io.github.santulator.test.core.TestFileManager;
import io.github.santulator.test.session.TestSessionStateTool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SessionSerialiserTest {
    private TestFileManager files;

    private Path file;

    private final SessionState state1 = TestSessionStateTool.buildFullState("State 1");

    private final SessionState state2 = TestSessionStateTool.buildFullState("State 2");

    private final SessionSerialiser target = new SessionSerialiserImpl();

    @BeforeEach
    public void setUp() throws Exception {
        files = new TestFileManager(getClass());
        file = files.addFile("file.json");
    }

    @AfterEach
    public void tearDown() throws Exception {
        files.cleanup();
    }

    @Test
    public void testNonexistentFile() {
        assertThrows(SantaException.class, () -> target.read(Paths.get("does-not-exist.json")));
    }

    @Test
    public void testEmptyFile() throws Exception {
        Files.createFile(file);

        assertThrows(SantaException.class, () -> target.read(file));
    }

    @Test
    public void testInvalidFile() throws Exception {
        Files.write(file, List.of("unreadable"));

        assertThrows(SantaException.class, () -> target.read(file));
    }

    @Test
    public void testSameState() {
        SessionState read = writeAndReadBackState1();

        assertEquals(state1, read, "Same state");
    }

    @Test
    public void testDifferentState() {
        SessionState read = writeAndReadBackState1();

        assertNotEquals(state2, read, "Different state");
    }

    private SessionState writeAndReadBackState1() {
        target.write(file, state1);

        return target.read(file);
    }
}
