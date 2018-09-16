package io.github.santulator.gui.services;

import io.github.santulator.gui.model.ParticipantModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class ParticipantTableTool {
    private static final Logger LOG = LoggerFactory.getLogger(ParticipantTableTool.class);

    private final List<ParticipantModel> participants;

    private final IntConsumer rowSelector;

    public ParticipantTableTool(final List<ParticipantModel> participants) {
        this(participants, ParticipantTableTool::logSelection);
    }

    public ParticipantTableTool(final List<ParticipantModel> participants, final IntConsumer rowSelector) {
        this.participants = participants;
        this.rowSelector = rowSelector;
    }

    public void initialise() {
        renumberFrom(0);
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

        int selection;

        if (index == 0) {
            selection = 0;
        } else if (isLastParticipant) {
            selection = index - 1;
        } else {
            selection = index;
        }
        renumberFrom(selection);
        rowSelector.accept(selection);
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

    public ParticipantModel addRow() {
        int index = participants.size() - 1;
        ParticipantModel row = participants.get(index);

        row.setPlaceholder(false);
        rowSelector.accept(index);
        participants.add(new ParticipantModel());
        renumberFrom(index + 1);

        return row;
    }

    private void renumberFrom(final int startIndex) {
        IntStream.range(startIndex, participants.size())
            .forEach(i -> participants.get(i).setRowNumber(i + 1));
    }

    private static void logSelection(final int index) {
        LOG.debug("Row selected: {}", index);
    }
}
