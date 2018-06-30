package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.ParticipantModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.IntConsumer;

public class ParticipantTableTool {
    private static final Logger LOG = LoggerFactory.getLogger(ParticipantTableTool.class);

    private final List<ParticipantModel> participants;

    private final IntConsumer rowSelector;

    public ParticipantTableTool(final List<ParticipantModel> participants, final IntConsumer rowSelector) {
        this.participants = participants;
        this.rowSelector = rowSelector;
    }

    public void onActionButton(final ParticipantModel participant) {
        int index = participants.indexOf(participant);

        if (index == -1) {
            LOG.error("Action requested for unknown participant");
        } else if (participant.isPlaceholder()) {
            addRow();
        } else {
            remove(index);
        }
    }

    private void remove(final int index) {
        boolean isLastParticipant = index == participants.size() - 2;

        if (isLastParticipant && index == 0) {
            participants.get(index).clear();
        } else {
            participants.remove(index);
        }
        if (index == 0) {
            rowSelector.accept(0);
        } else if (isLastParticipant) {
            rowSelector.accept(index - 1);
        } else {
            rowSelector.accept(index);
        }
    }

    public void onEnterPress(final ParticipantModel participant) {
        int index = participants.indexOf(participant);

        if (index == -1 || index == participants.size() - 1) {
            LOG.error("Enter pressed on unknown participant");
        } else if (index == participants.size() - 2) {
            addRow();
        } else {
            rowSelector.accept(index + 1);
        }
    }

    private void addRow() {
        int index = participants.size() - 1;

        participants.get(index).setPlaceholder(false);
        rowSelector.accept(index);
        participants.add(new ParticipantModel());
    }
}
