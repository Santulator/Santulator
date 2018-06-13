package io.github.santulator.gui.model;

import io.github.santulator.model.DrawSelection;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.nio.file.Path;

public class DrawModel {
    private final SimpleStringProperty drawName;

    private final SimpleBooleanProperty drawStarted = new SimpleBooleanProperty();

    private final SimpleObjectProperty<DrawSelection> drawSelection = new SimpleObjectProperty<>();

    private final SimpleStringProperty drawResultDescription = new SimpleStringProperty();

    private final SimpleStringProperty completedDrawDescription = new SimpleStringProperty();

    private final SimpleBooleanProperty drawFailed = new SimpleBooleanProperty();

    private final SimpleBooleanProperty drawPerformed = new SimpleBooleanProperty();

    private final SimpleObjectProperty<Path> directory = new SimpleObjectProperty<>();

    private final SimpleBooleanProperty drawSaved = new SimpleBooleanProperty();

    private final SimpleObjectProperty<DrawWizardPage> drawWizardPage = new SimpleObjectProperty<>(DrawWizardPage.RUN_DRAW);

    private final SimpleBooleanProperty blockNext = new SimpleBooleanProperty();

    private final SimpleStringProperty savedDrawDescription = new SimpleStringProperty();

    private final SimpleStringProperty completedSaveDescription = new SimpleStringProperty();

    private final SimpleBooleanProperty resultsDirectoryOpened = new SimpleBooleanProperty();

    public DrawModel(final String drawName) {
        this.drawName = new SimpleStringProperty(drawName);
    }

    public SimpleStringProperty drawNameProperty() {
        return drawName;
    }

    public SimpleBooleanProperty drawStartedProperty() {
        return drawStarted;
    }

    public void setDrawStarted(final boolean drawStarted) {
        this.drawStarted.set(drawStarted);
    }

    public void setDrawSelection(final DrawSelection drawSelection) {
        this.drawSelection.set(drawSelection);
    }

    public DrawSelection getDrawSelection() {
        return drawSelection.get();
    }

    public SimpleBooleanProperty drawPerformedProperty() {
        return drawPerformed;
    }

    public void setDrawPerformed(final boolean drawPerformed) {
        this.drawPerformed.set(drawPerformed);
    }

    public void setDirectory(final Path directory) {
        this.directory.set(directory);
    }

    public Path getDirectory() {
        return directory.get();
    }

    public SimpleBooleanProperty drawSavedProperty() {
        return drawSaved;
    }

    public void setDrawSaved(final boolean drawSaved) {
        this.drawSaved.set(drawSaved);
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

    public void setDrawResultDescription(final String drawResultDescription) {
        this.drawResultDescription.set(drawResultDescription);
    }

    public SimpleStringProperty drawResultDescriptionProperty() {
        return drawResultDescription;
    }

    public SimpleStringProperty completedDrawDescriptionProperty() {
        return completedDrawDescription;
    }

    public String getCompletedDrawDescription() {
        return completedDrawDescription.get();
    }

    public void setDrawFailed(final boolean drawFailed) {
        this.drawFailed.set(drawFailed);
    }

    public SimpleBooleanProperty drawFailedProperty() {
        return drawFailed;
    }

    public void setSavedDrawDescription(final String savedDrawDescription) {
        this.savedDrawDescription.set(savedDrawDescription);
    }

    public SimpleStringProperty savedDrawDescriptionProperty() {
        return savedDrawDescription;
    }

    public SimpleStringProperty completedSaveDescriptionProperty() {
        return completedSaveDescription;
    }

    public String getCompletedSaveDescription() {
        return completedSaveDescription.get();
    }

    public SimpleBooleanProperty resultsDirectoryOpenedProperty() {
        return resultsDirectoryOpened;
    }

    public void setResultsDirectoryOpened(final boolean resultsDirectoryOpened) {
        this.resultsDirectoryOpened.set(resultsDirectoryOpened);
    }
}
