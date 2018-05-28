package io.github.santulator.gui.model;

import io.github.santulator.model.DrawSelection;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.nio.file.Path;

public class DrawModel {
    private final SimpleStringProperty drawName;

    private final SimpleObjectProperty<DrawSelection> drawSelection = new SimpleObjectProperty<>();

    private final SimpleBooleanProperty drawPerformed = new SimpleBooleanProperty();

    private final SimpleObjectProperty<Path> directory = new SimpleObjectProperty<>();

    private final SimpleBooleanProperty drawSaved = new SimpleBooleanProperty();

    private final SimpleObjectProperty<DrawWizardPage> drawWizardPage = new SimpleObjectProperty<>(DrawWizardPage.RUN_DRAW);

    private final SimpleBooleanProperty blockNext = new SimpleBooleanProperty();

    public DrawModel(final String drawName) {
        this.drawName = new SimpleStringProperty(drawName);
    }

    public SimpleStringProperty drawNameProperty() {
        return drawName;
    }

    public void setDrawSelection(final DrawSelection drawSelection) {
        this.drawSelection.set(drawSelection);
    }

    public DrawSelection getDrawSelection() {
        return drawSelection.get();
    }

    public SimpleObjectProperty<DrawSelection> drawSelectionProperty() {
        return drawSelection;
    }

    public SimpleBooleanProperty drawPerformedProperty() {
        return drawPerformed;
    }

    public boolean isDrawPerformed() {
        return drawPerformed.get();
    }

    public void setDirectory(final Path directory) {
        this.directory.set(directory);
    }

    public SimpleObjectProperty<Path> directoryProperty() {
        return directory;
    }

    public SimpleBooleanProperty drawSavedProperty() {
        return drawSaved;
    }

    public boolean isDrawSaved() {
        return drawSaved.get();
    }

    public void setDrawWizardPage(final DrawWizardPage drawWizardPage) {
        this.drawWizardPage.set(drawWizardPage);
    }

    public SimpleObjectProperty<DrawWizardPage> drawWizardPageProperty() {
        return drawWizardPage;
    }

    public SimpleBooleanProperty blockNextProperty() {
        return blockNext;
    }

    public boolean isBlockNext() {
        return blockNext.get();
    }
}
