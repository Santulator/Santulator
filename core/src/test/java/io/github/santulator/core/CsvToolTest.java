package io.github.santulator.core;

import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.santulator.core.CoreTool.listOf;
import static org.junit.jupiter.api.Assertions.*;

public class CsvToolTest {
    @Test
    public void testNull() {
        validate(null);
    }

    @Test
    public void testEmpty() {
        validate("");
    }

    @Test
    public void testBlank() {
        validate("");
    }

    @Test
    public void testSingle() {
        validate("abc", "abc");
    }

    @Test
    public void testSingleWithSpaces() {
        validate(" abc ", "abc");
    }

    @Test
    public void testList() {
        validate(" abc, 123,xyz ,456 ", "abc", "123", "xyz", "456");
    }

    @Test
    public void testComma() {
        validate(",", "", "");
    }

    private void validate(final String input, final String... expected) {
        List<String> result = CsvTool.splitToList(input);

        assertEquals(listOf(expected), result);
    }
}