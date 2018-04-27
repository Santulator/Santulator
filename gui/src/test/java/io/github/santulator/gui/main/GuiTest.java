/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.main;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import io.github.santulator.core.GuiTaskHandler;
import io.github.santulator.core.GuiTaskHandlerForTesting;
import io.github.santulator.core.SantaException;
import io.github.santulator.gui.common.Placement;
import io.github.santulator.gui.dialogues.FileDialogue;
import io.github.santulator.gui.dialogues.FileDialogueFactory;
import io.github.santulator.gui.dialogues.FileDialogueType;
import io.github.santulator.gui.dialogues.FileFormatType;
import io.github.santulator.gui.services.EnvironmentManager;
import io.github.santulator.gui.services.PlacementManager;
import io.github.santulator.gui.services.WebPageTool;
import io.github.santulator.gui.settings.SettingsManager;
import io.github.santulator.gui.settings.SettingsManagerImpl;
import io.github.santulator.test.TestFileManager;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import static io.github.santulator.gui.main.GuiTestConstants.WINDOW_HEIGHT;
import static io.github.santulator.gui.main.GuiTestConstants.WINDOW_WIDTH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxToolkit.registerPrimaryStage;
import static org.testfx.api.FxToolkit.setupApplication;

public class GuiTest extends FxRobot implements GuiTestValidator {
    private TestFileManager manager;

    @Mock
    private EnvironmentManager environmentManager;

    @Mock
    private PlacementManager placementManager;

    @Mock
    private FileDialogueFactory fileDialogueFactory;

    @Mock
    private FileDialogue fileDialogue;

    @Mock
    private WebPageTool webPageTool;

    @Captor
    private ArgumentCaptor<String> webPageCaptor;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void setupSpec() throws Exception {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
        registerPrimaryStage();
    }

    @BeforeEach
    public void setUp() throws Exception {
        when(environmentManager.useSystemMenuBar()).thenReturn(false);
        when(environmentManager.isExitOptionShown()).thenReturn(true);
        when(placementManager.getMainWindow()).thenReturn(new Placement(WINDOW_WIDTH, WINDOW_HEIGHT));

        manager = new TestFileManager(getClass());

        Path settingsFile = manager.addFile(SettingsManagerImpl.SETTINGS_JSON);
        SettingsManager settingsManager = new SettingsManagerImpl(settingsFile);

        CoreGuiModule coreModule = new CoreGuiModule();
        Module testModule = new AbstractModule() {
            @Override
            protected void configure() {
                bind(SettingsManager.class).toInstance(settingsManager);
                bind(FileDialogueFactory.class).toInstance(fileDialogueFactory);
                bind(EnvironmentManager.class).toInstance(environmentManager);
                bind(PlacementManager.class).toInstance(placementManager);
                bind(WebPageTool.class).toInstance(webPageTool);
                bind(GuiTaskHandler.class).to(GuiTaskHandlerForTesting.class);
            }
        };
        SantulatorGuiExecutable.setModules(coreModule, testModule);

        setupApplication(SantulatorGuiExecutable.class);
    }

    @Override
    public void setUpFileDialogue(final FileDialogueType dialogueType, final FileFormatType fileType, final String file) {
        try {
            setUpFileDialogue(dialogueType, fileType, manager.addCopy(file));
        } catch (URISyntaxException | IOException e) {
            throw new SantaException("Unable to open file " + file, e);
        }
    }

    @Override
    public void setUpFileDialogue(final FileDialogueType dialogueType, final FileFormatType fileType, final Path file) {
        when(fileDialogueFactory.create(eq(dialogueType), any(Stage.class))).thenReturn(fileDialogue);
        when(fileDialogue.isFileSelected()).thenReturn(true);
        when(fileDialogue.getSelectedFile()).thenReturn(file);
        when(fileDialogue.getFileFormatType()).thenReturn(fileType);
    }

    @AfterEach
    public void tearDown() throws Exception {
        manager.cleanup();
    }

    @Test
    public void testWalkThrough() {
        GuiTestSteps steps = new GuiTestSteps(this, this, manager);

        steps.part1BasicWalkThrough();
        steps.part2WebLinks();
        steps.part3Exit();
    }

    @Override
    public void validateWebPage(final String page) {
        verify(webPageTool, atLeastOnce()).showWebPage(webPageCaptor.capture());
        assertEquals(page, webPageCaptor.getValue(), "Website");
    }

    @Override
    public void validateSavedSession(final Path file, final String name) {
        // TODO Implement session validation
    }
}
