package io.github.santulator.gui.view;

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
    private static final String GIVER_TEXT = "This person only gives a gift";

    private static final String RECEIVER_TEXT = "This person only receives a gift";

    private static final String BOTH_TEXT = "This person both gives and receives a gift";

    private final Map<ParticipantRole, Node> nodes = buildNodeMap();

    private final Map<ParticipantRole, String> texts = buildTextMap();

    private final boolean isLongForm;

    public RoleCell(final boolean isLongForm) {
        this.isLongForm = isLongForm;
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

    private Map<ParticipantRole, String> buildTextMap() {
        Map<ParticipantRole, String> texts = new EnumMap<>(ParticipantRole.class);

        texts.put(ParticipantRole.GIVER, GIVER_TEXT);
        texts.put(ParticipantRole.RECEIVER, RECEIVER_TEXT);
        texts.put(ParticipantRole.BOTH, BOTH_TEXT);

        return texts;
    }
}
