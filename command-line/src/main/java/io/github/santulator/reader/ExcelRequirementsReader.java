/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.reader;

import io.github.santulator.core.CoreTool;
import io.github.santulator.core.SantaException;
import io.github.santulator.model.DrawRequirements;
import io.github.santulator.model.ParticipantRole;
import io.github.santulator.model.Person;
import io.github.santulator.model.Restriction;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.inject.Singleton;

import static io.github.santulator.core.CoreConstants.LOCALE;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Singleton
public class ExcelRequirementsReader implements RequirementsReader {
    private static final String ERROR_READ = "Unable to read spreadsheet '%s'";

    private static final String ERROR_DUPLICATE = "Duplicate person %s in spreadsheet '%s'";

    private static final String ERROR_UNKNOWN = "Unknown person %s in spreadsheet '%s'";

    private static final String ERROR_BLANK = "Row %d contains a blank cell in spreadsheet '%s'";

    private static final String ERROR_ROLE = "Bad role name in spreadsheet '%s'";

    @Override
    public DrawRequirements read(final String name, final InputStream stream) {
        List<List<String>> content = readContent(name, stream);
        List<Person> participants = content.stream()
            .map(l -> new Person(l.get(0), role(name, l)))
            .collect(toList());
        Map<String, Person> participantMap = participantMap(name, participants);
        List<Restriction> restrictions = restrictions(name, participantMap, content);

        return new DrawRequirements(participants, restrictions);
    }

    private ParticipantRole role(final String name, final List<String> l) {
        if (l.size() >= 2) {
            String text = l.get(1);

            if (text != null) {
                try {
                    return ParticipantRole.valueOf(text.trim().toUpperCase(LOCALE));
                } catch (IllegalArgumentException e) {
                    throw new SantaException(String.format(ERROR_ROLE, name), e);
                }
            }
        }

        throw new SantaException(String.format(ERROR_ROLE, name));
    }

    private List<Restriction> restrictions(final String name, final Map<String, Person> people, final List<List<String>> content) {
        return content.stream()
            .flatMap(l -> restrictionsForPerson(name, people, l))
            .collect(toList());
    }

    private Stream<Restriction> restrictionsForPerson(final String name, final Map<String, Person> people, final List<String> line) {
        Person from = people.get(line.get(0));

        return line.subList(2, line.size()).stream()
            .map(n -> restriction(name, people, from, n));
    }

    private Restriction restriction(final String name, final Map<String, Person> people, final Person fromPerson, final String to) {
        Person toPerson = people.get(to);

        if (toPerson == null) {
            throw new SantaException(String.format(ERROR_UNKNOWN, to, name));
        }

        return new Restriction(fromPerson, toPerson);
    }

    private Map<String, Person> participantMap(final String name, final List<Person> participants) {
        return participants.stream()
            .collect(toMap(Person::getName, Function.identity(), (p1, p2) -> {
                throw new SantaException(String.format(ERROR_DUPLICATE, p1, name));
            }));
    }

    private List<List<String>> readContent(final String name, final InputStream stream) {
        Workbook wb = readWorkbook(name, stream);
        Sheet sheet = wb.getSheetAt(0);
        List<List<String>> content = StreamSupport.stream(sheet.spliterator(), false)
            .map(this::row)
            .filter(l -> !l.isEmpty())
            .collect(toList());

        rejectBlanks(name, content);

        return content;
    }

    private void rejectBlanks(final String name, final List<List<String>> lines) {
        OptionalInt result = IntStream.range(0, lines.size())
            .filter(i -> lines.get(i).contains(null))
            .findFirst();

        result.ifPresent(i -> {
            throw new SantaException(String.format(ERROR_BLANK, i + 1, name));
        });
    }

    private List<String> row(final Row row) {
        List<String> result = IntStream.range(0, row.getLastCellNum())
            .mapToObj(i -> row.getCell(i, MissingCellPolicy.RETURN_BLANK_AS_NULL))
            .map(this::cellValue)
            .map(StringUtils::trimToNull)
            .collect(toList());
        int last = CoreTool.findLast(result, Objects::nonNull).orElse(-1);

        return result.subList(0, last + 1);
    }

    private String cellValue(final Cell cell) {
        if (cell == null) {
            return null;
        } else {
            return cell.getStringCellValue();
        }
    }

    private Workbook readWorkbook(final String name, final InputStream stream) {
        try {
            return WorkbookFactory.create(stream);
        } catch (Exception e) {
            throw new SantaException(String.format(ERROR_READ, name), e);
        }
    }
}
