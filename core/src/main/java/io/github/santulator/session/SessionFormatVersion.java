/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.session;

public final class SessionFormatVersion {
    /**
     * First version of the format.
     */
    public static final int FORMAT_1 = 1;

    public static final int LATEST_VERSION = FORMAT_1;

    private SessionFormatVersion() {
        // Prevent instantiation - only constants are defined.
    }
}
