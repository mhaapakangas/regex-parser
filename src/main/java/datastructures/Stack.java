package datastructures;

/**
 * A stack data structure
 * @param <T> data type stored in the stack
 */
public class Stack<T> {
    private StackItem<T> head;

    /**
     * Add an item on top of the stack.
     * @param data the item to add
     */
    public void push(T data) {
        StackItem<T> item = new StackItem<>(data);
        item.setNext(head);
        head = item;
    }

    /**
     * Remove the top item from the stack.
     * @return the removed item
     */
    public T pop() {
        if (head == null) {
            return null;
        }
        StackItem<T> previousHead = head;
        head = head.getNext();
        return previousHead.getItem();
    }

    /**
     * Return the top item of the stack.
     * @return the top item
     */
    public T peek() {
        if (head == null) {
            return null;
        }
        return head.getItem();
    }

    /**
     * Check if the stack is empty.
     * @return true if the stack is empty, otherwise false
     */
    public boolean isEmpty() {
        return head == null;
    }
}
