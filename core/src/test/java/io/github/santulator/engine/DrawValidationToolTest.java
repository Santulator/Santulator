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
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DrawValidationToolTest {
    private static final Person PERSON_1 = new Person("PERSON_1");

    private static final Person PERSON_2 = new Person("PERSON_2");

    private static final Person PERSON_3 = new Person("PERSON_3");

    private static final Person GIVER_ONLY_1 = new Person("GIVER_1", ParticipantRole.GIVER);

    private static final Person GIVER_ONLY_2 = new Person("GIVER_2", ParticipantRole.GIVER);

    private static final Person RECEIVER_ONLY_1 = new Person("RECEIVER_1", ParticipantRole.RECEIVER);

    private static final Person RECEIVER_ONLY_2 = new Person("RECEIVER_2", ParticipantRole.RECEIVER);

    private static final Restriction RESTRICTION_1 = new Restriction(PERSON_1, PERSON_2);

    private static final List<Restriction> RESTRICTIONS = Collections.singletonList(RESTRICTION_1);

    private static final DrawRequirements REQUIREMENTS = requirements(RESTRICTIONS, PERSON_1, PERSON_2, PERSON_3);

    @Test
    public void testAcceptedSelection() {
        DrawSelection selection = selection(giver(PERSON_3, PERSON_2), giver(PERSON_2, PERSON_1), giver(PERSON_1, PERSON_3));

        DrawValidationTool.validate(REQUIREMENTS, selection);
    }

    @Test
    public void testSimpleGiveAndReceive() {
        DrawSelection selection = selection(giver(GIVER_ONLY_1, RECEIVER_ONLY_1));

        DrawValidationTool.validate(requirements(GIVER_ONLY_1, RECEIVER_ONLY_1), selection);
    }

    @Test
    public void testInverseGiveAndReceive() {
        DrawSelection selection = selection(giver(RECEIVER_ONLY_1, GIVER_ONLY_1));

        validateBadSelection(requirements(GIVER_ONLY_1, RECEIVER_ONLY_1), selection);
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
        DrawSelection selection = selection(giver(GIVER_ONLY_1, RECEIVER_ONLY_1), giver(GIVER_ONLY_1, RECEIVER_ONLY_2));

        validateBadSelection(requirements(GIVER_ONLY_1, RECEIVER_ONLY_1, RECEIVER_ONLY_2), selection);
    }

    @Test
    public void testDuplicateReceiver() {
        DrawSelection selection = selection(giver(GIVER_ONLY_1, RECEIVER_ONLY_1), giver(GIVER_ONLY_2, RECEIVER_ONLY_1));

        validateBadSelection(requirements(GIVER_ONLY_1, GIVER_ONLY_2, RECEIVER_ONLY_1), selection);
    }

    @Test
    public void testBadGiverOnly() {
        DrawSelection selection = selection(giver(PERSON_1, GIVER_ONLY_1), giver(GIVER_ONLY_1, PERSON_1));

        validateBadSelection(requirements(PERSON_1, GIVER_ONLY_1), selection);
    }

    @Test
    public void testBadReceiverOnly() {
        DrawSelection selection = selection(giver(PERSON_1, RECEIVER_ONLY_1), giver(RECEIVER_ONLY_1, PERSON_1));

        validateBadSelection(requirements(PERSON_1, GIVER_ONLY_1), selection);
    }

    private void validateBadSelection(final DrawSelection selection) {
        validateBadSelection(REQUIREMENTS, selection);
    }

    private void validateBadSelection(final DrawRequirements requirements, final DrawSelection selection) {
        assertThrows(SantaException.class, () -> DrawValidationTool.validate(requirements, selection));
    }

    private static DrawRequirements requirements(final Person... participants) {
        return new DrawRequirements(listOf(participants), emptyList());
    }

    private static DrawRequirements requirements(final List<Restriction> restrictions, final Person... participants) {
        return new DrawRequirements(listOf(participants), restrictions);
    }

    private DrawSelection selection(final GiverAssignment... givers) {
        return new DrawSelection(listOf(givers));
    }

    private GiverAssignment giver(final Person from, final Person to) {
        return new GiverAssignment(from, to);
    }
}
