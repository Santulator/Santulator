/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.core;

import java.util.concurrent.ScheduledExecutorService;

public interface ThreadPoolTool {
    ScheduledExecutorService guiThreadPool();

    void forceShutdown();
}
