package io.github.santulator.gui.view;

import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.gui.model.ParticipantModel;
import io.github.santulator.model.ParticipantRole;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

    private static final String CLASS_LABEL_ROW_NUMBER = "labelRowNumber";

    private final CustomTextField fieldName = new CustomTextField();

    private final ComboBox<ParticipantRole> choiceRole = new ComboBox<>();

    private final Button buttonAction = new Button();

    private final Label labelRowNumber = new Label();

    private final ExclusionFields exclusionFields;

    private final HBox lineBox;

    private final ParticipantSelectionTool tool;

    private ParticipantModel lastItem;

    public ParticipantCell(final I18nManager i18nManager, final Consumer<ParticipantModel> actionButtonHandler, final Consumer<ParticipantModel> enterPressHandler,
        final ParticipantSelectionTool tool) {
        this.tool = tool;

        choiceRole.getItems().setAll(ParticipantRole.values());

        applyStyle(fieldName, CLASS_FIELD_NAME);
        fieldName.textProperty().addListener((o, old, v) -> lastItem.setName(v));
        fieldName.setOnAction(e -> enterPressHandler.accept(lastItem));
        fieldName.setLeft(icon(FontAwesome.Glyph.USER));

        applyStyle(labelRowNumber, CLASS_LABEL_ROW_NUMBER);

        applyStyle(choiceRole, CLASS_CHOICE_ROLE);
        choiceRole.setOnAction(e -> lastItem.setRole(choiceRole.getValue()));
        choiceRole.setCellFactory(p -> new RoleCell(i18nManager, true));
        choiceRole.setButtonCell(new RoleCell(i18nManager, false));

        applyStyle(buttonAction, CLASS_BUTTON_ACTION);
        buttonAction.setOnAction(e -> actionButtonHandler.accept(lastItem));

        exclusionFields = new ExclusionFields(() -> enterPressHandler.accept(lastItem));
        lineBox = new HBox(labelRowNumber, buttonAction, fieldName, choiceRole, exclusionFields.getBox());
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

            labelRowNumber.setText(rowNumber(item));

            choiceRole.getSelectionModel().select(item.getRole());
            choiceRole.disableProperty().set(isPlaceholder);

            exclusionFields.updateModel(item);

            buttonAction.setGraphic(buttonGraphic(isPlaceholder));

            tool.registerField(fieldName, getIndex());
        }
    }

    private String rowNumber(final ParticipantModel item) {
        if (item.isPlaceholder()) {
            return "";
        } else {
            return Integer.toString(item.getRowNumber());
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
