/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.writer;

import io.github.santulator.core.I18nBundleProvider;
import io.github.santulator.core.SantaException;
import io.github.santulator.model.DrawSelection;
import io.github.santulator.model.GiverAssignment;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

@Singleton
public class DrawSelectionWriterImpl implements DrawSelectionWriter {
    private static final Logger LOG = LoggerFactory.getLogger(DrawSelectionWriterImpl.class);

    private static final String ERROR_DIR = "Unable to create directory '%s'";

    private static final String ERROR_ASSIGNMENT = "Unable to create file '%s'";

    private final I18nBundleProvider i18nBundleProvider;

    @Inject
    public DrawSelectionWriterImpl(final I18nBundleProvider i18nBundleProvider) {
        this.i18nBundleProvider = i18nBundleProvider;
    }

    @Override
    public void writeDrawSelection(final DrawSelection selection, final Path dir, final String password, final Runnable onWriteComplete) {
        long start = System.nanoTime();
        GiverAssignmentWriter writer = new PdfGiverAssignmentWriter(i18nBundleProvider);

        mkdir(dir);
        selection.getGivers()
            .forEach(assignment -> writeGiverAssignment(writer, assignment, password, onWriteComplete, dir));

        String timeText = String.format("%,d", Duration.ofNanos(System.nanoTime() - start).toMillis());

        LOG.info("{} files written ({} ms)", selection.getGivers().size(), timeText);
    }

    private void writeGiverAssignment(final GiverAssignmentWriter writer, final GiverAssignment assignment, final String password, final Runnable onWriteComplete, final Path dir) {
        String name = assignment.getFrom().getName() + writer.getFormatSuffix();
        Path file = dir.resolve(name);

        try (OutputStream out = Files.newOutputStream(file)) {
            writer.writeGiverAssignment(name, assignment, out, password);
            onWriteComplete.run();
        } catch (IOException | NoClassDefFoundError e) {
            throw new SantaException(String.format(ERROR_ASSIGNMENT, file), e);
        }
    }

    private void mkdir(final Path dir) {
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            throw new SantaException(String.format(ERROR_DIR, dir), e);
        }
    }
}
