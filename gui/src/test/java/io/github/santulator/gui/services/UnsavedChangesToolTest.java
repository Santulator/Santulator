package io.github.santulator.gui.services;

import io.github.santulator.gui.model.ParticipantModel;
import io.github.santulator.gui.model.SessionModel;
import io.github.santulator.model.ParticipantRole;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnsavedChangesToolTest {
    private final ParticipantModel participant = new ParticipantModel();

    @Test
    public void testEmpty() {
        SessionModel model = createEmptyBoundModel();

        assertTrue(model.isChangesSaved());
    }

    @Test
    public void testUpdateDrawName() {
        SessionModel model = createEmptyBoundModel();

        model.setDrawName("Updated");

        assertFalse(model.isChangesSaved());
    }

    @Test
    public void testUpdatePassword() {
        SessionModel model = createEmptyBoundModel();

        model.setPassword("New Password");

        assertFalse(model.isChangesSaved());
    }

    @Test
    public void testAddParticipant() {
        SessionModel model = createEmptyBoundModel();

        model.getParticipants().add(participant);

        assertFalse(model.isChangesSaved());
    }

    @Test
    public void testUnchanged() {
        SessionModel model = createBoundModelWithParticipant();

        assertTrue(model.isChangesSaved());
    }

    @Test
    public void testSetParticipantName() {
        SessionModel model = createBoundModelWithParticipant();

        participant.setName("New Name");

        assertFalse(model.isChangesSaved());
    }

    @Test
    public void testSetParticipantRole() {
        SessionModel model = createBoundModelWithParticipant();

        participant.setRole(ParticipantRole.GIVER);

        assertFalse(model.isChangesSaved());
    }

    @Test
    public void testSetParticipantExclusions() {
        SessionModel model = createBoundModelWithParticipant();

        participant.setExclusions("New Exclusions");

        assertFalse(model.isChangesSaved());
    }

    @Test
    public void testSetParticipantPlaceholderStatus() {
        SessionModel model = createBoundModelWithParticipant();

        participant.setPlaceholder(false);

        assertFalse(model.isChangesSaved());
    }

    private SessionModel createEmptyBoundModel() {
        SessionModel model = new SessionModel();

        UnsavedChangesTool.createBindings(model);

        return model;
    }

    private SessionModel createBoundModelWithParticipant() {
        SessionModel model = new SessionModel(singletonList(participant));

        UnsavedChangesTool.createBindings(model);

        return model;
    }
}