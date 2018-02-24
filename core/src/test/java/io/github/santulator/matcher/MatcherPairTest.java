/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.matcher;

import io.github.santulator.test.AbstractBeanTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatcherPairTest extends AbstractBeanTest<MatcherPair<String>> {
    private static final String LEFT_1 = "LEFT_1";

    private static final String RIGHT_1 = "RIGHT_1";

    private static final String RIGHT_2 = "RIGHT_2";

    @Test
    public void testGetLeft() {
        MatcherPair<String> p = new MatcherPair<>(LEFT_1, RIGHT_1);

        assertEquals(LEFT_1, p.getLeft(), "Left");
    }

    @Test
    public void testGetRight() {
        MatcherPair<String> p = new MatcherPair<>(LEFT_1, RIGHT_1);

        assertEquals(RIGHT_1, p.getRight(), "Right");
    }

    @Override
    protected MatcherPair<String> buildPrimary() {
        return new MatcherPair<>(LEFT_1, RIGHT_1);
    }

    @Override
    protected MatcherPair<String> buildSecondary() {
        return new MatcherPair<>(LEFT_1, RIGHT_2);
    }
}
