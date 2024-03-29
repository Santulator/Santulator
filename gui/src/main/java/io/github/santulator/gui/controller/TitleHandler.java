/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.controller;

import io.github.santulator.gui.i18n.I18nKey;
import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.gui.model.MainModel;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import javafx.beans.value.ChangeListener;
import org.apache.commons.lang3.StringUtils;

@Singleton
public class TitleHandler {
    private static final int TITLE_BUFFER_SIZE = 100;

    private final MainModel model;

    private final I18nManager i18nManager;

    @Inject
    public TitleHandler(final MainModel model, final I18nManager i18nManager) {
        this.model = model;
        this.i18nManager = i18nManager;
    }

    public void initialise() {
        ChangeListener<Object> listener = (o, old, v) -> updateTitle();

        model.sessionFileProperty().addListener(listener);
        model.changesSavedProperty().addListener(listener);
        model.drawNameProperty().addListener(listener);

        updateTitle();
    }

    private void updateTitle() {
        StringBuilder title = new StringBuilder(TITLE_BUFFER_SIZE);

        String drawName = model.getDrawName();

        if (StringUtils.isNotBlank(drawName)) {
            title.append(drawName);
        } else {
            title.append(i18nManager.text(I18nKey.MAIN_WINDOW_UNTITLED));
        }

        if (model.hasSessionFile()) {
            title.append(" (").append(model.getSessionFile().getFileName()).append(')');
        }

        if (!model.isChangesSaved()) {
            title.append(" - ").append(i18nManager.text(I18nKey.MAIN_WINDOW_UNSAVED));
        }

        title.append(" - Santulator");
        model.setTitle(title.toString());
    }
}
