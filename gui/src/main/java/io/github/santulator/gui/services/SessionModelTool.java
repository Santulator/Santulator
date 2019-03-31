/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.services;

import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.gui.model.ParticipantModel;
import io.github.santulator.gui.model.SessionModel;
import io.github.santulator.model.ParticipantState;
import io.github.santulator.model.SessionState;

import java.nio.file.Path;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

import static java.util.stream.Collectors.toList;

@Singleton
public class SessionModelTool {
    private final I18nManager i18nManager;

    @Inject
    public SessionModelTool(final I18nManager i18nManager) {
        this.i18nManager = i18nManager;
    }

    public SessionModel buildGuiModel(final SessionState state, final Path file) {
        SessionModel model = buildGuiModel(state.getParticipants());

        model.setDrawName(state.getDrawName());
        model.setPassword(state.getPassword());
        model.setSessionFile(file);

        return model;
    }

    public SessionModel buildGuiModel(final List<ParticipantState> participants) {
        List<ParticipantModel> participantModels = participants.stream()
            .map(this::buildParticipantModel)
            .collect(toList());

        return new SessionModel(i18nManager, participantModels);
    }

    private ParticipantModel buildParticipantModel(final ParticipantState state) {
        return new ParticipantModel(state.getName(), state.getRole(), state.getExclusions());
    }

    public SessionState buildFileModel(final SessionModel model) {
        SessionState state = new SessionState();
        List<ParticipantState> participants = model.getParticipants().stream()
            .filter(p -> !p.isPlaceholder())
            .map(this::buildParticipantState)
            .collect(toList());

        state.setParticipants(participants);
        state.setDrawName(model.getDrawName());
        state.setPassword(model.getPassword());

        return state;
    }

    private ParticipantState buildParticipantState(final ParticipantModel model) {
        return new ParticipantState(model.getName(), model.getRole(), model.getExclusions());
    }
}
