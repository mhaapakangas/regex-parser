package parser;

import org.junit.Assert;
import org.junit.Test;

public class NFABuilderTest {
    @Test
    public void testNFAForEmptyRegex() {
        NFA result = NFABuilder.buildNFA("");
        State state = result.getStart().getEpsilonTransitions()[0];
        Assert.assertEquals(state, result.getEnd());
        Assert.assertNull(result.getStart().getEpsilonTransitions()[1]);
        Assert.assertNull(result.getStart().getTransition());
    }

    @Test
    public void testNFAForSymbol() {
        NFA result = NFABuilder.buildNFA("a");
        Assert.assertEquals(result.getStart().getTransition(), result.getEnd());
        Assert.assertEquals(result.getStart().getSymbol(), Character.valueOf('a'));
        Assert.assertNull(result.getStart().getEpsilonTransitions()[0]);
    }

    @Test
    public void testNFAForConcatenation() {
        NFA result = NFABuilder.buildNFA("ab.");

        Assert.assertNotNull(result.getStart().getTransition());
        Assert.assertEquals(result.getStart().getSymbol(), Character.valueOf('a'));

        State state = result.getStart().getTransition().getEpsilonTransitions()[0];
        Assert.assertNotNull(state.getTransition());
        Assert.assertEquals(state.getSymbol(), Character.valueOf('b'));

        State endState = state.getTransition();
        Assert.assertTrue(endState.isEnd());
    }
}
