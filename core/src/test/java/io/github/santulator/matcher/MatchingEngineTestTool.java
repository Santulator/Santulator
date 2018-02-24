/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import java.util.*;
import java.util.stream.Collectors;

import static io.github.santulator.core.CoreTool.listOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchingEngineTestTool {
    private List<Node> nodes;

    private Set<MatcherPair<Node>> restrictions;

    private final Set<Set<MatcherPair<Node>>> expectations = new HashSet<>();

    public void setNodes(final Node... nodes) {
        this.nodes = listOf(nodes);
    }

    public void setRestrictions(final NodePairSet restrictions) {
        this.restrictions = restrictions.toSet();
    }

    public void setEmptyExpectations() {
        expectations.add(null);
    }

    public void addExpectation(final NodePairSet expectation) {
        expectations.add(expectation.toSet());
    }

    public void performValidation() {
        MatchingEngine engine = new MatchingEngine();
        Set<Set<MatcherPair<Node>>> matches = new HashSet<>();

        for (List<Node> list : generatePermutations()) {
            ConsIterable<MatcherPair<Node>> match = engine.findMatch(nodes, list, restrictions);

            matches.add(matchSet(match));
        }

        assertEquals(expectations, matches, "Match set");
    }

    private Set<MatcherPair<Node>> matchSet(
            final ConsIterable<MatcherPair<Node>> match) {
        if (match == null) {
            return null;
        } else {
            return match.stream()
                    .collect(Collectors.toSet());
        }
    }

    private List<List<Node>> generatePermutations() {
        List<List<Node>> result = new ArrayList<>();

        addPermutations(result, new ArrayList<>(), copyOf(nodes));

        return result;
    }

    private void addPermutations(final List<List<Node>> accumulator, final List<Node> current, final Set<Node> remaining) {
        if (remaining.isEmpty()) {
            accumulator.add(current);
        } else {
            for (Node node : remaining) {
                List<Node> nextCurrent = new ArrayList<>(current);
                Set<Node> nextRemaining = copyOf(remaining);

                nextCurrent.add(node);
                nextRemaining.remove(node);
                addPermutations(accumulator, nextCurrent, nextRemaining);
            }
        }
    }

    private Set<Node> copyOf(final Collection<Node> original) {
        if (original.isEmpty()) {
            return EnumSet.noneOf(Node.class);
        } else {
            return EnumSet.copyOf(original);
        }
    }
}
