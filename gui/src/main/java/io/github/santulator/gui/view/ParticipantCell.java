package io.github.santulator.gui.view;

import io.github.santulator.gui.model.ParticipantModel;
import io.github.santulator.model.ParticipantRole;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.function.Consumer;

public class ParticipantCell extends ListCell<ParticipantModel> {
    public static final String CLASS_FIELD_NAME = "fieldParticipantName";

    public static final String CLASS_CHOICE_ROLE = "choiceRole";

    public static final String CLASS_FIELD_EXCLUSIONS = "fieldExclusions";

    public static final String CLASS_BUTTON_ACTION = "buttonParticipantAction";

    private final TextField fieldName = new TextField();

    private final ChoiceBox<ParticipantRole> choiceRole = new ChoiceBox<>();

    private final Button buttonAction = new Button();

    private final ExclusionFields exclusionFields = new ExclusionFields();

    private final HBox lineBox = new HBox(fieldName, choiceRole, exclusionFields.getBox(), buttonAction);

    private final ParticipantTableTool tool;

    private ParticipantModel lastItem;

    public ParticipantCell(final Consumer<ParticipantModel> actionButtonHandler, final ParticipantTableTool tool) {
        this.tool = tool;

        choiceRole.getItems().setAll(ParticipantRole.values());

        applyStyle(fieldName, CLASS_FIELD_NAME);
        fieldName.textProperty().addListener((o, old, v) -> lastItem.setName(v));

        applyStyle(choiceRole, CLASS_CHOICE_ROLE);
        choiceRole.setOnAction(e -> lastItem.setRole(choiceRole.getValue()));

        applyStyle(buttonAction, CLASS_BUTTON_ACTION);
        buttonAction.setOnAction(e -> actionButtonHandler.accept(lastItem));
    }

    private void applyStyle(final Node node, final String styleClass) {
        node.getStyleClass().add(styleClass);
    }

    @Override
    protected void updateItem(final ParticipantModel item, final boolean isEmpty) {
        super.updateItem(item, isEmpty);
        setText(null);
        if (isEmpty || item == null) {
            setGraphic(null);
            lastItem = null;
        } else {
            lastItem = item;
            setGraphic(lineBox);

            boolean isPlaceholder = item.isPlaceholder();

            fieldName.setText(item.getName());
            fieldName.disableProperty().set(isPlaceholder);
            choiceRole.getSelectionModel().select(item.getRole());
            choiceRole.disableProperty().set(isPlaceholder);
            exclusionFields.updateModel(item);
            buttonAction.setText(buttonName(isPlaceholder));

            tool.registerField(fieldName, getIndex());
        }
    }

    private String buttonName(final boolean isPlaceholder) {
        if (isPlaceholder) {
            return "Add";
        } else {
            return "Delete";
        }
    }
}
