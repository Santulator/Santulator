/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.session;

import io.github.santulator.core.ExcelTool;
import io.github.santulator.core.SantaException;
import io.github.santulator.model.ParticipantRole;
import io.github.santulator.model.ParticipantState;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import javax.inject.Singleton;

import static io.github.santulator.session.FileNameTool.filename;
import static java.util.stream.Collectors.toList;

@Singleton
public class SessionImporterImpl implements SessionImporter {
    @Override
    public List<ParticipantState> importParticipants(final Path file) {
        try (InputStream in = new BufferedInputStream(Files.newInputStream(file))) {
            List<List<String>> lines = ExcelTool.readContent(filename(file), in);
            List<ParticipantState> result = lines.stream()
                .map(l -> l.get(0))
                .filter(Objects::nonNull)
                .map(n -> new ParticipantState(n, ParticipantRole.BOTH))
                .collect(toList());

            if (result.isEmpty()) {
                throw new SantaException(String.format("No participants found in file '%s'", file));
            } else {
                return result;
            }
        } catch (IOException e) {
            throw new SantaException(String.format("Unable to read file '%s'", file), e);
        }
    }
}
