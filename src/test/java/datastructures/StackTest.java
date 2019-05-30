package datastructures;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StackTest {
    private Stack<String> stack;

    @Before
    public void init() {
        stack = new Stack<>();
    }

    @Test
    public void testStackIsEmpty() {
        Assert.assertTrue(stack.isEmpty());
    }

    @Test
    public void testStackIsNotEmpty() {
        stack.push("one");
        Assert.assertFalse(stack.isEmpty());
    }

    @Test
    public void testPeekReturnsNullWhenStackEmpty() {
        Assert.assertNull(stack.peek());
    }

    @Test
    public void testPeekReturnsItemWithoutRemoving() {
        stack.push("one");
        stack.push("two");

        Assert.assertEquals("two", stack.peek());
        Assert.assertEquals("two", stack.peek());
        Assert.assertEquals("two", stack.peek());
    }

    @Test
    public void testPopReturnsNullWhenStackEmpty() {
        Assert.assertNull(stack.pop());
    }

    @Test
    public void testPopRemovesAndReturnsItem() {
        stack.push("one");
        stack.push("two");

        Assert.assertEquals("two", stack.pop());
        Assert.assertEquals("one", stack.pop());
        Assert.assertNull(stack.pop());
    }
}
