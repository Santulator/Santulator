/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.session;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressFBWarnings("DMI_HARDCODED_ABSOLUTE_FILENAME")
public class FileNameToolTest {
    @Test
    public void testEmpty() {
        validate("", ".santa");
    }

    @Test
    public void testExtensionAtRoot() {
        validate("test.santa", "test.santa");
    }

    @Test
    public void testNoExtensionAtRoot() {
        validate("test", "test.santa");
    }

    @Test
    public void testExtensionAtLeaf() {
        validate("/root/next/test.santa", "/root/next/test.santa");
    }

    @Test
    public void testNoExtensionAtLeaf() {
        validate("/root/next/test", "/root/next/test.santa");
    }

    private void validate(final String original, final String expected) {
        Path originalPath = Paths.get(original);
        Path expectedPath = Paths.get(expected);

        Path actual = FileNameTool.ensureSessionFileHasSuffix(originalPath);
        assertEquals(expectedPath, actual, "Path match");
    }

    @ParameterizedTest
    @CsvSource({
        "'test.santa',       true",
        "'/home/test.santa', true",
        "'/home/test',       false",
        "'/home/test.txt',   false"
    })
    public void testIsSessionFile(final String name, final boolean expected) {
        Path file = Paths.get(name);

        assertEquals(expected, FileNameTool.isSessionFile(file));
    }
}
