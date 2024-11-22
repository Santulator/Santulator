/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.view;

import io.github.santulator.gui.model.ParticipantModel;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.glyphfont.FontAwesome;

import java.util.List;
import java.util.stream.IntStream;

import static io.github.santulator.gui.view.ExclusionFieldTool.updateExclusions;
import static io.github.santulator.gui.view.IconTool.icon;
import static io.github.santulator.gui.view.ParticipantCell.CLASS_FIELD_EXCLUSIONS;

public class ExclusionFields {
    private static final int MAX_EXCLUSION_FIELDS = 9;

    private final TextField[] fields = IntStream.range(0, MAX_EXCLUSION_FIELDS)
        .mapToObj(this::prepareExclusionField)
        .toArray(TextField[]::new);

    private final HBox box = new HBox(fields);

    private ParticipantModel model;

    private final Runnable enterPressHandler;

    public ExclusionFields(final Runnable enterPressHandler) {
        this.enterPressHandler = enterPressHandler;
    }

    private TextField prepareExclusionField(final int index) {
        CustomTextField field = new CustomTextField();

        applyStyles(field, CLASS_FIELD_EXCLUSIONS, index);
        field.textProperty().addListener((o, old, v) -> updateExclusions(model.getExclusions(), index, v));
        field.setOnAction(e -> enterPressHandler.run());
        field.setLeft(icon(FontAwesome.Glyph.USER_TIMES));

        return field;
    }

    private void applyStyles(final Node node, final String styleClass, final int index) {
        List<String> styles = node.getStyleClass();

        styles.add(styleClass);
        styles.add(styleClass + "_" + index);
    }

    public void updateModel(final ParticipantModel model) {
        this.model = model;
        updateExclusionFieldContent();
    }

    private void updateExclusionFieldContent() {
        IntStream.range(0, MAX_EXCLUSION_FIELDS)
            .forEach(this::updateExclusionFieldContent);
    }

    private void updateExclusionFieldContent(final int index) {
        TextField field = fields[index];
        List<String> exclusions = model.getExclusions();
        int exclusionCount = exclusions.size();
        boolean isVisible = index <= exclusionCount;

        field.setManaged(isVisible);
        field.setVisible(isVisible);
        if (index < exclusionCount) {
            String value = exclusions.get(index);

            field.setText(value);
        } else {
            field.clear();
        }
        field.setDisable(model.isPlaceholder());
    }

    public HBox getBox() {
        return box;
    }
}
