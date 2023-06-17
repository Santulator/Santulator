/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.services;

import io.github.santulator.core.ThreadPoolTool;
import io.github.santulator.gui.i18n.I18nKey;
import io.github.santulator.gui.i18n.I18nManager;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.net.URI;
import java.nio.file.Path;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

@Singleton
public class DesktopResourceToolImpl implements DesktopResourceTool {
    private static final Logger LOG = LoggerFactory.getLogger(DesktopResourceToolImpl.class);

    private final Executor executor;

    private final Consumer<String> pageOpener;

    private final Consumer<Path> pathOpener;

    private final I18nManager i18nManager;

    @Inject
    public DesktopResourceToolImpl(final ThreadPoolTool threadPoolTool, final I18nManager i18nManager) {
        this(threadPoolTool.guiThreadPool(), DesktopResourceToolImpl::openPageInternal, DesktopResourceToolImpl::openPathInternal, i18nManager);
    }

    public DesktopResourceToolImpl(final Executor executor, final Consumer<String> pageOpener, final Consumer<Path> pathOpener, final I18nManager i18nManager) {
        this.executor = executor;
        this.pageOpener = pageOpener;
        this.pathOpener = pathOpener;
        this.i18nManager = i18nManager;
    }

    @Override
    public void showWebPage(final String page) {
        executor.execute(() -> pageOpener.accept(page));
    }

    @Override
    public void showWebPage(final I18nKey key) {
        showWebPage(i18nManager.text(key));
    }

    @Override
    public void openPath(final Path path) {
        executor.execute(() -> pathOpener.accept(path));
    }

    private static void openPageInternal(final String page) {
        try {
            Desktop.getDesktop().browse(new URI(page));
        } catch (final Exception e) {
            LOG.error("Unable to open page {}", page, e);
        }
    }

    private static void openPathInternal(final Path path) {
        try {
            Desktop.getDesktop().open(path.toFile());
        } catch (final Exception e) {
            LOG.error("Unable to open path {}", path, e);
        }
    }
}
