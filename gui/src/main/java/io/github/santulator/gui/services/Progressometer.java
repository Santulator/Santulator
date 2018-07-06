package io.github.santulator.gui.services;

import io.github.santulator.core.ThreadPoolTool;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.inject.Inject;

public class Progressometer {
    private static final int PROGRESS_WAIT = 1_000;

    private static final int PROGRESS_TICKS = 100;

    private final ThreadPoolTool threadPoolTool;

    private final AtomicInteger completedCount = new AtomicInteger();

    private final ProgressSequencer sequencer;

    private final DoubleProperty progress = new SimpleDoubleProperty();

    private final BooleanProperty complete = new SimpleBooleanProperty();

    private final AtomicReference<ScheduledFuture<?>> future = new AtomicReference<>();

    @Inject
    public Progressometer(final ThreadPoolTool threadPoolTool, final ProgressSequencer sequencer) {
        this.threadPoolTool = threadPoolTool;
        this.sequencer = sequencer;
    }

    public void start(final int size) {
        ScheduledExecutorService executor = threadPoolTool.guiThreadPool();
        int betweenTicks = PROGRESS_WAIT / PROGRESS_TICKS;
        ProgressState state = new ProgressState(sequencer.sequence(size));

        future.set(executor.scheduleAtFixedRate(() -> executeTick(state), betweenTicks, betweenTicks, TimeUnit.MILLISECONDS));
    }

    public void completeTask() {
        completedCount.incrementAndGet();
    }

    private void executeTick(final ProgressState state) {
        int tick = state.tick(completedCount.get());

        if (state.isComplete()) {
            cancel();
            Platform.runLater(() -> complete.set(true));
        }
        Platform.runLater(() -> progress.set((double) tick / PROGRESS_TICKS));
    }

    public void reset() {
        cancel();
        Platform.runLater(() -> resetProperties());
    }

    private void cancel() {
        ScheduledFuture<?> task = future.getAndSet(null);

        if (task != null) {
            task.cancel(true);
        }
    }

    private void resetProperties() {
        progress.set(0);
        complete.set(false);
    }

    public DoubleProperty progressProperty() {
        return progress;
    }

    public BooleanProperty completeProperty() {
        return complete;
    }
}
