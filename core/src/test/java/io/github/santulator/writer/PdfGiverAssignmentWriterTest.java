/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.writer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.santulator.model.GiverAssignment;
import io.github.santulator.test.core.TestFileManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PdfGiverAssignmentWriterTest {
    private Path file;

    private TestFileManager manager;

    private GiverAssignmentWriter target;

    @BeforeEach
    public void setUp() throws Exception {
        manager = new TestFileManager(PdfGiverAssignmentWriterTest.class);
        file = manager.addFile("test.pdf");
        if (Files.exists(file)) {
            FileUtils.forceDelete(file.toFile());
        }
        Injector injector = Guice.createInjector(new WriterModule(), new TestResourcesModule());
        target = injector.getInstance(GiverAssignmentWriter.class);
    }

    @AfterEach
    public void tearDown() throws Exception {
        manager.cleanup();
    }

    @Test
    public void testWrite() throws Exception {
        try (OutputStream out = Files.newOutputStream(file)) {
            GiverAssignment assignment = WriterTestTool.assignment("Albert", "Beryl");

            target.writeGiverAssignment(file.toString(), assignment, out, "secret");
            assertTrue(Files.size(file) > 0, "Output");
        }
    }
}
