package io.github.santulator.core;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static io.github.santulator.core.CoreTool.listOf;

public final class CsvTool {
    private static final Pattern PATTERN = Pattern.compile("\\s*,\\s*");

    private static final List<String> SINGLE_BLANK = listOf("");

    private CsvTool() {
        // Prevent instantiation - all methods are static
    }

    public static List<String> splitToList(final String text) {
        if (text == null) {
            return Collections.emptyList();
        } else {
            String trimmed = text.trim();
            List<String> result = listOf(PATTERN.split(trimmed, -1));

            if (result.equals(SINGLE_BLANK)) {
                return Collections.emptyList();
            } else {
                return result;
            }
        }
    }
}
