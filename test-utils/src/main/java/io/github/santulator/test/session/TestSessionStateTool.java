package io.github.santulator.test.session;

import io.github.santulator.model.ParticipantRole;
import io.github.santulator.session.ParticipantState;
import io.github.santulator.session.SessionState;

import java.util.List;

public final class TestSessionStateTool {
    public static final int PARTICIPANT_COUNT = buildFullParticipants().size();

    public static final String DRAW_NAME_1 = "Secret Santa Draw";

    public static final String DRAW_NAME_2 = "New Draw Name";

    public static final String PASSWORD = "TopSecret";

    private TestSessionStateTool() {
        // Prevent instantiation - all methods are static
    }

    public static SessionState buildFullState() {
        return buildFullState(DRAW_NAME_1);
    }

    public static SessionState buildFullState(final String drawName) {
        return buildState(drawName, buildFullParticipants());
    }

    public static SessionState buildSimpleState() {
        return buildSimpleState(DRAW_NAME_1);
    }

    public static SessionState buildSimpleState(final String drawName) {
        return buildState(drawName, buildSimpleParticipants());
    }

    private static SessionState buildState(final String drawName, final List<ParticipantState> participants) {
        SessionState bean = new SessionState();

        bean.setDrawName(drawName);
        bean.setParticipants(participants);
        bean.setPassword(PASSWORD);

        return bean;
    }

    private static List<ParticipantState> buildFullParticipants() {
        return List.of(
            new ParticipantState("Albert", ParticipantRole.GIVER, "Beryl", "Carla"),
            new ParticipantState("Beryl",  ParticipantRole.BOTH,  "Albert"),
            new ParticipantState("Carla",  ParticipantRole.BOTH,  "David"),
            new ParticipantState("David",  ParticipantRole.BOTH,  "Carla"),
            new ParticipantState("Edith",  ParticipantRole.BOTH,  "Fred"),
            new ParticipantState("Fred",   ParticipantRole.BOTH,  "Edith"),
            new ParticipantState("Gina",   ParticipantRole.BOTH,  "Harry"),
            new ParticipantState("Harry",  ParticipantRole.BOTH,  "Gina"),
            new ParticipantState("Iris",   ParticipantRole.BOTH,  "John"),
            new ParticipantState("John",   ParticipantRole.BOTH,  "Iris"),
            new ParticipantState("Kate",   ParticipantRole.RECEIVER)
        );
    }

    private static List<ParticipantState> buildSimpleParticipants() {
        return List.of(
            new ParticipantState("Albert", ParticipantRole.GIVER,    "Beryl", "Carla"),
            new ParticipantState("Beryl",  ParticipantRole.BOTH,     "David"),
            new ParticipantState("Carla",  ParticipantRole.BOTH),
            new ParticipantState("David",  ParticipantRole.RECEIVER)
        );
    }
}
