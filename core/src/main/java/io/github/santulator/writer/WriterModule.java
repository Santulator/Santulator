package io.github.santulator.writer;

import com.google.inject.AbstractModule;

public class WriterModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DrawSelectionWriter.class).to(DrawSelectionWriterImpl.class);
        bind(GiverAssignmentWriter.class).to(PdfGiverAssignmentWriter.class);
    }
}
