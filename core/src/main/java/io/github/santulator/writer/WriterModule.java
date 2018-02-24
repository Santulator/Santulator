package io.github.santulator.writer;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import io.github.santulator.core.Language;

public class WriterModule extends AbstractModule {
    private final Language language;

    public WriterModule(final Language language) {
        this.language = language;
    }

    @Override
    protected void configure() {
        Names.bindProperties(binder(), language.loadProperties());

        bind(DrawSelectionWriter.class).to(DrawSelectionWriterImpl.class);
        bind(GiverAssignmentWriter.class).to(PdfGiverAssignmentWriter.class);
    }
}
