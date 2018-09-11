/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.dialogues;

import io.github.santulator.gui.common.UnsavedResponse;
import io.github.santulator.gui.i18n.I18nGuiKey;
import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.session.FileNameTool;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

import static io.github.santulator.gui.view.CssTool.applyCss;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class UnsavedChangesDialogue {
    private static final String ALERT_ID = "unsavedChanges";

    private final Path file;

    private final I18nManager i18nManager;

    private final Map<ButtonType, UnsavedResponse> map;

    private UnsavedResponse result;

    public UnsavedChangesDialogue(final Path file, final I18nManager i18nManager) {
        this.file = file;
        this.i18nManager = i18nManager;
        this.map = Stream.of(UnsavedResponse.values())
            .collect(toMap(this::buttonType, identity()));
    }

    public void showDialogue() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        String message = i18nManager.guiText(I18nGuiKey.FILE_MODIFIED, filename());

        alert.setTitle(i18nManager.guiText(I18nGuiKey.FILE_UNSAVED));
        alert.setHeaderText(message);
        alert.getButtonTypes().setAll(map.keySet());
        alert.getDialogPane().setId(ALERT_ID);
        applyCss(alert);

        result = alert.showAndWait()
            .map(map::get)
            .orElse(UnsavedResponse.CANCEL);
    }

    private String filename() {
        if (file == null) {
            return i18nManager.guiText(I18nGuiKey.MAIN_WINDOW_UNTITLED);
        } else {
            return FileNameTool.filename(file);
        }
    }

    public UnsavedResponse getUserResponse() {
        return result;
    }

    private ButtonType buttonType(final UnsavedResponse r) {
        return new ButtonType(i18nManager.guiText(r.getKey()));
    }
}
