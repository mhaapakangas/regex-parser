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
        nfa = NFABuilder.buildNFA("a");
        Assert.assertFalse(RegexMatcher.match(nfa, "b"));
        nfa = NFABuilder.buildNFA("a");
        Assert.assertFalse(RegexMatcher.match(nfa, "aa"));
        nfa = NFABuilder.buildNFA("a");
        Assert.assertFalse(RegexMatcher.match(nfa, ""));
    }

    @Test
    public void testEmptyRegex() {
        NFA nfa = NFABuilder.buildNFA("");
        Assert.assertTrue(RegexMatcher.match(nfa, ""));
        nfa = NFABuilder.buildNFA("");
        Assert.assertFalse(RegexMatcher.match(nfa, "b"));
    }

    @Test
    public void testConcatenation() {
        NFA nfa = NFABuilder.buildNFA("ab.");
        Assert.assertTrue(RegexMatcher.match(nfa, "ab"));
        nfa = NFABuilder.buildNFA("ab.");
        Assert.assertFalse(RegexMatcher.match(nfa, "b"));
        nfa = NFABuilder.buildNFA("ab.");
        Assert.assertFalse(RegexMatcher.match(nfa, "a"));
        nfa = NFABuilder.buildNFA("ab.");
        Assert.assertFalse(RegexMatcher.match(nfa, "abb"));
    }

    @Test
    public void testUnion() {
        NFA nfa = NFABuilder.buildNFA("ab|");
        Assert.assertTrue(RegexMatcher.match(nfa, "a"));
        nfa = NFABuilder.buildNFA("ab|");
        Assert.assertTrue(RegexMatcher.match(nfa, "b"));
        nfa = NFABuilder.buildNFA("ab|");
        Assert.assertFalse(RegexMatcher.match(nfa, "ab"));
        nfa = NFABuilder.buildNFA("ab|");
        Assert.assertFalse(RegexMatcher.match(nfa, "aa"));
    }

    @Test
    public void testKleeneStar() {
        NFA nfa = NFABuilder.buildNFA("a*");
        Assert.assertTrue(RegexMatcher.match(nfa, "a"));
        nfa = NFABuilder.buildNFA("a*");
        Assert.assertTrue(RegexMatcher.match(nfa, ""));
        nfa = NFABuilder.buildNFA("a*");
        Assert.assertTrue(RegexMatcher.match(nfa, "aaaa"));
        nfa = NFABuilder.buildNFA("a*");
        Assert.assertFalse(RegexMatcher.match(nfa, "aaaaab"));
    }

    @Test
    public void testCombinedOperators() {
        NFA nfa = NFABuilder.buildNFA("ab|*c."); //Corresponding regex: (a|b)*c
        Assert.assertTrue(RegexMatcher.match(nfa, "c"));
        nfa = NFABuilder.buildNFA("ab|*c.");
        Assert.assertTrue(RegexMatcher.match(nfa, "ac"));
        nfa = NFABuilder.buildNFA("ab|*c.");
        Assert.assertTrue(RegexMatcher.match(nfa, "bc"));
        nfa = NFABuilder.buildNFA("ab|*c.");
        Assert.assertTrue(RegexMatcher.match(nfa, "ababbaac"));
        nfa = NFABuilder.buildNFA("ab|*c.");
        Assert.assertFalse(RegexMatcher.match(nfa, "aaaaab"));
    }

    @Test
    public void testCombinedOperators2() {
        NFA nfa = NFABuilder.buildNFA("abcd.*.*.e."); //Corresponding regex: a(b(cd)*)*e
        Assert.assertTrue(RegexMatcher.match(nfa, "ae"));
        nfa = NFABuilder.buildNFA("abcd.*.*.e.");
        Assert.assertTrue(RegexMatcher.match(nfa, "abe"));
        nfa = NFABuilder.buildNFA("abcd.*.*.e.");
        Assert.assertTrue(RegexMatcher.match(nfa, "abcde"));
        nfa = NFABuilder.buildNFA("abcd.*.*.e.");
        Assert.assertTrue(RegexMatcher.match(nfa, "abcdcdcde"));
        nfa = NFABuilder.buildNFA("abcd.*.*.e.");
        Assert.assertTrue(RegexMatcher.match(nfa, "abcdbcde"));
        nfa = NFABuilder.buildNFA("abcd.*.*.e.");
        Assert.assertFalse(RegexMatcher.match(nfa, "bcde"));
        nfa = NFABuilder.buildNFA("abcd.*.*.e.");
        Assert.assertFalse(RegexMatcher.match(nfa, "abcd"));
        nfa = NFABuilder.buildNFA("abcd.*.*.e.");
        Assert.assertFalse(RegexMatcher.match(nfa, "abcdcdce"));
        nfa = NFABuilder.buildNFA("abcd.*.*.e.");
        Assert.assertFalse(RegexMatcher.match(nfa, "acde"));
    }

    @Test
    public void testRecursiveKleeneStars() {
        NFA nfa = NFABuilder.buildNFA("a**");  //Corresponding regex: (a*)*
        Assert.assertTrue(RegexMatcher.match(nfa, "a"));
        nfa = NFABuilder.buildNFA("a**");
        Assert.assertTrue(RegexMatcher.match(nfa, ""));
        nfa = NFABuilder.buildNFA("a**");
        Assert.assertFalse(RegexMatcher.match(nfa, "b"));
    }
}
