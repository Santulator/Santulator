/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.executable;

import io.github.santulator.core.SantaException;
import io.github.santulator.engine.DrawOutputTool;
import io.github.santulator.engine.DrawService;
import io.github.santulator.model.DrawRequirements;
import io.github.santulator.model.DrawSelection;
import io.github.santulator.reader.RequirementsReader;
import io.github.santulator.writer.DrawSelectionWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SimpleSantaRunnerImpl implements SimpleSantaRunner {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleSantaRunnerImpl.class);

    private final RequirementsReader reader;

    private final DrawService drawService;

    private final DrawSelectionWriter writer;

    @Inject
    public SimpleSantaRunnerImpl(final RequirementsReader reader, final DrawService drawService, final DrawSelectionWriter writer) {
        this.reader = reader;
        this.drawService = drawService;
        this.writer = writer;
    }

    @Override
    public void run(final Path input, final Path output) {
        try (InputStream in = new BufferedInputStream(Files.newInputStream(input))) {
            DrawRequirements requirements = reader.read(input.toString(), in);
            DrawSelection selection = drawService.draw(requirements);
            DrawOutputTool tool = new DrawOutputTool(requirements);

            writer.writeDrawSelection(selection, output);
            tool.showSelection(selection);
            LOG.info("Selection complete");
        } catch (IOException e) {
            throw new SantaException(String.format("Unable to read file '%s'", input), e);
        }
    }
}
