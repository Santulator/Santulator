/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.main;

import io.github.santulator.gui.dialogues.FileDialogueType;
import io.github.santulator.gui.dialogues.FileFormatType;
import io.github.santulator.gui.model.SessionModel;
import io.github.santulator.test.TestFileManager;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.TextInputControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.api.FxRobot;
import org.testfx.service.query.NodeQuery;

import java.nio.file.Path;

import static io.github.santulator.gui.common.GuiConstants.*;
import static io.github.santulator.gui.view.ParticipantCell.*;
import static io.github.santulator.session.TestSessionStateTool.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ListViewMatchers.hasItems;
import static org.testfx.util.NodeQueryUtils.*;

public class GuiTestSteps {
    private static final Logger LOG = LoggerFactory.getLogger(GuiTestSteps.class);

    private final FxRobot robot;

    private final GuiTestValidator validator;

    private final Path sessionFile;

    private final Path drawDirectory;

    private int stepNo;

    public GuiTestSteps(final FxRobot robot, final GuiTestValidator validator, final TestFileManager manager) {
        this.robot = robot;
        this.validator = validator;

        sessionFile = manager.addFile("session.santa");
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
            robot.clickOn(participantNode(CLASS_CHOICE_ROLE, 0)).clickOn("GIVER");
            robot.clickOn(participantNode(CLASS_FIELD_EXCLUSIONS, 0)).write("Beryl, Carla");
            verifyThat("#listParticipants", hasItems(2));
        });

        step("Add Beryl", () -> {
            robot.clickOn(participantNode(CLASS_BUTTON_ACTION, 1));
            robot.clickOn(participantNode(CLASS_FIELD_NAME, 1)).write("Beryl");
            robot.clickOn(participantNode(CLASS_FIELD_EXCLUSIONS, 1)).write("David");
            verifyThat("#listParticipants", hasItems(3));
        });

        step("Add Carla", () -> {
            robot.clickOn(participantNode(CLASS_BUTTON_ACTION, 2));
            robot.clickOn(participantNode(CLASS_FIELD_NAME, 2)).write("Carla");
            verifyThat("#listParticipants", hasItems(4));
        });

        step("Add David", () -> {
            robot.clickOn(participantNode(CLASS_BUTTON_ACTION, 3));
            robot.clickOn(participantNode(CLASS_FIELD_NAME, 3)).write("David");
            robot.clickOn(participantNode(CLASS_CHOICE_ROLE, 3)).clickOn("RECEIVER");
            verifyThat("#listParticipants", hasItems(5));
        });

        step("Save the session", () -> {
            validator.setUpFileDialogue(FileDialogueType.SAVE_SESSION, FileFormatType.SESSION, sessionFile);
            robot.clickOn("#buttonSave");
            validator.validateSavedSession(sessionFile, buildSimpleState());
        });

        step("Start new session", () -> {
            robot.clickOn("#buttonNew");
            verifyThat("#fieldDrawName", hasText(SessionModel.DEFAULT_DRAW_NAME));
            verifyThat("#fieldPassword", hasText(SessionModel.DEFAULT_PASSWORD));
        });
    }

    public void part2StartNewSession() {
        step("Start new session", () -> {
            robot.clickOn("#buttonNew");
            verifyThat("#listParticipants", hasItems(2));
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
        });

        step("Move to wizard step 2", () -> {
            robot.clickOn("Next");
            verifyThat("#drawWizardPage2", isVisible());
        });

        step("Save the draw results", () -> {
            validator.setUpFileDialogue(FileDialogueType.RUN_DRAW, FileFormatType.DRAW, drawDirectory);
            robot.clickOn("#buttonDraw2SaveResults");
            validator.validateDraw(drawDirectory, "Albert.pdf", "Beryl.pdf", "Carla.pdf");
        });

        step("Close the draw wizard", () -> {
            robot.clickOn("Finish ");
        });
    }

    public void part4WebLinks() {
        step("Open website", () -> {
            robot.clickOn("#menuHelp");
            robot.clickOn("#menuWebsite");
            validator.validateWebPage(WEBSITE);
        });

        step("Open Santulator How To", () -> {
            robot.clickOn("#menuHelp");
            robot.clickOn("#menuHowTo");
            validator.validateWebPage(WEBPAGE_HELP);
        });

        step("Open Issue Reporting", () -> {
            robot.clickOn("#menuHelp");
            robot.clickOn("#menuIssue");
            validator.validateWebPage(WEBPAGE_ISSUE);
        });
    }

    public void part5Exit() {
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

    private Node participantNode(final String style, final int index) {
        return robot.lookup("." + style).nth(index).query();
    }

    private void clearField(final String query) {
        TextInputControl control = robot.lookup(query).queryTextInputControl();

        Platform.runLater(control::clear);
    }

    private Node lookup(final String first, final String... queries) {
        NodeQuery nodeQuery = robot.lookup(first);

        for (String query : queries) {
            nodeQuery = nodeQuery.lookup(query);
        }

        return nodeQuery.query();
    }
}
