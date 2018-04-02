/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import io.github.santulator.model.Person;
import org.junit.jupiter.api.Test;

public class MatchingEngineTest {
    private static final Person A = new Person("A");

    private static final Person B = new Person("B");

    private static final Person C = new Person("C");

    private final MatchingEngineTestTool tool = new MatchingEngineTestTool();

    @Test
    public void testEmpty() {
        tool.setParticipants();
        tool.setRestrictions(new PairSetTool());
        tool.addExpectation(new PairSetTool());
        tool.performValidation();
    }

    @Test
    public void testMinimal() {
        tool.setParticipants(A, B);
        tool.setRestrictions(new PairSetTool());
        tool.addExpectation(new PairSetTool().add(A, B).add(B, A));
        tool.performValidation();
    }

    @Test
    public void testMinimalWithRestriction() {
        tool.setParticipants(A, B);
        tool.setRestrictions(new PairSetTool().add(A, B));
        tool.setEmptyExpectations();
        tool.performValidation();
    }

    @Test
    public void testThreeFreePeople() {
        tool.setParticipants(A, B, C);
        tool.setRestrictions(new PairSetTool());
        tool.addExpectation(new PairSetTool().add(A, B).add(B, C).add(C, A));
        tool.addExpectation(new PairSetTool().add(A, C).add(B, A).add(C, B));
        tool.performValidation();
    }

    @Test
    public void testThreeRestrictedPeople() {
        tool.setParticipants(A, B, C);
        tool.setRestrictions(new PairSetTool().add(A, C));
        tool.addExpectation(new PairSetTool().add(A, B).add(B, C).add(C, A));
        tool.performValidation();
    }
}
