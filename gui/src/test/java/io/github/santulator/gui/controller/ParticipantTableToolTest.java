/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.ParticipantModel;
import io.github.santulator.gui.services.ParticipantTableTool;
import io.github.santulator.model.ParticipantRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ParticipantTableToolTest {
    private final List<ParticipantModel> participants = new ArrayList<>();

    @Mock
    private IntConsumer rowSelector;

    private ParticipantTableTool target;

    @BeforeEach
    public void setUp() {
        target = new ParticipantTableTool(participants, rowSelector);
    }

    @Test
    public void testPreloadedState() {
        prepare("A", "B", "C");
        validateParticipants("A", "B", "C");
    }

    @Test
    public void testAddAfterInitialState() {
        prepare("");
        triggerAction(1);
        validateParticipants("", "");
        validateSelection(1);
    }

    @Test
    public void testRemoveAfterInitialState() {
        prepare("");
        triggerAction(0);
        validateParticipants("");
        validateSelection(0);
    }

    @Test
    public void testEnterAfterInitialState() {
        prepare("");
        triggerEnter(0);
        validateParticipants("", "");
        validateSelection(1);
    }

    @Test
    public void testRemoveFirst() {
        prepare("A", "B", "C");
        triggerAction(0);
        validateParticipants("B", "C");
        validateSelection(0);
    }

    @Test
    public void testRemoveSecond() {
        prepare("A", "B", "C");
        triggerAction(1);
        validateParticipants("A", "C");
        validateSelection(1);
    }

    @Test
    public void testRemoveLast() {
        prepare("A", "B", "C");
        triggerAction(2);
        validateParticipants("A", "B");
        validateSelection(1);
    }

    @Test
    public void testOnlyRemaining() {
        prepare("A");
        triggerAction(0);
        validateParticipants("");
        validateSelection(0);
    }

    @Test
    public void testEnterOnFirst() {
        prepare("A", "B", "C");
        triggerEnter(0);
        validateParticipants("A", "B", "C");
        validateSelection(1);
    }

    @Test
    public void testEnterOnSecond() {
        prepare("A", "B", "C");
        triggerEnter(1);
        validateParticipants("A", "B", "C");
        validateSelection(2);
    }

    @Test
    public void testEnterOnLast() {
        prepare("A", "B", "C");
        triggerEnter(2);
        validateParticipants("A", "B", "C", "");
        validateSelection(3);
    }

    @Test
    public void testActionOnUnknownParticipant() {
        prepare("A", "B", "C");
        target.onActionButton(new ParticipantModel("A", ParticipantRole.GIVER));
        prepare("A", "B", "C");
        validateNoSelection();
    }

    @Test
    public void testEnterOnUnknownParticipant() {
        prepare("A", "B", "C");
        target.onEnterPress(new ParticipantModel("A", ParticipantRole.GIVER));
        prepare("A", "B", "C");
        validateNoSelection();
    }

    @Test
    public void testEnterOnPlaceholder() {
        prepare("A");
        triggerEnter(1);
        prepare("A");
        validateNoSelection();
    }

    private void triggerAction(final int index) {
        ParticipantModel participant = participants.get(index);

        target.onActionButton(participant);
    }

    private void triggerEnter(final int index) {
        ParticipantModel participant = participants.get(index);

        target.onEnterPress(participant);
    }

    private void prepare(final String... names) {
        Arrays.stream(names)
            .map(n -> new ParticipantModel(n, ParticipantRole.BOTH))
            .forEach(participants::add);
        participants.add(new ParticipantModel());
        target.initialise();
    }

    private void validateParticipants(final String... names) {
        assertAll(
            () -> assertEquals(names.length + 1, participants.size()),
            () -> assertAll(placeholderValidationStream(names)),
            () -> validatePlaceholder(names.length + 1, participants.get(participants.size() - 1))
        );
    }

    private Stream<Executable> placeholderValidationStream(final String... names) {
        return IntStream.range(0, names.length)
            .mapToObj(i -> () -> validateParticipant(i + 1, participants.get(i), names[i]));
    }

    private void validateParticipant(final int rowNumber, final ParticipantModel participant, final String name) {
        validateEntry(rowNumber, false, participant, name);
    }

    private void validatePlaceholder(final int rowNumber, final ParticipantModel participant) {
        validateEntry(rowNumber, true, participant, "");
    }

    private void validateEntry(final int rowNumber, final boolean isPlaceholder, final ParticipantModel participant, final String name) {
        assertAll(
            () -> assertEquals(rowNumber, participant.getRowNumber()),
            () -> assertEquals(isPlaceholder, participant.isPlaceholder()),
            () -> assertEquals(name, participant.getName()),
            () -> assertTrue(participant.getExclusions().isEmpty())
        );
    }

    private void validateSelection(final int i) {
        verify(rowSelector).accept(i);
    }

    private void validateNoSelection() {
        verify(rowSelector, never()).accept(anyInt());
    }
}