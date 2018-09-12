package io.github.santulator.gui.validator;

import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.gui.i18n.I18nManagerImpl;
import io.github.santulator.gui.model.ParticipantModel;
import io.github.santulator.gui.model.SessionModel;
import io.github.santulator.model.ParticipantRole;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationServiceTest {
    private final I18nManager i18nManager = new I18nManagerImpl();

    private final SessionModel model = new SessionModel(i18nManager, Collections.emptyList());

    private final ValidationService target = new ValidationServiceImpl();

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

    private void addParticipant(final String name) {
        List<ParticipantModel> participants = model.getParticipants();

        participants.add(participants.size() - 1, new ParticipantModel(name, ParticipantRole.BOTH));
    }

    private void validate(final String expected) {
        Optional<ValidationError> result = target.validate(model);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get().getMessage(i18nManager));
    }
}