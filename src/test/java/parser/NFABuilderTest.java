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
        Assert.assertEquals(Character.valueOf('a'), result.getStart().getSymbol());

        State state = result.getStart().getTransition().getEpsilonTransitions()[0];
        Assert.assertNotNull(state.getTransition());
        Assert.assertEquals(Character.valueOf('b'), state.getSymbol());

        State endState = state.getTransition();
        Assert.assertTrue(endState.isEnd());
    }

    @Test
    public void testNFAForUnion() {
        NFA result = NFABuilder.buildNFA("ab|");

        State stateA = result.getStart().getEpsilonTransitions()[0];
        Assert.assertNotNull(stateA);
        Assert.assertNotNull(stateA.getTransition());
        Assert.assertEquals(Character.valueOf('a'), stateA.getSymbol());

        State endStateA  = stateA.getTransition().getEpsilonTransitions()[0];
        Assert.assertNotNull(endStateA);
        Assert.assertTrue(endStateA.isEnd());

        State stateB = result.getStart().getEpsilonTransitions()[1];
        Assert.assertNotNull(stateB);
        Assert.assertNotNull(stateB.getTransition());
        Assert.assertEquals(Character.valueOf('b'), stateB.getSymbol());

        State endStateB  = stateB.getTransition().getEpsilonTransitions()[0];
        Assert.assertEquals(endStateA, endStateB);
    }

    @Test
    public void testNFAForKleeneStar() {
        NFA result = NFABuilder.buildNFA("a*");

        State endState = result.getStart().getEpsilonTransitions()[0];
        Assert.assertNotNull(endState);
        Assert.assertTrue(endState.isEnd());
        Assert.assertEquals(result.getEnd(), endState);

        State stateA = result.getStart().getEpsilonTransitions()[1];
        Assert.assertNotNull(stateA);
        Assert.assertNotNull(stateA.getTransition());
        Assert.assertEquals(Character.valueOf('a'), stateA.getSymbol());

        State loopTransition = stateA.getTransition().getEpsilonTransitions()[0];
        Assert.assertEquals(stateA, loopTransition);

        State endStateA  = stateA.getTransition().getEpsilonTransitions()[1];
        Assert.assertEquals(result.getEnd(), endStateA);
    }
}
