package io.github.santulator.gui.view;

import io.github.santulator.gui.i18n.I18nGuiKey;
import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.model.ParticipantRole;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public class RoleCell extends ListCell<ParticipantRole> {
    private final Map<ParticipantRole, Node> nodes = buildNodeMap();

    private final Map<ParticipantRole, String> texts;

    private final boolean isLongForm;

    public RoleCell(final I18nManager i18nManager, final boolean isLongForm) {
        this.isLongForm = isLongForm;
        this.texts = buildTextMap(i18nManager);
    }

    @Override
    protected void updateItem(final ParticipantRole item, final boolean isEmpty) {
        super.updateItem(item, isEmpty);
        if (isEmpty || item == null) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(nodes.get(item));
            if (isLongForm) {
                setText(texts.get(item));
            } else {
                setText(null);
            }
        }
    }

    private Map<ParticipantRole, Node> buildNodeMap() {
        Map<ParticipantRole, Node> nodes = new EnumMap<>(ParticipantRole.class);

        Node giver = buildNode(FontAwesome.Glyph.USER, FontAwesome.Glyph.LONG_ARROW_RIGHT, FontAwesome.Glyph.GIFT);
        nodes.put(ParticipantRole.GIVER, giver);

        Node receiver = buildNode(FontAwesome.Glyph.GIFT, FontAwesome.Glyph.LONG_ARROW_RIGHT, FontAwesome.Glyph.USER);
        nodes.put(ParticipantRole.RECEIVER, receiver);

        Node both = buildNode(FontAwesome.Glyph.USER, FontAwesome.Glyph.EXCHANGE, FontAwesome.Glyph.USER);
        nodes.put(ParticipantRole.BOTH, both);

        return nodes;
    }

    private Node buildNode(final FontAwesome.Glyph... icons) {
        Glyph[] glyphs = Arrays.stream(icons)
            .map(IconTool::icon)
            .toArray(Glyph[]::new);

        return new HBox(glyphs);
    }

    private Map<ParticipantRole, String> buildTextMap(final I18nManager i18nManager) {
        Map<ParticipantRole, String> texts = new EnumMap<>(ParticipantRole.class);

        texts.put(ParticipantRole.GIVER, i18nManager.guiText(I18nGuiKey.SESSION_ROLE_GIVER));
        texts.put(ParticipantRole.RECEIVER, i18nManager.guiText(I18nGuiKey.SESSION_ROLE_RECEIVER));
        texts.put(ParticipantRole.BOTH, i18nManager.guiText(I18nGuiKey.SESSION_ROLE_BOTH));

        return texts;
    }
}
