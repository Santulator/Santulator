/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.inject.Singleton;

@Singleton
public class ThreadPoolToolImpl implements ThreadPoolTool {
    private static final int CORE_POOL_SIZE = 3;

    private final List<ExecutorService> services = new ArrayList<>();

    @Override
    public Executor singleDaemonExecutor(final String name) {
        ExecutorService executor = Executors.newFixedThreadPool(1, r -> newDaemonThread(r, name));

        services.add(executor);

        return executor;
    }

    @Override
    public ScheduledExecutorService guiThreadPool() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(CORE_POOL_SIZE, r -> newDaemonThread(r, "gui-background-worker"));

        services.add(service);

        return service;
    }

    private Thread newDaemonThread(final Runnable r, final String name) {
        Thread thread = new Thread(r, name);

        thread.setDaemon(true);

        return thread;
    }

    @Override
    public void forceShutdown() {
        services.forEach(ExecutorService::shutdownNow);
    }
}
