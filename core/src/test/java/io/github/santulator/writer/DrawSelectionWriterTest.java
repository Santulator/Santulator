/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.writer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.santulator.model.DrawSelection;
import io.github.santulator.model.GiverAssignment;
import io.github.santulator.test.TestFileManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DrawSelectionWriterTest {
    private static final String PERSON_ALBERT = "Albert";

    private static final String PERSON_BERYL = "Beryl";

    private static final String PASSWORD = "password";

    private TestFileManager manager;

    private Path dir;

    private DrawSelectionWriter target;

    @BeforeEach
    public void setUp() throws Exception {
        manager = new TestFileManager(DrawSelectionWriterTest.class);
        dir = manager.addFile("selection");
        Injector injector = Guice.createInjector(new WriterModule(), new TestResourcesModule());
        target = injector.getInstance(DrawSelectionWriter.class);
    }

    @AfterEach
    public void tearDown() throws Exception {
        manager.cleanup();
    }

    @Test
    public void testWrite() throws Exception {
        DrawSelection selection = selection();
        AtomicInteger callCount = new AtomicInteger();

        target.writeDrawSelection(selection, dir, PASSWORD, callCount::incrementAndGet);
        validateFile(PERSON_ALBERT);
        validateFile(PERSON_BERYL);
        assertEquals(2, callCount.get());
    }

    private DrawSelection selection() {
        List<GiverAssignment> givers = new ArrayList<>();

        givers.add(WriterTestTool.assignment(PERSON_ALBERT, "Carla"));
        givers.add(WriterTestTool.assignment(PERSON_BERYL, "David"));

        return new DrawSelection(givers);
    }

    private void validateFile(final String person) throws Exception {
        String name = person + PdfGiverAssignmentWriter.FORMAT_SUFFIX;
        Path file = dir.resolve(name);
        long size = Files.size(file);

        assertTrue(size > 0, file + " file");
    }
}
