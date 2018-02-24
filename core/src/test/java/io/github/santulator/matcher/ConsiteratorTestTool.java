/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import java.util.List;
import java.util.stream.Collectors;

import static io.github.santulator.core.CoreTool.listOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class ConsiteratorTestTool {
    private ConsiteratorTestTool() {
        // Prevent instantiation - all methods are static
    }

    public static void validate(final ConsIterable<String> actual, final String... expected) {
        List<String> actualList = actual.stream()
                .collect(Collectors.toList());

        assertEquals(listOf(expected), actualList, "Content");
    }
}
