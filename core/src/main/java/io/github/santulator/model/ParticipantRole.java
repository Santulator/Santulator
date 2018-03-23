package io.github.santulator.model;

public enum ParticipantRole {
    GIVER(true, false), RECEIVER(false, true), BOTH(true, true);

    private final boolean isGiver;

    private final boolean isReceiver;

    ParticipantRole(final boolean isGiver, final boolean isReceiver) {
        this.isGiver = isGiver;
        this.isReceiver = isReceiver;
    }

    public boolean isGiver() {
        return isGiver;
    }

    public boolean isReceiver() {
        return isReceiver;
    }
}
