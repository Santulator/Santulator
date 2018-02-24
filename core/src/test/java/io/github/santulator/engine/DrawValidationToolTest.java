/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.engine;

import io.github.santulator.core.SantaException;
import io.github.santulator.model.*;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static io.github.santulator.core.CoreTool.listOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DrawValidationToolTest {
    private static final Person PERSON_1 = new Person("PERSON_1");

    private static final Person PERSON_2 = new Person("PERSON_2");

    private static final Person PERSON_3 = new Person("PERSON_3");

    private static final List<Person> PARTICIPANTS = listOf(PERSON_1, PERSON_2, PERSON_3);

    private static final Restriction RESTRICTION_1 = new Restriction(PERSON_1, PERSON_2);

    private static final List<Restriction> RESTRICTIONS = Collections.singletonList(RESTRICTION_1);

    private static final DrawRequirements REQUIREMENTS = new DrawRequirements(PARTICIPANTS, RESTRICTIONS);

    @Test
    public void testAcceptedSelection() {
        DrawSelection selection = selection(giver(PERSON_3, PERSON_2), giver(PERSON_2, PERSON_1), giver(PERSON_1, PERSON_3));

        DrawValidationTool.validate(REQUIREMENTS, selection);
    }

    @Test
    public void testValidateMissingPerson() {
        DrawSelection selection = selection(giver(PERSON_3, PERSON_1));

        validateBadSelection(selection);
    }

    @Test
    public void testRestrictionViolation() {
        DrawSelection selection = selection(giver(PERSON_1, PERSON_2), giver(PERSON_2, PERSON_3), giver(PERSON_3, PERSON_1));

        validateBadSelection(selection);
    }

    @Test
    public void testDuplicateGiver() {
        DrawSelection selection = selection(giver(PERSON_3, PERSON_2), giver(PERSON_2, PERSON_1), giver(PERSON_1, PERSON_3), giver(PERSON_1, PERSON_3));

        validateBadSelection(selection);
    }

    @Test
    public void testDuplicateGift() {
        DrawSelection selection = selection(giver(PERSON_3, PERSON_2), giver(PERSON_2, PERSON_1), giver(PERSON_1, PERSON_3), giver(PERSON_2, PERSON_3));

        validateBadSelection(selection);
    }

    private void validateBadSelection(final DrawSelection selection) {
        assertThrows(SantaException.class, () -> DrawValidationTool.validate(REQUIREMENTS, selection));
    }

    private DrawSelection selection(final GiverAssignment... givers) {
        return new DrawSelection(listOf(givers));
    }

    private GiverAssignment giver(final Person from, final Person to) {
        return new GiverAssignment(from, to);
    }
}