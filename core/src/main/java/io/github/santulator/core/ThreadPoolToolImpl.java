/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Singleton;

@Singleton
public class ThreadPoolToolImpl implements ThreadPoolTool {
    private static final int CORE_POOL_SIZE = 3;

    private final AtomicInteger nextThreadNumber = new AtomicInteger(1);

    private final ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(CORE_POOL_SIZE, r -> newDaemonThread(r, "gui-background-worker-"));

    @Override
    public ScheduledExecutorService guiThreadPool() {
        return threadPool;
    }

    private Thread newDaemonThread(final Runnable r, final String prefix) {
        int threadNumber = nextThreadNumber.getAndIncrement();
        Thread thread = new Thread(r, prefix + threadNumber);

        thread.setDaemon(true);

        return thread;
    }

    @Override
    public void forceShutdown() {
        threadPool.shutdownNow();
    }
}
