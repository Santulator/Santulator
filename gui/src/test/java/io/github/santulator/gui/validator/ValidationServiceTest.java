/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.validator;

import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.gui.i18n.I18nManagerImpl;
import io.github.santulator.gui.model.ParticipantModel;
import io.github.santulator.gui.model.SessionModel;
import io.github.santulator.gui.services.ParticipantTableTool;
import io.github.santulator.model.ParticipantRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static io.github.santulator.model.ParticipantRole.*;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationServiceTest {
    private final I18nManager i18nManager = new I18nManagerImpl();

    private final SessionModel model = new SessionModel(i18nManager, List.of());

    private final ParticipantTableTool tool = new ParticipantTableTool(model.getParticipants());

    private final ValidationService target = new ValidationServiceImpl();

    @BeforeEach
    public void setUp() {
        tool.initialise();
    }

    @Test
    public void testOk() {
        addParticipant("Albert");
        addParticipant("Beryl");

        Optional<ValidationError> result = target.validate(model);

        assertFalse(result.isPresent());
    }

    @Test
    public void testEmpty() {
        addParticipant("");
        validate("Your draw has no participants.  You need at least two participants before you can run the draw.");
    }

    @Test
    public void testSingleParticipant() {
        addParticipant("Albert");
        validate("Your draw has only one participant.  You need at least two participants before you can run the draw.");
    }

    @Test
    public void testEmptyFirstParticipant() {
        addParticipant("");
        addParticipant("Albert");
        validate("The person on line 1 has no name.  Make sure all of the participants are named or delete those you don't need.");
    }

    @Test
    public void testEmptySecondParticipant() {
        addParticipant("Albert");
        addParticipant("");
        validate("The person on line 2 has no name.  Make sure all of the participants are named or delete those you don't need.");
    }

    @Test
    public void testDuplicateParticipant() {
        addParticipant("Albert");
        addParticipant("Beryl");
        addParticipant("Albert");
        validate("'Albert' is listed as a participant on line 1 but also on line 3.  Participant names must be unique.");
    }

    @Test
    public void testDuplicateParticipantDifferentCase() {
        addParticipant("Albert");
        addParticipant("Beryl");
        addParticipant("beryl");
        validate("'Beryl' is listed as a participant on line 2 but also on line 3.  Participant names must be unique.");
    }

    @Test
    public void testMoreGiversThanReceivers() {
        addParticipant("Albert", GIVER);
        addParticipant("Beryl", GIVER);
        addParticipant("Carla", RECEIVER);
        validate("2 participants will give a present but only 1 participant will receive a present. The number of present givers must be the same as the number of present receivers.");
    }

    @Test
    public void testMoreReceiversThanGivers() {
        addParticipant("Albert", RECEIVER);
        addParticipant("Beryl", RECEIVER);
        addParticipant("Carla", GIVER);
        validate("2 participants will receive a present but only 1 participant will give a present. The number of present givers must be the same as the number of present receivers.");
    }

    @Test
    public void testUnknownExclusion() {
        addParticipant("Albert");
        addParticipant("Beryl", "Carla");
        validate("'Beryl' on line 2 excludes 'Carla' but 'Carla' isn't listed as a participant.");
    }

    @Test
    public void testRepeatExclusion() {
        addParticipant("Albert");
        addParticipant("Beryl");
        addParticipant("Carla", "Beryl", "Beryl");
        validate("On line 3, 'Beryl' is excluded more than once.");
    }

    @Test
    public void testSelfExclusion() {
        addParticipant("Albert");
        addParticipant("Beryl", "Beryl");
        validate("On line 2, 'Beryl' is the name of the participant but is also listed as an exclusion.  A participant cannot exclude themself.");
    }

    @Test
    public void testDrawImpossible() {
        addParticipant("Albert");
        addParticipant("Beryl", "Albert");
        validate("It isn't possible to run this draw as there are too many exclusions. You will need to remove some of the restrictions.");
    }

    private void addParticipant(final String name, final String... exclusions) {
        addParticipant(name, BOTH, exclusions);
    }

    private void addParticipant(final String name, final ParticipantRole role, final String... exclusions) {
        ParticipantModel participant = tool.addRow();

        participant.setName(name);
        participant.setRole(role);
        participant.setExclusions(exclusions);
    }

    private void validate(final String expected) {
        Optional<ValidationError> result = target.validate(model);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get().getMessage(i18nManager));
    }
}