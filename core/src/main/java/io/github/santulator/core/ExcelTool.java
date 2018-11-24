package io.github.santulator.core;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public final class ExcelTool {
    private static final String ERROR_READ = "Unable to read spreadsheet '%s'";

    private ExcelTool() {
        // Prevent instantiation - all methods are static
    }

    public static List<List<String>> readContent(final String name, final InputStream stream) {
        Workbook wb = readWorkbook(name, stream);
        Sheet sheet = wb.getSheetAt(0);

        return StreamSupport.stream(sheet.spliterator(), false)
            .map(ExcelTool::row)
            .filter(l -> !l.isEmpty())
            .collect(toList());
    }

    private static List<String> row(final Row row) {
        List<String> result = IntStream.range(0, row.getLastCellNum())
            .mapToObj(i -> row.getCell(i, MissingCellPolicy.RETURN_BLANK_AS_NULL))
            .map(ExcelTool::cellValue)
            .map(StringUtils::trimToNull)
            .collect(toList());
        int last = CoreTool.findLast(result, Objects::nonNull).orElse(-1);

        return result.subList(0, last + 1);
    }

    private static String cellValue(final Cell cell) {
        if (cell == null) {
            return null;
        } else {
            return cell.getStringCellValue();
        }
    }

    private static Workbook readWorkbook(final String name, final InputStream stream) {
        try {
            return WorkbookFactory.create(stream);
        } catch (Exception e) {
            throw new SantaException(String.format(ERROR_READ, name), e);
        }
    }
}
