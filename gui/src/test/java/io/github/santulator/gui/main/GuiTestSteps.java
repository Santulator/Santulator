/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.main;

import io.github.santulator.core.SantaException;
import io.github.santulator.gui.dialogues.FileDialogueType;
import io.github.santulator.gui.dialogues.FileFormatType;
import io.github.santulator.gui.i18n.I18nKey;
import io.github.santulator.test.core.TestFileManager;
import javafx.application.Platform;
import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.Node;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.api.FxRobot;
import org.testfx.service.query.NodeQuery;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static io.github.santulator.gui.view.ParticipantCell.*;
import static io.github.santulator.test.session.TestSessionStateTool.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.ListViewMatchers.hasItems;
import static org.testfx.util.NodeQueryUtils.hasText;
import static org.testfx.util.WaitForAsyncUtils.waitFor;

public class GuiTestSteps {
    private static final Logger LOG = LoggerFactory.getLogger(GuiTestSteps.class);

    private static final int BRIEF_PAUSE_MILLIS = 100;

    private final FxRobot robot;

    private final GuiTestValidator validator;

    private final Path sessionFile;

    private final Path drawDirectory;

    private int stepNo;

    public GuiTestSteps(final FxRobot robot, final GuiTestValidator validator, final TestFileManager manager) {
        this.robot = robot;
        this.validator = validator;

        sessionFile = manager.addFile(GuiTestConstants.SESSION_1);
        drawDirectory = manager.addFile("draw");
    }

    public void part1SetupDraw() {
        step("Open application", () -> {
            verifyThat("#mainBorderPane", isVisible());
        });

        step("Enter draw name", () -> {
            clearField("#fieldDrawName");
            robot.clickOn("#fieldDrawName").write(DRAW_NAME_1);
            verifyThat("#fieldDrawName", hasText(DRAW_NAME_1));
        });

        step("Enter password", () -> {
            clearField("#fieldPassword");
            robot.clickOn("#fieldPassword").write(PASSWORD);
            verifyThat("#fieldPassword", hasText(PASSWORD));
        });

        step("Add Albert", () -> {
            robot.clickOn(participantNode(CLASS_FIELD_NAME, 0)).write("Albert");
            robot.clickOn(participantNode(CLASS_CHOICE_ROLE, 0)).clickOn("This person only gives a gift");
            robot.clickOn(participantNode(CLASS_FIELD_EXCLUSIONS, 0, 0)).write("Beryl");
            robot.clickOn(participantNode(CLASS_FIELD_EXCLUSIONS, 0, 1)).write("Carla");
            verifyThat("#listParticipants", hasItems(2));
        });

        step("Add Beryl", () -> {
            robot.clickOn(participantNode(CLASS_BUTTON_ACTION, 1));
            robot.clickOn(participantNode(CLASS_FIELD_NAME, 1)).write("Beryl");
            robot.clickOn(participantNode(CLASS_FIELD_EXCLUSIONS, 1, 0)).write("David");
            verifyThat("#listParticipants", hasItems(3));
        });

        step("Add Carla (after pressing enter on exclusion field)", () -> {
            robot.type(KeyCode.ENTER);
            robot.write("Carla");
            verifyThat("#listParticipants", hasItems(4));
        });

        step("Add David (after pressing enter on name field)", () -> {
            robot.type(KeyCode.ENTER);
            robot.write("David");
            robot.clickOn(participantNode(CLASS_CHOICE_ROLE, 3)).clickOn("This person only receives a gift");
            verifyThat("#listParticipants", hasItems(5));
        });

        step("Save the session", () -> {
            validator.setUpFileDialogue(FileDialogueType.SAVE_SESSION, FileFormatType.SESSION, sessionFile);
            robot.clickOn("#buttonSave");
            validator.validateSavedSession(sessionFile, buildSimpleState());
        });
    }

    public void part2ChangeSessions() {
        step("Start new session", () -> {
            robot.clickOn("#buttonNew");
            verifyThat("#listParticipants", hasItems(2));
            verifyThat("#fieldDrawName", hasText("My Secret Santa Draw"));
            verifyThat("#fieldPassword", hasText("christmas"));
        });

        step("Import from spreadsheet", () -> {
            validator.setUpFileDialogue(FileDialogueType.IMPORT_SESSION, FileFormatType.SPREADSHEET, GuiTestConstants.SPREADSHEET);
            robot.clickOn("#buttonImport");
            verifyThat("#listParticipants", hasItems(5));
            verifyThat(participantNodeQuery(CLASS_FIELD_NAME, 0), hasText("Abigail"));
            verifyThat(participantNodeQuery(CLASS_FIELD_NAME, 1), hasText("Bill"));
            verifyThat(participantNodeQuery(CLASS_FIELD_NAME, 2), hasText("Carol"));
            verifyThat(participantNodeQuery(CLASS_FIELD_NAME, 3), hasText("Dean"));
        });
    }

    public void part3RunDraw() {
        step("Open the saved session", () -> {
            validator.setUpFileDialogue(FileDialogueType.OPEN_SESSION, FileFormatType.SESSION, sessionFile);
            robot.clickOn("#buttonOpen");
            verifyThat("#fieldDrawName", hasText(DRAW_NAME_1));
            verifyThat("#fieldPassword", hasText(PASSWORD));
            verifyThat("#listParticipants", hasItems(5));
        });

        step("Open the draw wizard", () -> {
            robot.clickOn("#buttonRunDraw");
            verifyThat("#drawWizardPage1", isVisible());
        });

        step("Run the draw", () -> {
            robot.clickOn("#buttonDraw1RunDraw");
            waitUntilEnabled("Next");
            verifyThat("#labelDraw1Result", hasText("Draw complete, 3 gifts will be given"));
        });

        step("Move to wizard step 2", () -> {
            robot.clickOn("Next");
            verifyThat("#drawWizardPage2", isVisible());
        });

        step("Save the draw results", () -> {
            validator.setUpFileDialogue(FileDialogueType.RUN_DRAW, FileFormatType.DRAW, drawDirectory);
            robot.clickOn("#buttonDraw2SaveResults");
            waitUntilEnabled("Next");
            verifyThat("#labelDraw2SavedDescription", hasText("Results saved in directory 'draw'"));
            validator.validateDraw(drawDirectory, "Albert.pdf", "Beryl.pdf", "Carla.pdf");
        });

        step("Move to wizard step 3", () -> {
            robot.clickOn("Next");
            verifyThat("#drawWizardPage3", isVisible());
        });

        step("Open the results directory", () -> {
            robot.clickOn("#buttonDraw3Open");
            validator.validateOpenPath(drawDirectory);
        });

        step("Close the draw wizard", () -> {
            robot.clickOn("Finish ");
        });
    }

    public void part4AboutDialogue() {
        step("Open About dialogue", () -> {
            robot.clickOn("#menuHelp");
            robot.clickOn("#menuAbout");
            verifyThat("#aboutDialogue", isVisible());
        });

        step("Open website from About dialogue", () -> {
            robot.clickOn("#linkWebsite");
            validator.validateWebPage(I18nKey.LINK_MAIN);
        });

        step("Close About dialogue", () -> {
            robot.clickOn("#buttonClose");
        });
    }

    public void part5WebLinks() {
        step("Open website", () -> {
            robot.clickOn("#menuHelp");
            robot.clickOn("#menuWebsite");
            validator.validateWebPage(I18nKey.LINK_MAIN);
        });

        step("Open Santulator How To", () -> {
            robot.clickOn("#menuHelp");
            robot.clickOn("#menuHowTo");
            validator.validateWebPage(I18nKey.LINK_HELP);
        });

        step("Open Issue Reporting", () -> {
            robot.clickOn("#menuHelp");
            robot.clickOn("#menuIssue");
            validator.validateWebPage(I18nKey.LINK_ISSUE);
        });
    }

    public void part6Exit() {
        step("Reopen the saved session", () -> {
            validator.setUpFileDialogue(FileDialogueType.OPEN_SESSION, FileFormatType.SESSION, sessionFile);
            robot.clickOn("#buttonOpen");
            verifyThat("#fieldDrawName", hasText(DRAW_NAME_1));
        });

        step("Update draw name", () -> {
            clearField("#fieldDrawName");
            robot.clickOn("#fieldDrawName").write(DRAW_NAME_2);
            verifyThat("#fieldDrawName", hasText(DRAW_NAME_2));
        });

        step("Cancel exit", () -> {
            robot.clickOn("#menuFile");
            robot.clickOn("#menuExit");
            robot.clickOn(lookup("#unsavedChanges", "Cancel"));
            verifyThat("#fieldDrawName", hasText(DRAW_NAME_2));
        });

        step("Exit", () -> {
            validator.setUpFileDialogue(FileDialogueType.SAVE_SESSION, FileFormatType.SESSION, sessionFile);
            robot.clickOn("#menuFile");
            robot.clickOn("#menuExit");
            robot.clickOn(lookup("#unsavedChanges", "Save"));
            validator.validateSavedSession(sessionFile, buildSimpleState(DRAW_NAME_2));
        });
    }

    private void step(final String step, final Runnable runnable) {
        ++stepNo;
        LOG.info("STEP {}: Begin - {}", stepNo, step);
        runnable.run();
        LOG.info("STEP {}:   End - {}", stepNo, step);
    }

    private Node participantNode(final String style, final int row, final int column) {
        return participantNode(style + "_" + column, row);
    }

    private Node participantNode(final String style, final int row) {
        return participantNodeQuery(style, row).query();
    }

    private NodeQuery participantNodeQuery(final String style, final int row) {
        return robot.lookup("." + style).nth(row);
    }

    private void waitUntilEnabled(final String query) {
        ObservableBooleanValue property = robot.lookup(query).query().disableProperty().not();

        waitForProperty(property, query);
        briefPause();
    }

    private void clearField(final String query) {
        TextInputControl control = robot.lookup(query).queryTextInputControl();
        ObservableBooleanValue property = control.textProperty().isEmpty();

        if (!property.get()) {
            Platform.runLater(control::clear);
            waitForProperty(property, query);
            briefPause();
        }
    }

    private void waitForProperty(final ObservableBooleanValue property, final String name) {
        try {
            waitFor(10, TimeUnit.SECONDS, property);
        } catch (TimeoutException e) {
            throw new SantaException(String.format("Timeout waiting for '%s'", name), e);
        }
    }

    private void briefPause() {
        try {
            Thread.sleep(BRIEF_PAUSE_MILLIS);
        } catch (final InterruptedException e) {
            LOG.debug("Thread woken unexpectedly", e);
            Thread.currentThread().interrupt();
        }
    }

    private Node lookup(final String first, final String... queries) {
        NodeQuery nodeQuery = robot.lookup(first);

        for (String query : queries) {
            nodeQuery = nodeQuery.lookup(query);
        }

        return nodeQuery.query();
    }
}
