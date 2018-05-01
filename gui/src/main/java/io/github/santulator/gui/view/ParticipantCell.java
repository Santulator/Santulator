package io.github.santulator.gui.view;

import io.github.santulator.gui.model.ParticipantModel;
import io.github.santulator.model.ParticipantRole;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.function.Consumer;

public class ParticipantCell extends ListCell<ParticipantModel> {
    private final TextField fieldName = new TextField();

    private final ChoiceBox<ParticipantRole> choiceRole = new ChoiceBox<>();

    private final TextField fieldExclusions = new TextField();

    private final Button buttonAction = new Button();

    private final HBox hbox = new HBox(fieldName, choiceRole, fieldExclusions, buttonAction);

    private ParticipantModel lastItem;

    public ParticipantCell(final Consumer<ParticipantModel> actionButtonHandler) {
        choiceRole.getItems().setAll(ParticipantRole.values());

        fieldName.textProperty().addListener((o, old, v) -> lastItem.setName(v));
        choiceRole.setOnAction(e -> lastItem.setRole(choiceRole.getValue()));
        fieldExclusions.textProperty().addListener((o, old, v) -> lastItem.setExclusions(v));
        buttonAction.setOnAction(e -> actionButtonHandler.accept(lastItem));
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
            setGraphic(hbox);

            boolean isPlaceholder = item.isPlaceholder();

            fieldName.setText(item.getName());
            fieldName.disableProperty().set(isPlaceholder);
            choiceRole.getSelectionModel().select(item.getRole());
            choiceRole.disableProperty().set(isPlaceholder);
            fieldExclusions.setText(item.getExclusions());
            fieldExclusions.disableProperty().set(isPlaceholder);
            buttonAction.setText(buttonName(isPlaceholder));
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
