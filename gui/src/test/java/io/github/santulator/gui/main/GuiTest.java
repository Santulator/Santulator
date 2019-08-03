/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.main;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import io.github.santulator.core.SantaException;
import io.github.santulator.gui.common.GuiTaskHandler;
import io.github.santulator.gui.common.GuiTaskHandlerForTesting;
import io.github.santulator.gui.common.Placement;
import io.github.santulator.gui.dialogues.FileDialogue;
import io.github.santulator.gui.dialogues.FileDialogueFactory;
import io.github.santulator.gui.dialogues.FileDialogueType;
import io.github.santulator.gui.dialogues.FileFormatType;
import io.github.santulator.gui.services.DesktopResourceTool;
import io.github.santulator.gui.services.EnvironmentManager;
import io.github.santulator.gui.services.PlacementManager;
import io.github.santulator.gui.settings.SettingsManager;
import io.github.santulator.gui.settings.SettingsManagerImpl;
import io.github.santulator.model.SessionState;
import io.github.santulator.session.SessionSerialiser;
import io.github.santulator.test.core.TestFileManager;
import io.github.santulator.writer.WriterModule;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Stream;

import static io.github.santulator.gui.main.GuiTestConstants.WINDOW_HEIGHT;
import static io.github.santulator.gui.main.GuiTestConstants.WINDOW_WIDTH;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxToolkit.registerPrimaryStage;
import static org.testfx.api.FxToolkit.setupApplication;

@ExtendWith(MockitoExtension.class)
@ExtendWith(ApplicationExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GuiTest implements GuiTestValidator {
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
    private DesktopResourceTool desktopResourceTool;

    @Captor
    private ArgumentCaptor<String> webPageCaptor;

    @Captor
    private ArgumentCaptor<Path> pathCaptor;

    private SantulatorGuiExecutable executable;

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
                bind(DesktopResourceTool.class).toInstance(desktopResourceTool);
                bind(GuiTaskHandler.class).to(GuiTaskHandlerForTesting.class);
            }
        };
        SantulatorGuiExecutable.setModules(coreModule, testModule, new WriterModule());

        executable = (SantulatorGuiExecutable) setupApplication(SantulatorGuiExecutable.class);
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
    public void testWalkThrough(final FxRobot robot) {
        GuiTestSteps steps = new GuiTestSteps(robot, this, manager);

        steps.part1SetupDraw();
        steps.part2ChangeSessions();
        steps.part3RunDraw();
        steps.part4AboutDialogue();
        steps.part5WebLinks();
        steps.part6Exit();
    }

    @Override
    public void validateWebPage(final String page) {
        verify(desktopResourceTool, atLeastOnce()).showWebPage(webPageCaptor.capture());
        assertEquals(page, webPageCaptor.getValue(), "Website");
    }

    @Override
    public void validateOpenPath(final Path path) {
        verify(desktopResourceTool, atLeastOnce()).openPath(pathCaptor.capture());
        assertEquals(path, pathCaptor.getValue(), "Path");
    }

    @Override
    public void validateSavedSession(final Path file, final SessionState expected) {
        SessionSerialiser serialiser = executable.getInstance(SessionSerialiser.class);
        SessionState state = serialiser.read(file);

        assertEquals(expected, state);
    }

    @Override
    public void validateDraw(final Path directory, final String... names) {
        try (Stream<Path> files = Files.list(directory)) {
            Set<Path> result = files
                .collect(toSet());
            Set<Path> expected = Stream.of(names)
                .map(directory::resolve)
                .collect(toSet());

            assertEquals(expected, result);
        } catch (final IOException e) {
            throw new SantaException(String.format("Unable to list directory '%s'", directory), e);
        }
    }
}
