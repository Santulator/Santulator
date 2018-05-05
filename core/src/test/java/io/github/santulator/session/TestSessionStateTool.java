package io.github.santulator.session;

import io.github.santulator.core.CoreTool;
import io.github.santulator.model.ParticipantRole;

import java.util.List;

public final class TestSessionStateTool {
    public static final int PARTICIPANT_COUNT = buildFullParticipants().size();

    public static final String DRAW_NAME = "Secret Santa Draw";

    public static final String PASSWORD = "TopSecret";

    public static final String DIRECTORY = "TEST-DIRECTORY";

    private TestSessionStateTool() {
        // Prevent instantiation - all methods are static
    }

    public static SessionState buildFullState() {
        return buildFullState(DRAW_NAME);
    }

    public static SessionState buildFullState(final String drawName) {
        SessionState bean = buildState(drawName, buildFullParticipants());

        bean.setDirectory(DIRECTORY);

        return bean;
    }

    public static SessionState buildSimpleState() {
        return buildState(DRAW_NAME, buildSimpleParticipants());
    }

    private static SessionState buildState(final String drawName, final List<ParticipantState> participants) {
        SessionState bean = new SessionState();

        bean.setDrawName(drawName);
        bean.setParticipants(participants);
        bean.setPassword(PASSWORD);

        return bean;
    }

    private static List<ParticipantState> buildFullParticipants() {
        return CoreTool.listOf(
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
        return CoreTool.listOf(
            new ParticipantState("Albert", ParticipantRole.GIVER,    "Beryl", "Carla"),
            new ParticipantState("Beryl",  ParticipantRole.BOTH,     "David"),
            new ParticipantState("Carla",  ParticipantRole.BOTH),
            new ParticipantState("David",  ParticipantRole.RECEIVER)
        );
    }
}
