package io.github.santulator.core;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

public class ExcelToolTest {
    private static final String PATH_PREFIX = "/spreadsheets/";

    public static final String FILE_UNREADABLE = PATH_PREFIX + "unreadable.xls";

    public static final String FILE_ZERO_BYTES = PATH_PREFIX + "zero-bytes.xls";

    public static final String FILE_EMPTY = PATH_PREFIX + "empty.xls";

    public static final String FILE_TEST = PATH_PREFIX + "test.xls";

    private static final String[][] EXPECTED_CONTENT_ARRAY = {
        {null, "B1"},
        {"A1", "B2", "C3"},
        {"A4", "B4"}
    };

    private static final List<List<String>> EXPECTED_CONTENT = Arrays.stream(EXPECTED_CONTENT_ARRAY)
        .map(Arrays::asList)
        .collect(toList());

    @Test
    public void testUnreadable() {
        assertThrows(SantaException.class, () -> load(FILE_UNREADABLE));
    }

    @Test
    public void testZeroBytes() {
        assertThrows(SantaException.class, () -> load(FILE_ZERO_BYTES));
    }

    @Test
    public void testEmpty() throws Exception {
        List<List<String>> content = load(FILE_EMPTY);

        assertTrue(content.isEmpty());
    }

    @Test
    public void testContent() throws Exception {
        List<List<String>> content = load(FILE_TEST);

        assertEquals(EXPECTED_CONTENT, content);
    }

    private List<List<String>> load(final String name) throws Exception {
        try (InputStream in = ExcelToolTest.class.getResourceAsStream(name)) {
            return ExcelTool.readContent(name, in);
        }
    }
}