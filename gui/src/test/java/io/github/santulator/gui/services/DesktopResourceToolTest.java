package io.github.santulator.gui.services;

import io.github.santulator.core.ThreadPoolTool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DesktopResourceToolTest {
    private static final String WEB_PAGE = "WEB_PAGE";

    private static final Path PATH = Paths.get("PATH");

    @Mock
    private ThreadPoolTool threadPoolTool;

    @Mock
    private ExecutorService executorService;

    @Mock
    private Consumer<String> pageOpener;

    @Mock
    private Consumer<Path> pathOpener;

    @Captor
    private ArgumentCaptor<Runnable> captor;

    private DesktopResourceTool target;

    @BeforeEach
    public void setUp() {
        when(threadPoolTool.singleDaemonExecutor(anyString())).thenReturn(executorService);
        target = new DesktopResourceToolImpl(threadPoolTool, pageOpener, pathOpener);
    }

    @Test
    public void testShowWebPage() {
        target.showWebPage(WEB_PAGE);
        verify(executorService).execute(captor.capture());
        captor.getValue().run();
        verify(pageOpener).accept(WEB_PAGE);
    }

    @Test
    public void testOpenPath() {
        target.openPath(PATH);
        verify(executorService).execute(captor.capture());
        captor.getValue().run();
        verify(pathOpener).accept(PATH);
    }
}
