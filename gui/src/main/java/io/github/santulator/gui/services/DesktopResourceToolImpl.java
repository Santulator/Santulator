/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.services;

import io.github.santulator.core.ThreadPoolTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.net.URI;
import java.nio.file.Path;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DesktopResourceToolImpl implements DesktopResourceTool {
    private static final Logger LOG = LoggerFactory.getLogger(DesktopResourceToolImpl.class);

    private final Executor executor;

    private final Consumer<String> pageOpener;

    private final Consumer<Path> pathOpener;

    @Inject
    public DesktopResourceToolImpl(final ThreadPoolTool threadPoolTool) {
        this(threadPoolTool, DesktopResourceToolImpl::openPageInternal, DesktopResourceToolImpl::openPathInternal);
    }

    public DesktopResourceToolImpl(final ThreadPoolTool threadPoolTool, final Consumer<String> pageOpener, final Consumer<Path> pathOpener) {
        this.executor = threadPoolTool.guiThreadPool();
        this.pageOpener = pageOpener;
        this.pathOpener = pathOpener;
    }

    @Override
    public void showWebPage(final String page) {
        executor.execute(() -> pageOpener.accept(page));
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
