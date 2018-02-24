/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.model;

import java.util.ArrayList;
import java.util.List;

import static io.github.santulator.core.CoreTool.listOf;
import static java.util.Collections.unmodifiableList;

public final class TestDataBuilder {
    public static final Person ALBERT = new Person("Albert");

    public static final Person BERYL = new Person("Beryl");

    public static final Person CARLA = new Person("Carla");

    public static final Person DAVID = new Person("David");

    public static final Person EDITH = new Person("Edith");

    public static final Person FRED = new Person("Fred");

    public static final Person GINA = new Person("Gina");

    public static final Person HARRY = new Person("Harry");

    public static final Person IRIS = new Person("Iris");

    public static final Person JOHN = new Person("John");

    public static final Person KATE = new Person("Kate");

    public static final List<Person> PEOPLE = unmodifiableList(listOf(ALBERT, BERYL, CARLA, DAVID, EDITH, FRED, GINA, HARRY, IRIS, JOHN, KATE));

    private TestDataBuilder() {
        // Prevent instantiation - all methods are static
    }

    public static DrawRequirements buildDrawRequirements() {
        List<Restriction> restrictions = new ArrayList<>();

        addRestrictions(restrictions, ALBERT, BERYL);
        addRestrictions(restrictions, CARLA, DAVID);
        addRestrictions(restrictions, EDITH, FRED);
        addRestrictions(restrictions, GINA, HARRY);
        addRestrictions(restrictions, JOHN, IRIS);

        return new DrawRequirements(PEOPLE, restrictions);
    }

    private static void addRestrictions(final List<Restriction> restrictions, final Person from, final Person to) {
        restrictions.add(new Restriction(from, to));
        restrictions.add(new Restriction(to, from));
    }
}
