/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.services;

import io.github.santulator.core.CsvTool;
import io.github.santulator.gui.model.ParticipantModel;
import io.github.santulator.gui.model.SessionModel;
import io.github.santulator.session.ParticipantState;
import io.github.santulator.session.SessionState;

import java.util.List;
import javax.inject.Singleton;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Singleton
public class SessionModelTool {
    public SessionModel buildGuiModel(final SessionState state) {
        List<ParticipantModel> participants = state.getParticipants().stream()
            .map(this::buildParticipantModel)
            .collect(toList());
        SessionModel model = new SessionModel(participants);

        model.setDrawName(state.getDrawName());
        model.setPassword(state.getPassword());
        model.setDirectory(state.getDirectory());

        return model;
    }

    private ParticipantModel buildParticipantModel(final ParticipantState state) {
        String exclusions = state.getExclusions().stream()
            .collect(joining(", "));
        return new ParticipantModel(state.getName(), state.getRole(), exclusions);
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
        state.setDirectory(model.getDirectory());

        return state;
    }

    public ParticipantState buildParticipantState(final ParticipantModel model) {
        return new ParticipantState(model.getName(), model.getRole(), CsvTool.splitToList(model.getExclusions()));
    }
}
