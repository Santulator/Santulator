/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.reader;

import io.github.santulator.core.SantaException;
import io.github.santulator.model.DrawRequirements;
import io.github.santulator.model.Person;
import io.github.santulator.model.Restriction;
import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.inject.Singleton;

@Singleton
public class ExcelRequirementsReader implements RequirementsReader {
    private static final String ERROR_READ = "Unable to read spreadsheet '%s'";

    private static final String ERROR_DUPLICATE = "Duplicate person %s in spreadsheet '%s'";

    private static final String ERROR_UNKNOWN = "Unknown person %s in spreadsheet '%s'";

    @Override
    public DrawRequirements read(final String name, final InputStream stream) {
        List<Person> participants = new ArrayList<>();
        List<Restriction> restrictions = new ArrayList<>();
        Map<String, List<String>> content = content(name, stream);
        Map<String, Person> people = new TreeMap<>();

        for (String personName : content.keySet()) {
            Person person = new Person(personName);

            people.put(personName, person);
            participants.add(person);
        }
        for (Entry<String, List<String>> entry : content.entrySet()) {
            Person from = people.get(entry.getKey());
            for (String rhs : entry.getValue()) {
                Person to = people.get(rhs);

                if (to == null) {
                    throw new SantaException(String.format(ERROR_UNKNOWN, rhs, name));
                } else {
                    restrictions.add(new Restriction(from, to));
                }
            }
        }

        return new DrawRequirements(participants, restrictions);
    }

    private Map<String, List<String>> content(final String name,
                                              final InputStream stream) {
        Map<String, List<String>> content = new TreeMap<>();
        Workbook wb = readWorkbook(name, stream);
        Sheet sheet = wb.getSheetAt(0);

        for (Row row : sheet) {
            ArrayList<String> list = null;

            for (Cell cell : row) {
                String text = cell.getStringCellValue();

                if (list == null) {
                    if (content.containsKey(text)) {
                        throw new SantaException(String.format(ERROR_DUPLICATE, text, name));
                    } else {
                        list = new ArrayList<>();
                        content.put(text, list);
                    }
                } else {
                    list.add(text);
                }
            }
        }

        return content;
    }

    private Workbook readWorkbook(final String name, final InputStream stream) {
        try {
            return WorkbookFactory.create(stream);
        } catch (Exception e) {
            throw new SantaException(String.format(ERROR_READ, name), e);
        }
    }
}
