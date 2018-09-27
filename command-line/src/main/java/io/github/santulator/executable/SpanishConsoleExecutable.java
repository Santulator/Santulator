/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.executable;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.santulator.writer.WriterModule;

import java.nio.file.Paths;
import java.util.Locale;

public final class SpanishConsoleExecutable {
    private SpanishConsoleExecutable() {
        // Prevent instantiation - all methods are static
    }

    public static void main(final String[] args) {
        Injector injector = Guice.createInjector(new SantaModule(Locale.forLanguageTag("es")), new WriterModule());
        SimpleSantaRunner runner = injector.getInstance(SimpleSantaRunner.class);

        runner.run(Paths.get(args[0]), Paths.get(args[1]));
    }
}
