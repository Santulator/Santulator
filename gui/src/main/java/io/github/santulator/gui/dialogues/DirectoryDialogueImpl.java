/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.dialogues;

import io.github.santulator.gui.settings.SettingsManager;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DirectoryDialogueImpl implements FileDialogue {
    private final FileDialogueType type;

    private final FileFormatType formatType;

    private final Window window;

    private final SettingsManager settingsManager;

    private final Function<SettingsManager, Path> pathGetter;

    private final BiConsumer<SettingsManager, Path> pathSetter;

    private FileChoice selected;

    public DirectoryDialogueImpl(final FileDialogueType type, final FileFormatType formatType, final Window window, final SettingsManager settingsManager,
        final Function<SettingsManager, Path> pathGetter, final BiConsumer<SettingsManager, Path> pathSetter) {
        this.window = window;
        this.formatType = formatType;
        this.type = type;
        this.settingsManager = settingsManager;
        this.pathGetter = pathGetter;
        this.pathSetter = pathSetter;
    }

    @Override
    public void showChooser() {
        DirectoryChooser chooser = new DirectoryChooser();

        chooser.setTitle(type.getTitle());
        File initialDirectory = pathGetter.apply(this.settingsManager).toFile();
        chooser.setInitialDirectory(initialDirectory);

        selected = showChooser(chooser);
    }

    private FileChoice showChooser(final DirectoryChooser chooser) {
        File result = chooser.showDialog(window);

        if (result == null) {
            return null;
        } else {
            Path file = result.toPath();

            pathSetter.accept(this.settingsManager, file.getParent());

            return new FileChoice(file, formatType);
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
