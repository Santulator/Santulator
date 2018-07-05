package io.github.santulator.gui.view;

import io.github.santulator.gui.model.ParticipantModel;
import io.github.santulator.model.ParticipantRole;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.glyphfont.FontAwesome;

import java.util.function.Consumer;

import static io.github.santulator.gui.view.IconTool.icon;

public class ParticipantCell extends ListCell<ParticipantModel> {
    public static final String CLASS_FIELD_NAME = "fieldParticipantName";

    public static final String CLASS_CHOICE_ROLE = "choiceRole";

    public static final String CLASS_FIELD_EXCLUSIONS = "fieldExclusions";

    public static final String CLASS_BUTTON_ACTION = "buttonParticipantAction";

    private final CustomTextField fieldName = new CustomTextField();

    private final ComboBox<ParticipantRole> choiceRole = new ComboBox<>();

    private final Button buttonAction = new Button();

    private final ExclusionFields exclusionFields;

    private final HBox lineBox;

    private final ParticipantSelectionTool tool;

    private ParticipantModel lastItem;

    public ParticipantCell(final Consumer<ParticipantModel> actionButtonHandler, final Consumer<ParticipantModel> enterPressHandler, final ParticipantSelectionTool tool) {
        this.tool = tool;

        choiceRole.getItems().setAll(ParticipantRole.values());

        applyStyle(fieldName, CLASS_FIELD_NAME);
        fieldName.textProperty().addListener((o, old, v) -> lastItem.setName(v));
        fieldName.setOnAction(e -> enterPressHandler.accept(lastItem));
        fieldName.setLeft(icon(FontAwesome.Glyph.USER));

        applyStyle(choiceRole, CLASS_CHOICE_ROLE);
        choiceRole.setOnAction(e -> lastItem.setRole(choiceRole.getValue()));
        choiceRole.setCellFactory(p -> new RoleCell(true));
        choiceRole.setButtonCell(new RoleCell(false));

        applyStyle(buttonAction, CLASS_BUTTON_ACTION);
        buttonAction.setOnAction(e -> actionButtonHandler.accept(lastItem));

        exclusionFields = new ExclusionFields(() -> enterPressHandler.accept(lastItem));
        lineBox = new HBox(buttonAction, fieldName, choiceRole, exclusionFields.getBox());
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
            buttonAction.setGraphic(buttonGraphic(isPlaceholder));

            tool.registerField(fieldName, getIndex());
        }
    }

    private Node buttonGraphic(final boolean isPlaceholder) {
        if (isPlaceholder) {
            return icon(FontAwesome.Glyph.PLUS_CIRCLE);
        } else {
            return icon(FontAwesome.Glyph.MINUS_CIRCLE);
        }
    }
}
