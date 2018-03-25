/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.reader;

import io.github.santulator.model.DrawRequirements;
import io.github.santulator.model.TestRequirementsTool;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExcelRequirementsReaderTest {
    private static final String SPREADSHEET_XLS = "/spreadsheets/SantaRequirements.xls";

    private final RequirementsReader target = new ExcelRequirementsReader();

    @Test
    public void loadRequirements() throws Exception {
        try (InputStream stream = ExcelRequirementsReaderTest.class.getResourceAsStream(SPREADSHEET_XLS)) {
            DrawRequirements requirements = target.read(SPREADSHEET_XLS, stream);

            assertEquals(TestRequirementsTool.REQUIREMENTS, requirements);
        }
    }
}
