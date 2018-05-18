/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.MainModel;
import javafx.beans.value.ChangeListener;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import static io.github.santulator.gui.common.GuiConstants.UNTITLED;

@Singleton
public class TitleHandler {
    private static final int TITLE_BUFFER_SIZE = 100;

    private final MainModel model;

    @Inject
    public TitleHandler(final MainModel model) {
        this.model = model;
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
            title.append(UNTITLED);
        }

        if (model.hasSessionFile()) {
            title.append(" (").append(model.getSessionFile().getFileName()).append(')');
        }

        if (!model.isChangesSaved()) {
            title.append(" - Unsaved Changes");
        }

        title.append(" - Santulator");
        model.setTitle(title.toString());
    }
}
