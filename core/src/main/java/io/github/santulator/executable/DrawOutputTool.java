/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.executable;

import io.github.santulator.model.DrawRequirements;
import io.github.santulator.model.DrawSelection;
import io.github.santulator.model.GiverAssignment;
import io.github.santulator.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class DrawOutputTool {
    private static final boolean REVEAL_NUMBERS = false;

    private static final Logger LOG = LoggerFactory.getLogger(DrawOutputTool.class);

    private final List<Person> participants = new ArrayList<>();

    public DrawOutputTool(final DrawRequirements requirements) {
        participants.addAll(requirements.getParticipants());
        Collections.shuffle(participants);
        if (REVEAL_NUMBERS) {
            revealNumbers();
        }
    }

    private void revealNumbers() {
        IntStream.range(0, participants.size())
            .forEach(this::revealNumber);
    }

    private void revealNumber(final int i) {
        Person participant = participants.get(i);
        int displayNumber = i + 1;

        LOG.info("{} = {}", displayNumber, participant.getName());
    }

    public void showSelection(final DrawSelection selection) {
        selection.getGivers().forEach(this::showAssignment);
    }

    private void showAssignment(final GiverAssignment assignment) {
        String from = assignment.getFrom().getName();
        int to = participants.indexOf(assignment.getTo()) + 1;

        LOG.info("{} -> {}", from, to);
    }
}
