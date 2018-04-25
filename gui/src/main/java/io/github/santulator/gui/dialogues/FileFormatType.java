/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.dialogues;

import io.github.santulator.core.SantaException;
import io.github.santulator.session.FileNameTool;
import javafx.stage.FileChooser.ExtensionFilter;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static io.github.santulator.core.CoreTool.listOf;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public enum FileFormatType {
    SESSION("Santulator Session Files", "*" + FileNameTool.SESSION_SUFFIX);

    static final List<FileFormatType> TYPES_SESSIONS = listOf(SESSION);

    private static final Map<ExtensionFilter, FileFormatType> TYPES = Stream.of(FileFormatType.values())
        .collect(toMap(FileFormatType::getFilter, identity()));

    private final ExtensionFilter filter;

    FileFormatType(final String description, final String... extensions) {
        this.filter = new ExtensionFilter(description, extensions);
    }

    public ExtensionFilter getFilter() {
        return filter;
    }

    public static FileFormatType getByFilter(final ExtensionFilter filter) {
        FileFormatType result = TYPES.get(filter);

        if (result == null) {
            throw new SantaException("Unknown type " + filter.getDescription());
        } else {
            return result;
        }
    }
}
