package io.github.santulator.gui.view;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public final class ExclusionFieldTool {
    private ExclusionFieldTool() {
        // Prevent instantiation - all methods are static
    }

    public static void updateExclusions(final List<String> exclusions, final int index, final String value) {
        boolean isBlank = StringUtils.isBlank(value);

        if (index < exclusions.size()) {
            if (isBlank && index == lastIndex(exclusions)) {
                removeLastWithCascade(exclusions);
            } else {
                replaceIfDifferent(exclusions, index, value);
            }
        } else if (!isBlank) {
            padUpToIndex(exclusions, index);
            exclusions.add(value);
        }
    }

    private static void replaceIfDifferent(final List<String> exclusions, final int index, final String value) {
        String old = exclusions.get(index);

        if (!old.equals(value)) {
            exclusions.set(index, value);
        }
    }

    private static void padUpToIndex(final List<String> exclusions, final int index) {
        while (index > exclusions.size()) {
            exclusions.add("");
        }
    }

    private static void removeLastWithCascade(final List<String> exclusions) {
        removeLast(exclusions);
        while (!exclusions.isEmpty() && StringUtils.isBlank(lastElement(exclusions))) {
            removeLast(exclusions);
        }
    }

    private static void removeLast(final List<String> exclusions) {
        exclusions.remove(lastIndex(exclusions));
    }

    private static String lastElement(final List<String> exclusions) {
        return exclusions.get(lastIndex(exclusions));
    }

    private static int lastIndex(final List<String> exclusions) {
        return exclusions.size() - 1;
    }
}
