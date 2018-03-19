package io.github.santulator.executable;

import com.google.inject.AbstractModule;
import io.github.santulator.engine.DrawService;
import io.github.santulator.engine.DrawServiceImpl;
import io.github.santulator.reader.ExcelRequirementsReader;
import io.github.santulator.reader.RequirementsReader;

public class SantaModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DrawService.class).to(DrawServiceImpl.class);
        bind(RequirementsReader.class).to(ExcelRequirementsReader.class);
        bind(SimpleSantaRunner.class).to(SimpleSantaRunnerImpl.class);
    }
}
