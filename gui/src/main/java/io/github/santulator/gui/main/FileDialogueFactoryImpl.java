/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.main;

import io.github.santulator.gui.dialogues.*;
import io.github.santulator.gui.settings.SettingsManager;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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

    private final Map<FileDialogueType, Function<Stage, FileDialogue>> creators = buildCreatorMap();

    @Inject
    public FileDialogueFactoryImpl(final SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    @Override
    public FileDialogue create(final FileDialogueType type, final Stage stage) {
        Function<Stage, FileDialogue> factory = creators.get(type);

        return factory.apply(stage);
    }

    private FileDialogue createOpenSessionDialogue(final Stage stage) {
        return new FileDialogueImpl(
            OPEN_SESSION, stage, settingsManager, FileFormatType.TYPES_SESSIONS, FileChooser::showOpenDialog, SettingsManager::getSessionsPath, SettingsManager::setSessionsPath);
    }

    private FileDialogue createSaveSessionDialogue(final Stage stage) {
        return new FileDialogueImpl(
            SAVE_SESSION, stage, settingsManager, FileFormatType.TYPES_SESSIONS, FileChooser::showSaveDialog, SettingsManager::getSessionsPath, SettingsManager::setSessionsPath);
    }

    private FileDialogue createRunDrawDialogue(final Stage stage) {
        return new DirectoryDialogueImpl(
            RUN_DRAW, DRAW, stage, settingsManager, SettingsManager::getDrawPath, SettingsManager::setDrawPath);
    }

    private Map<FileDialogueType, Function<Stage, FileDialogue>> buildCreatorMap() {
        Map<FileDialogueType, Function<Stage, FileDialogue>> map = new EnumMap<>(FileDialogueType.class);

        map.put(OPEN_SESSION, this::createOpenSessionDialogue);
        map.put(SAVE_SESSION, this::createSaveSessionDialogue);
        map.put(RUN_DRAW, this::createRunDrawDialogue);

        return unmodifiableMap(map);
    }
}
