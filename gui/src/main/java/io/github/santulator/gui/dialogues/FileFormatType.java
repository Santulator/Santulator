/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.dialogues;

import io.github.santulator.core.SantaException;
import io.github.santulator.gui.i18n.I18nGuiKey;
import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.session.FileNameTool;
import javafx.stage.FileChooser.ExtensionFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static io.github.santulator.core.CoreTool.listOf;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public enum FileFormatType {
    SESSION(I18nGuiKey.FILE_TYPE_SESSION, "*" + FileNameTool.SESSION_SUFFIX),
    DRAW(I18nGuiKey.FILE_TYPE_DRAW, "*");

    public static final List<FileFormatType> TYPES_SESSIONS = listOf(SESSION);

    private static final Map<List<String>, FileFormatType> TYPES = Stream.of(FileFormatType.values())
        .collect(toMap(FileFormatType::getExtensions, identity()));

    private final I18nGuiKey descriptionKey;

    private final List<String> extensions;

    FileFormatType(final I18nGuiKey descriptionKey, final String... extensions) {
        this.descriptionKey = descriptionKey;
        this.extensions = Arrays.asList(extensions);
    }

    private List<String> getExtensions() {
        return Collections.unmodifiableList(extensions);
    }

    public ExtensionFilter buildFilter(final I18nManager i18nManager) {
        return new ExtensionFilter(i18nManager.guiText(descriptionKey), extensions);
    }

    public static FileFormatType getByFilter(final ExtensionFilter filter) {
        FileFormatType result = TYPES.get(filter.getExtensions());

        if (result == null) {
            throw new SantaException("Unknown type " + filter.getDescription());
        } else {
            return result;
        }
    }
}
