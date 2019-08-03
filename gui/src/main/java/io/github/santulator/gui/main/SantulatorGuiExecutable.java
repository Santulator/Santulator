/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.main;

import com.gluonhq.ignite.guice.GuiceContext;
import com.google.inject.Module;
import io.github.santulator.writer.WriterModule;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import javax.inject.Inject;

import static io.github.santulator.gui.main.ExecutableLogTool.*;

public class SantulatorGuiExecutable extends Application {
    private static Collection<Module> modules;

    private final GuiceContext context = new GuiceContext(this, () -> modules);

    @Inject
    private SantulatorGui santulatorGui;

    public static void setModules(final Module... m) {
        modules = List.of(m);
    }

    public <T> T getInstance(final Class<T> c) {
        return context.getInstance(c);
    }

    @Override
    public void start(final Stage stage) {
        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> logError(e));
        try {
            context.init();
            santulatorGui.start(stage);
        } catch (final RuntimeException e) {
            logError(e);
            Platform.exit();
        }
    }

    public static void main(final String... args) {
        runApp(args, Application::launch, new CoreGuiModule(args), new WriterModule(), new LiveGuiModule());
    }

    protected static void runApp(final String[] args, final Consumer<String[]> launcher, final Module... modules) {
        logStartup();
        try {
            logSystemDetails();
            setModules(modules);
            launcher.accept(args);
        } finally {
            logShutdown();
        }
    }
}
