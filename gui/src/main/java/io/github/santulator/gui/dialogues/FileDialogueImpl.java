/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.dialogues;

import io.github.santulator.gui.settings.SettingsManager;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class FileDialogueImpl implements FileDialogue {
    private final FileDialogueType type;

    private final Window window;

    private final SettingsManager settingsManager;

    private final List<ExtensionFilter> filters;

    private final BiFunction<FileChooser, Window, File> openFunction;

    private final Function<SettingsManager, Path> pathGetter;

    private final BiConsumer<SettingsManager, Path> pathSetter;

    private FileChoice selected;

    public FileDialogueImpl(final FileDialogueType type, final Window window, final SettingsManager settingsManager, final List<FileFormatType> formats,
        final BiFunction<FileChooser, Window, File> openFunction, final Function<SettingsManager, Path> pathGetter, final BiConsumer<SettingsManager, Path> pathSetter) {
        this.window = window;
        this.type = type;
        this.settingsManager = settingsManager;
        this.filters = formats.stream()
            .map(FileFormatType::getFilter)
            .collect(toList());
        this.openFunction = openFunction;
        this.pathGetter = pathGetter;
        this.pathSetter = pathSetter;
    }

    @Override
    public void showChooser() {
        FileChooser chooser = new FileChooser();

        chooser.setTitle(type.getTitle());
        chooser.getExtensionFilters().addAll(filters);
        File initialDirectory = pathGetter.apply(this.settingsManager).toFile();
        chooser.setInitialDirectory(initialDirectory);

        selected = showChooser(chooser);
    }

    private FileChoice showChooser(final FileChooser chooser) {
        File result = openFunction.apply(chooser, window);

        if (result == null) {
            return null;
        } else {
            Path file = result.toPath();
            ExtensionFilter extensionFilter = chooser.getSelectedExtensionFilter();
            FileFormatType type = FileFormatType.getByFilter(extensionFilter);

            pathSetter.accept(this.settingsManager, file.getParent());

            return new FileChoice(file, type);
        }
    }

    @Override
    public boolean isFileSelected() {
        return selected != null;
    }

    @Override
    public Path getSelectedFile() {
        return selected.getFile();
    }

    @Override
    public FileFormatType getFileFormatType() {
        return selected.getType();
    }
}
