/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.services;

import io.github.santulator.gui.common.GuiConstants;
import io.github.santulator.gui.i18n.I18nKey;
import io.github.santulator.gui.i18n.I18nManager;
import io.github.santulator.gui.i18n.I18nManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DesktopResourceToolTest {
    private static final String WEB_PAGE = "WEB_PAGE";

    private static final Path PATH = Paths.get("PATH");

    private final I18nManager i18nManager = new I18nManagerImpl();

    @Mock
    private Consumer<String> pageOpener;

    @Mock
    private Consumer<Path> pathOpener;

    private DesktopResourceTool target;

    @BeforeEach
    public void setUp() {
        target = new DesktopResourceToolImpl(Runnable::run, pageOpener, pathOpener, i18nManager);
    }

    @Test
    public void testShowWebPage() {
        target.showWebPage(WEB_PAGE);
        verify(pageOpener).accept(WEB_PAGE);
    }

    @Test
    public void testShowWebPageWithLookup() {
        target.showWebPage(I18nKey.LINK_MAIN);
        verify(pageOpener).accept(GuiConstants.WEBSITE);
    }

    @Test
    public void testOpenPath() {
        target.openPath(PATH);
        verify(pathOpener).accept(PATH);
    }
}
