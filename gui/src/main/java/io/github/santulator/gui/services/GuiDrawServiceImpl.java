package io.github.santulator.gui.services;

import io.github.santulator.engine.DrawService;
import io.github.santulator.gui.model.MainModel;
import io.github.santulator.model.DrawRequirements;
import io.github.santulator.model.DrawSelection;
import io.github.santulator.session.SessionState;
import io.github.santulator.session.SessionStateTranslator;
import io.github.santulator.writer.DrawSelectionWriter;

import java.nio.file.Path;
import javax.inject.Inject;

public class GuiDrawServiceImpl implements GuiDrawService {
    private final DrawService drawService;

    private final SessionStateTranslator translator;

    private final SessionModelTool sessionModelTool;

    private final DrawSelectionWriter writer;

    private final MainModel mainModel;

    @Inject
    public GuiDrawServiceImpl(final DrawService drawService, final SessionStateTranslator translator, final SessionModelTool sessionModelTool,
        final DrawSelectionWriter writer, final MainModel mainModel) {
        this.drawService = drawService;
        this.translator = translator;
        this.sessionModelTool = sessionModelTool;
        this.writer = writer;
        this.mainModel = mainModel;
    }

    @Override
    public void draw(final Path directory) {
        SessionState state = sessionModelTool.buildFileModel(mainModel.getSessionModel());
        DrawRequirements requirements = translator.toRequirements(state);
        DrawSelection selection = drawService.draw(requirements);

        writer.writeDrawSelection(selection, directory, state.getPassword());
    }
}
