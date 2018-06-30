package io.github.santulator.gui.model;

import io.github.santulator.model.ParticipantRole;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

import java.util.Collections;
import java.util.List;

import static io.github.santulator.core.CoreTool.listOf;

public class ParticipantModel {
    private static final ParticipantRole DEFAULT_ROLE = ParticipantRole.BOTH;

    public static final Callback<ParticipantModel, Observable[]> PROPERTY_EXTRACTOR
        = m -> new Observable[] {m.name, m.role, m.exclusions, m.isPlaceholder};

    private final SimpleStringProperty name;

    private final SimpleObjectProperty<ParticipantRole> role;

    private final ObservableList<String> exclusions;

    private final SimpleBooleanProperty isPlaceholder;

    public ParticipantModel(final String name, final ParticipantRole role, final String... exclusions) {
        this(name, role, listOf(exclusions));
    }

    public ParticipantModel(final String name, final ParticipantRole role, final List<String> exclusions) {
        this(false, name, role, exclusions);
    }

    public ParticipantModel() {
        this(true);
    }

    public ParticipantModel(final boolean isPlaceholder) {
        this(isPlaceholder, "", DEFAULT_ROLE, Collections.emptyList());
    }

    private ParticipantModel(final boolean isPlaceholder, final String name, final ParticipantRole role, final List<String> exclusions) {
        this.isPlaceholder = new SimpleBooleanProperty(isPlaceholder);
        this.name = new SimpleStringProperty(name);
        this.role = new SimpleObjectProperty<>(role);
        this.exclusions = FXCollections.observableArrayList(exclusions);
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

    public List<String> getExclusions() {
        return exclusions;
    }

    public boolean isPlaceholder() {
        return isPlaceholder.get();
    }

    public void setPlaceholder(final boolean isPlaceholder) {
        this.isPlaceholder.set(isPlaceholder);
    }

    public void clear() {
        name.set("");
        role.set(DEFAULT_ROLE);
        exclusions.clear();
    }
}
