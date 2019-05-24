package matcher;

import org.junit.Assert;
import org.junit.Test;
import models.NFA;
import parser.NFABuilder;

public class RegexMatcherTest {

    @Test
    public void testSimpleMatch() {
        NFA nfa = NFABuilder.buildNFA("a");

        Assert.assertTrue(RegexMatcher.match(nfa, "a"));
        Assert.assertFalse(RegexMatcher.match(nfa, "b"));
        Assert.assertFalse(RegexMatcher.match(nfa, "aa"));
        Assert.assertFalse(RegexMatcher.match(nfa, ""));
    }

    @Test
    public void testEmptyRegex() {
        NFA nfa = NFABuilder.buildNFA("");

        Assert.assertTrue(RegexMatcher.match(nfa, ""));
        Assert.assertFalse(RegexMatcher.match(nfa, "b"));
    }

    @Test
    public void testConcatenation() {
        NFA nfa = NFABuilder.buildNFA("ab.");

        Assert.assertTrue(RegexMatcher.match(nfa, "ab"));
        Assert.assertFalse(RegexMatcher.match(nfa, "b"));
        Assert.assertFalse(RegexMatcher.match(nfa, "a"));
        Assert.assertFalse(RegexMatcher.match(nfa, "abb"));
    }

    @Test
    public void testUnion() {
        NFA nfa = NFABuilder.buildNFA("ab|");

        Assert.assertTrue(RegexMatcher.match(nfa, "a"));
        Assert.assertTrue(RegexMatcher.match(nfa, "b"));
        Assert.assertFalse(RegexMatcher.match(nfa, "ab"));
        Assert.assertFalse(RegexMatcher.match(nfa, "aa"));
    }

    @Test
    public void testKleeneStar() {
        NFA nfa = NFABuilder.buildNFA("a*");

        Assert.assertTrue(RegexMatcher.match(nfa, "a"));
        Assert.assertTrue(RegexMatcher.match(nfa, ""));
        Assert.assertTrue(RegexMatcher.match(nfa, "aaaa"));
        Assert.assertFalse(RegexMatcher.match(nfa, "aaaaab"));
    }

    @Test
    public void testCombinedOperators() {
        NFA nfa = NFABuilder.buildNFA("ab|*c."); //Corresponding regex: (a|b)*c

        Assert.assertTrue(RegexMatcher.match(nfa, "c"));
        Assert.assertTrue(RegexMatcher.match(nfa, "ac"));
        Assert.assertTrue(RegexMatcher.match(nfa, "bc"));
        Assert.assertTrue(RegexMatcher.match(nfa, "ababbaac"));
        Assert.assertFalse(RegexMatcher.match(nfa, "aaaaab"));
    }

    @Test
    public void testCombinedOperators2() {
        NFA nfa = NFABuilder.buildNFA("abcd.*.*.e."); //Corresponding regex: a(b(cd)*)*e

        Assert.assertTrue(RegexMatcher.match(nfa, "ae"));
        Assert.assertTrue(RegexMatcher.match(nfa, "abe"));
        Assert.assertTrue(RegexMatcher.match(nfa, "abcde"));
        Assert.assertTrue(RegexMatcher.match(nfa, "abcdcdcde"));
        Assert.assertTrue(RegexMatcher.match(nfa, "abcdbcde"));
        Assert.assertFalse(RegexMatcher.match(nfa, "bcde"));
        Assert.assertFalse(RegexMatcher.match(nfa, "abcd"));
        Assert.assertFalse(RegexMatcher.match(nfa, "abcdcdce"));
        Assert.assertFalse(RegexMatcher.match(nfa, "acde"));
    }
}
