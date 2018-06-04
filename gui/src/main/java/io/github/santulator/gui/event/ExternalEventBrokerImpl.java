/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.event;

import io.github.santulator.core.SantaException;
import io.github.santulator.core.ThreadPoolTool;
import javafx.application.Platform;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class ExternalEventBrokerImpl implements ExternalEventListener, ExternalEventBroker {
    private final BlockingQueue<ExternalOpenFileEvent> openFileEvents = new LinkedBlockingQueue<>();

    private final ThreadPoolTool threadPoolTool;

    @Inject
    public ExternalEventBrokerImpl(final ThreadPoolTool threadPoolTool) {
        this.threadPoolTool = threadPoolTool;
    }

    @Override
    public void fireOpenFileEvent(final ExternalOpenFileEvent event) {
        try {
            openFileEvents.put(event);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SantaException("Unable to register event", e);
        }
    }

    @Override
    public void setListener(final ExternalEventListener listener) {
        Executor executor = threadPoolTool.singleDaemonExecutor("External Event Broker");

        executor.execute(() -> refireEvents(listener));
    }

    private void refireEvents(final ExternalEventListener listener) {
        boolean isRunning = true;
        while (isRunning && !Thread.interrupted()) {
            try {
                ExternalOpenFileEvent event = openFileEvents.take();

                Platform.runLater(() -> listener.fireOpenFileEvent(event));
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
                isRunning = false;
            }
        }
    }
}
