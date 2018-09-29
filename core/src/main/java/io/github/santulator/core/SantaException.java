/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.core;

public class SantaException extends RuntimeException {
    public SantaException(final String message) {
        super(message);
    }

    public SantaException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
