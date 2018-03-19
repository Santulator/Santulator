/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.reader;

import io.github.santulator.model.DrawRequirements;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExcelRequirementsReaderTest {
    private static final String SPREADSHEET_XLS = "/spreadsheets/SantaRequirements.xls";

    private final RequirementsReader target = new ExcelRequirementsReader();

    @Test
    public void loadRequirements() throws Exception {
        InputStream stream = ExcelRequirementsReaderTest.class.getResourceAsStream(SPREADSHEET_XLS);
        DrawRequirements requirements = target.read(SPREADSHEET_XLS, stream);

        assertNotNull(requirements, "Requirements");
        stream.close();
    }
}
