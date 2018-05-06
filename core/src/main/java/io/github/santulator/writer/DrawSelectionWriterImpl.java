/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.writer;

import io.github.santulator.core.SantaException;
import io.github.santulator.model.DrawSelection;
import io.github.santulator.model.GiverAssignment;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class DrawSelectionWriterImpl implements DrawSelectionWriter {
    private static final String ERROR_DIR = "Unable to create directory '%s'";

    private static final String ERROR_ASSIGNMENT = "Unable to create file '%s'";

    private final GiverAssignmentWriter writer;

    private final String presetPassword;

    @Inject
    public DrawSelectionWriterImpl(
        final GiverAssignmentWriter writer,
        @Named("password") final String presetPassword) {
        this.writer = writer;
        this.presetPassword = presetPassword;
    }

    @Override
    public void writeDrawSelection(final DrawSelection selection, final Path dir) {
        writeDrawSelection(selection, dir, presetPassword);
    }

    @Override
    public void writeDrawSelection(final DrawSelection selection, final Path dir, final String password) {
        mkdir(dir);
        selection.getGivers()
            .forEach(assignment -> writeGiverAssignment(dir, assignment, password));
    }

    private void writeGiverAssignment(final Path dir, final GiverAssignment assignment, final String password) {
        String name = assignment.getFrom().getName() + writer.getFormatSuffix();
        Path file = dir.resolve(name);

        try (OutputStream out = Files.newOutputStream(file)) {
            writer.writeGiverAssignment(name, assignment, out, password);
        } catch (IOException e) {
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
