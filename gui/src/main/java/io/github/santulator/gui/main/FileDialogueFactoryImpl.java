/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.main;

import io.github.santulator.gui.dialogues.*;
import io.github.santulator.gui.settings.SettingsManager;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Singleton;

import static io.github.santulator.gui.dialogues.FileDialogueType.*;
import static io.github.santulator.gui.dialogues.FileFormatType.DRAW;
import static java.util.Collections.unmodifiableMap;

@Singleton
public class FileDialogueFactoryImpl implements FileDialogueFactory {
    private final SettingsManager settingsManager;

    private final Map<FileDialogueType, Function<Window, FileDialogue>> creators = buildCreatorMap();

    @Inject
    public FileDialogueFactoryImpl(final SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    @Override
    public FileDialogue create(final FileDialogueType type, final Window window) {
        Function<Window, FileDialogue> factory = creators.get(type);

        return factory.apply(window);
    }

    private FileDialogue createOpenSessionDialogue(final Window window) {
        return new FileDialogueImpl(
            OPEN_SESSION, window, settingsManager, FileFormatType.TYPES_SESSIONS, FileChooser::showOpenDialog, SettingsManager::getSessionsPath, SettingsManager::setSessionsPath);
    }

    private FileDialogue createSaveSessionDialogue(final Window window) {
        return new FileDialogueImpl(
            SAVE_SESSION, window, settingsManager, FileFormatType.TYPES_SESSIONS, FileChooser::showSaveDialog, SettingsManager::getSessionsPath, SettingsManager::setSessionsPath);
    }

    private FileDialogue createRunDrawDialogue(final Window window) {
        return new DirectoryDialogueImpl(
            RUN_DRAW, DRAW, window, settingsManager, SettingsManager::getDrawPath, SettingsManager::setDrawPath);
    }

    private Map<FileDialogueType, Function<Window, FileDialogue>> buildCreatorMap() {
        Map<FileDialogueType, Function<Window, FileDialogue>> map = new EnumMap<>(FileDialogueType.class);

        map.put(OPEN_SESSION, this::createOpenSessionDialogue);
        map.put(SAVE_SESSION, this::createSaveSessionDialogue);
        map.put(RUN_DRAW, this::createRunDrawDialogue);

        return unmodifiableMap(map);
    }
}
