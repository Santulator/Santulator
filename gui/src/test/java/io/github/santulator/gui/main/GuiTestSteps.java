/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.main;

import io.github.santulator.test.TestFileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.api.FxRobot;

import java.nio.file.Path;

import static io.github.santulator.gui.common.GuiConstants.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class GuiTestSteps {
    private static final Logger LOG = LoggerFactory.getLogger(GuiTestSteps.class);

    private final FxRobot robot;

    private final GuiTestValidator validator;

    private final Path sessionFile;

    private int stepNo;

    public GuiTestSteps(final FxRobot robot, final GuiTestValidator validator, final TestFileManager manager) {
        this.robot = robot;
        this.validator = validator;

        // TODO Make use of this file
        sessionFile = manager.addFile("session.santa");
    }

    public void part1BasicWalkThrough() {
        step("Open application", () -> {
            verifyThat("#mainBorderPane", isVisible());
        });

        step("Start new session", () -> {
            robot.clickOn("#buttonNew");
            verifyThat("#deleteme", isVisible());
        });

        // TODO Fix this step
        LOG.info("Add session save and validate session file {}", sessionFile);
    }

    public void part2WebLinks() {
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

    public void part3Exit() {
        step("Exit", () -> {
            robot.clickOn("#menuFile");
            robot.clickOn("#menuExit");
        });
    }

    private void step(final String step, final Runnable runnable) {
        ++stepNo;
        LOG.info("STEP {}: Begin - {}", stepNo, step);
        runnable.run();
        LOG.info("STEP {}:   End - {}", stepNo, step);
    }
}
