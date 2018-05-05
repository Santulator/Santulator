package io.github.santulator.gui.model;

import io.github.santulator.model.ParticipantRole;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Callback;

public class ParticipantModel {
    public static final Callback<ParticipantModel, Observable[]> PROPERTY_EXTRACTOR
        = m -> new Observable[] {m.name, m.role, m.exclusions, m.isPlaceholder};

    private final SimpleStringProperty name;

    private final SimpleObjectProperty<ParticipantRole> role;

    private final SimpleStringProperty exclusions;

    private final SimpleBooleanProperty isPlaceholder;

    public ParticipantModel(final String name, final ParticipantRole role, final String exclusions) {
        this(false, name, role, exclusions);
    }

    public ParticipantModel() {
        this(true);
    }

    public ParticipantModel(final boolean isPlaceholder) {
        this(isPlaceholder, "", ParticipantRole.BOTH, "");
    }

    private ParticipantModel(final boolean isPlaceholder, final String name, final ParticipantRole role, final String exclusions) {
        this.isPlaceholder = new SimpleBooleanProperty(isPlaceholder);
        this.name = new SimpleStringProperty(name);
        this.role = new SimpleObjectProperty<>(role);
        this.exclusions = new SimpleStringProperty(exclusions);
    }

    public String getName() {
        return name.get();
    }

    public void setName(final String name) {
        this.name.set(name);
    }

    public ParticipantRole getRole() {
        return role.get();
    }

    public void setRole(final ParticipantRole role) {
        this.role.set(role);
    }

    public String getExclusions() {
        return exclusions.get();
    }

    public void setExclusions(final String exclusions) {
        this.exclusions.set(exclusions);
    }

    public boolean isPlaceholder() {
        return isPlaceholder.get();
    }

    public void setPlaceholder(final boolean isPlaceholder) {
        this.isPlaceholder.set(isPlaceholder);
    }
}
