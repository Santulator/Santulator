package io.github.santulator.gui.view;

import io.github.santulator.gui.model.ParticipantModel;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.stream.IntStream;

import static io.github.santulator.gui.view.ExclusionFieldTool.*;
import static io.github.santulator.gui.view.ParticipantCell.CLASS_FIELD_EXCLUSIONS;
import static java.util.stream.Collectors.toList;

public class ExclusionFields {
    private static final int MAX_EXCLUSION_FIELDS = 5;

    private final List<TextField> fields = IntStream.range(0, MAX_EXCLUSION_FIELDS)
        .mapToObj(this::prepareExclusionField)
        .collect(toList());

    private final HBox box = new HBox(fields.toArray(new TextField[0]));

    private ParticipantModel model;

    private final Runnable enterPressHandler;

    public ExclusionFields(final Runnable enterPressHandler) {
        this.enterPressHandler = enterPressHandler;
    }

    private TextField prepareExclusionField(final int index) {
        TextField field = new TextField();

        applyStyle(field, CLASS_FIELD_EXCLUSIONS + "_" + index);
        field.textProperty().addListener((o, old, v) -> updateExclusions(model.getExclusions(), index, v));
        field.setOnAction(e -> enterPressHandler.run());

        return field;
    }

    private void applyStyle(final Node node, final String styleClass) {
        node.getStyleClass().add(styleClass);
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
        TextField field = fields.get(index);
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
