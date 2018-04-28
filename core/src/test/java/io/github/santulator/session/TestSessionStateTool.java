package io.github.santulator.session;

import io.github.santulator.core.CoreTool;
import io.github.santulator.model.ParticipantRole;

import java.util.Collections;
import java.util.List;

public final class TestSessionStateTool {
    public static final String DRAW_NAME = "Secret Santa Draw";

    public static final String PASSWORD = "TopSecret";

    private TestSessionStateTool() {
        // Prevent instantiation - all methods are static
    }

    public static SessionState buildFullState() {
        return buildFullState(DRAW_NAME);
    }

    public static SessionState buildFullState(final String drawName) {
        SessionState bean = buildState(drawName, buildParticipants());

        bean.setDirectory("TEST-DIRECTORY");

        return bean;
    }

    public static SessionState buildSimpleState() {
        return buildState(DRAW_NAME, Collections.emptyList());
    }

    private static SessionState buildState(final String drawName, final List<ParticipantState> participants) {
        SessionState bean = new SessionState();

        bean.setDrawName(drawName);
        bean.setParticipants(participants);
        bean.setPassword(PASSWORD);

        return bean;
    }

    private static List<ParticipantState> buildParticipants() {
        return CoreTool.listOf(
            new ParticipantState("Albert", ParticipantRole.GIVER, "Beryl"),
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
}
