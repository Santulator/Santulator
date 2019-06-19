/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.dialogues;

import io.github.santulator.test.core.AbstractBeanTest;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileChoiceTest extends AbstractBeanTest<FileChoice> {
    private static final Path FILE_1 = Paths.get("test1");

    private static final Path FILE_2 = Paths.get("test2");

    private static final FileChoice PRIMARY = new FileChoice(FILE_1, FileFormatType.SESSION);

    private static final FileChoice SECONDARY = new FileChoice(FILE_2, FileFormatType.SESSION);

    @Override
    protected FileChoice buildPrimary() {
        return PRIMARY;
    }

    @Override
    protected FileChoice buildSecondary() {
        return SECONDARY;
    }

    @Test
    public void testGetFile() {
        assertEquals(FILE_1, PRIMARY.getFile());
    }

    @Test
    public void testGetType() {
        assertEquals(FileFormatType.SESSION, PRIMARY.getType());
    }
}
