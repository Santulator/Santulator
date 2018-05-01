package io.github.santulator.gui.controller;

import io.github.santulator.gui.model.ParticipantModel;
import io.github.santulator.gui.model.SessionModel;
import io.github.santulator.model.ParticipantRole;
import io.github.santulator.session.SessionState;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.santulator.core.CoreTool.listOf;
import static io.github.santulator.session.TestSessionStateTool.*;
import static org.junit.jupiter.api.Assertions.*;

public class SessionModelToolTest {
    private static final int INDEX_ALBERT = 0;

    private static final int INDEX_BERYL = 1;

    private static final int INDEX_CARLA = 2;

    private static final int INDEX_DAVD = 3;

    private static final int INDEX_EDITH = 4;

    private static final int INDEX_FRED = 5;

    private static final int INDEX_GINA = 6;

    private static final int INDEX_HARRY = 7;

    private static final int INDEX_IRIS = 8;

    private static final int INDEX_JOHN = 9;

    private static final int INDEX_KATE = 10;

    private final SessionModelTool target = new SessionModelTool();

    private final ParticipantModel albert = new ParticipantModel("Albert", ParticipantRole.GIVER, "Beryl, Carla");

    private final ParticipantModel beryl = new ParticipantModel("Beryl", ParticipantRole.BOTH, "Albert");

    private final ParticipantModel carla = new ParticipantModel("Carla", ParticipantRole.BOTH, "David");

    private final ParticipantModel david = new ParticipantModel("David", ParticipantRole.BOTH, "Carla");

    private final ParticipantModel edith = new ParticipantModel("Edith", ParticipantRole.BOTH, "Fred");

    private final ParticipantModel fred = new ParticipantModel("Fred", ParticipantRole.BOTH, "Edith");

    private final ParticipantModel gina = new ParticipantModel("Gina", ParticipantRole.BOTH, "Harry");

    private final ParticipantModel harry = new ParticipantModel("Harry", ParticipantRole.BOTH, "Gina");

    private final ParticipantModel iris = new ParticipantModel("Iris", ParticipantRole.BOTH, "John");

    private final ParticipantModel john = new ParticipantModel("John", ParticipantRole.BOTH, "Iris");

    private final ParticipantModel kate = new ParticipantModel("Kate", ParticipantRole.RECEIVER, "");

    private final ParticipantModel placeholder = new ParticipantModel(true);

    @Test
    public void testBuildGuiModel() {
        SessionState input = buildFullState();
        SessionModel result = target.buildGuiModel(input);

        assertAll(
            () -> assertTrue(result.isChangesSaved()),
            () -> assertEquals(DRAW_NAME, result.getDrawName()),
            () -> assertEquals(PASSWORD, result.getPassword()),
            () -> assertEquals(DIRECTORY, result.getDirectory()),
            () -> {
                List<ParticipantModel> participants = result.getParticipants();

                assertAll(
                    () -> assertEquals(PARTICIPANT_COUNT + 1, participants.size()),
                    () -> validate(albert,      participants.get(INDEX_ALBERT)),
                    () -> validate(beryl,       participants.get(INDEX_BERYL)),
                    () -> validate(carla,       participants.get(INDEX_CARLA)),
                    () -> validate(david,       participants.get(INDEX_DAVD)),
                    () -> validate(edith,       participants.get(INDEX_EDITH)),
                    () -> validate(fred,        participants.get(INDEX_FRED)),
                    () -> validate(gina,        participants.get(INDEX_GINA)),
                    () -> validate(harry,       participants.get(INDEX_HARRY)),
                    () -> validate(iris,        participants.get(INDEX_IRIS)),
                    () -> validate(john,        participants.get(INDEX_JOHN)),
                    () -> validate(kate,        participants.get(INDEX_KATE)),
                    () -> validate(placeholder, participants.get(PARTICIPANT_COUNT))
                );
            }
        );
    }

    @Test
    public void testBuildFileModel() {
        List<ParticipantModel> participants = listOf(albert, beryl, carla, david, edith, fred, gina, harry, iris, john, kate);
        SessionModel input = new SessionModel(participants);

        input.setDrawName(DRAW_NAME);
        input.setPassword(PASSWORD);
        input.setDirectory(DIRECTORY);

        SessionState result = target.buildFileModel(input);

        assertEquals(buildFullState(), result);
    }

    private void validate(final ParticipantModel expected, final ParticipantModel actual) {
        assertAll(
            () -> assertEquals(expected.isPlaceholder(), actual.isPlaceholder(), "Is Placeholder"),
            () -> assertEquals(expected.getName(), actual.getName(), "Name"),
            () -> assertEquals(expected.getRole(), actual.getRole(), "Role"),
            () -> assertEquals(expected.getExclusions(), actual.getExclusions(), "Exclusions")
        );
    }
}