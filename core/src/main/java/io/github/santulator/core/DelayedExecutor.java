/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.core;

import java.util.concurrent.Executor;

public interface DelayedExecutor extends Executor {
    void beginExecution();
}
