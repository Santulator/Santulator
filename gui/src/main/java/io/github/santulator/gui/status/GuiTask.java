/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.status;

import io.github.santulator.core.GuiTaskHandler;
import javafx.concurrent.Task;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class GuiTask<T> extends Task<Void> {
    private final GuiTaskHandler guiTaskHandler;

    private final StatusManager statusManager;

    private final Supplier<T> body;

    private final Consumer<T> finisher;

    private final Consumer<RuntimeException> errorHandler;

    private final boolean isCompletedInCall;

    public GuiTask(final GuiTaskHandler guiTaskHandler, final StatusManager statusManager, final Supplier<T> body, final Consumer<RuntimeException> errorHandler, final boolean isCompletedInCall) {
        this(guiTaskHandler, statusManager, body, t -> doNothingFinisher(), errorHandler, isCompletedInCall);
    }

    public GuiTask(final GuiTaskHandler guiTaskHandler, final StatusManager statusManager, final Supplier<T> body, final Consumer<T> finisher, final Consumer<RuntimeException> errorHandler) {
        this(guiTaskHandler, statusManager, body, finisher, errorHandler, true);
    }

    public GuiTask(final GuiTaskHandler guiTaskHandler, final StatusManager statusManager, final Supplier<T> body, final Consumer<T> finisher,
        final Consumer<RuntimeException> errorHandler, final boolean isCompletedInCall) {
        this.guiTaskHandler = guiTaskHandler;
        this.statusManager = statusManager;
        this.body = body;
        this.finisher = finisher;
        this.errorHandler = errorHandler;
        this.isCompletedInCall = isCompletedInCall;
    }

    @Override
    protected Void call() {
        try {
            T result = body.get();

            if (result != null) {
                guiTaskHandler.executeOnGuiThread(() -> afterBody(result));
            }
        } catch (final RuntimeException e) {
            guiTaskHandler.executeOnGuiThread(() -> errorHandler.accept(e));
        } finally {
            if (isCompletedInCall) {
                guiTaskHandler.executeOnGuiThread(statusManager::completeAction);
            }
        }
        return null;
    }

    private void afterBody(final T result) {
        finisher.accept(result);
        statusManager.markSuccess();
    }

    private static void doNothingFinisher() {
        // No operation required - used where a finisher is not required
    }
}
