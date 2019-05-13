package parser;

import org.junit.Assert;
import org.junit.Test;

public class InputConverterTest {
    @Test
    public void addConcatenationChar1() {
        Assert.assertEquals("a.b.c", InputConverter.addConcatenationChar("abc"));
    }

    @Test
    public void addConcatenationChar2() {
        Assert.assertEquals("a.b*", InputConverter.addConcatenationChar("ab*"));
    }

    @Test
    public void addConcatenationChar3() {
        Assert.assertEquals("a.(b.c)", InputConverter.addConcatenationChar("a(bc)"));
    }

    @Test
    public void addConcatenationChar4() {
        Assert.assertEquals("a|b", InputConverter.addConcatenationChar("a|b"));
    }

    @Test
    public void addConcatenationChar5() {
        Assert.assertEquals("(a|b)*.c", InputConverter.addConcatenationChar("(a|b)*c"));
    }

    @Test
    public void addConcatenationChar6() {
        Assert.assertEquals("a.(b.b)*.a", InputConverter.addConcatenationChar("a(bb)*a"));
    }

    @Test
    public void addConcatenationChar7() {
        Assert.assertEquals("a", InputConverter.addConcatenationChar("a"));
    }

    @Test
    public void addConcatenationChar8() {
        Assert.assertEquals("(a)", InputConverter.addConcatenationChar("(a)"));
    }

    @Test
    public void addConcatenationChar9() {
        Assert.assertEquals("a.(b.(c.d)*)*.e", InputConverter.addConcatenationChar("a(b(cd)*)*e"));
    }

    @Test
    public void toPostfixNotation1() {
        Assert.assertEquals("ab.c.", InputConverter.toPostfixNotation("a.b.c"));
    }

    @Test
    public void toPostfixNotation2() {
        Assert.assertEquals("ab*.", InputConverter.toPostfixNotation("a.b*"));
    }

    @Test
    public void toPostfixNotation3() {
        Assert.assertEquals("abc..", InputConverter.toPostfixNotation("a.(b.c)"));
    }

    @Test
    public void toPostfixNotation4() {
        Assert.assertEquals("ab|", InputConverter.toPostfixNotation("a|b"));
    }

    @Test
    public void toPostfixNotation5() {
        Assert.assertEquals("ab|*c.", InputConverter.toPostfixNotation("(a|b)*.c"));
    }

    @Test
    public void toPostfixNotation6() {
        Assert.assertEquals("abb.*.a.", InputConverter.toPostfixNotation("a.(b.b)*.a"));
    }

    @Test
    public void toPostfixNotation7() {
        Assert.assertEquals("a", InputConverter.toPostfixNotation("a"));
    }

    @Test
    public void toPostfixNotation8() {
        Assert.assertEquals("a", InputConverter.toPostfixNotation("(a)"));
    }

    @Test
    public void toPostfixNotation9() {
        Assert.assertEquals("abcd.*.*.e.", InputConverter.toPostfixNotation("a.(b.(c.d)*)*.e"));
    }
}
