/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import org.junit.jupiter.api.Test;

public class MatchingEngineTest {
    private final MatchingEngineTestTool tool = new MatchingEngineTestTool();

    @Test
    public void testEmpty() {
        tool.setNodes();
        tool.setRestrictions(new NodePairSet());
        tool.addExpectation(new NodePairSet());
        tool.performValidation();
    }

    @Test
    public void testMinimal() {
        tool.setNodes(Node.A, Node.B);
        tool.setRestrictions(new NodePairSet());
        tool.addExpectation(new NodePairSet().add(Node.A, Node.B).add(Node.B, Node.A));
        tool.performValidation();
    }

    @Test
    public void testMinimalWithRestriction() {
        tool.setNodes(Node.A, Node.B);
        tool.setRestrictions(new NodePairSet().add(Node.A, Node.B));
        tool.setEmptyExpectations();
        tool.performValidation();
    }

    @Test
    public void testThreeFreeNodes() {
        tool.setNodes(Node.A, Node.B, Node.C);
        tool.setRestrictions(new NodePairSet());
        tool.addExpectation(new NodePairSet().add(Node.A, Node.B).add(Node.B, Node.C).add(Node.C, Node.A));
        tool.addExpectation(new NodePairSet().add(Node.A, Node.C).add(Node.B, Node.A).add(Node.C, Node.B));
        tool.performValidation();
    }

    @Test
    public void testThreeRestrictedNodes() {
        tool.setNodes(Node.A, Node.B, Node.C);
        tool.setRestrictions(new NodePairSet().add(Node.A, Node.C));
        tool.addExpectation(new NodePairSet().add(Node.A, Node.B).add(Node.B, Node.C).add(Node.C, Node.A));
        tool.performValidation();
    }
}
