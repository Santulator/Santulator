/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.core;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public final class ExcelTool {
    private static final String ERROR_READ = "Unable to read spreadsheet '%s'";

    private ExcelTool() {
        // Prevent instantiation - all methods are static
    }

    public static List<List<String>> readContent(final String name, final InputStream stream) {
        try (Workbook wb = readWorkbook(stream)) {
            Sheet sheet = wb.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter(CoreConstants.LOCALE);

            return StreamSupport.stream(sheet.spliterator(), false)
                .map(row -> row(row, dataFormatter))
                .filter(l -> !l.isEmpty())
                .toList();
        } catch (final Exception e) {
            throw new SantaException(String.format(ERROR_READ, name), e);
        }
    }

    private static List<String> row(final Row row, final DataFormatter dataFormatter) {
        List<String> result = IntStream.range(0, row.getLastCellNum())
            .mapToObj(i -> row.getCell(i, MissingCellPolicy.RETURN_BLANK_AS_NULL))
            .map(cell -> cellValue(cell, dataFormatter))
            .map(StringUtils::trimToNull)
            .toList();
        int last = CoreTool.findLast(result, Objects::nonNull).orElse(-1);

        return result.subList(0, last + 1);
    }

    private static String cellValue(final Cell cell, final DataFormatter dataFormatter) {
        if (cell == null) {
            return null;
        } else {
            return dataFormatter.formatCellValue(cell);
        }
    }

    private static Workbook readWorkbook(final InputStream stream) throws IOException {
        return WorkbookFactory.create(stream);
    }
}
