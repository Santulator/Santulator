package io.github.santulator.session;

import io.github.santulator.core.CoreTool;
import io.github.santulator.model.ParticipantRole;

import java.util.List;

public final class TestSessionStateTool {
    private TestSessionStateTool() {
        // Prevent instantiation - all methods are static
    }

    public static SessionState buildState() {
        return buildState("Test State");
    }

    public static SessionState buildState(final String drawName) {
        SessionState bean = new SessionState();

        bean.setDrawName(drawName);
        bean.setFormatVersion(SessionFormatVersion.FORMAT_1);
        bean.setParticipants(buildParticipants());
        bean.setDirectory("TEST-DIRECTORY");
        bean.setPassword("TOP-SECRET");

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
