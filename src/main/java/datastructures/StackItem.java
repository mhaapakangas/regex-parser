package datastructures;

/**
 * An item stored in a stack. Contains the stored data and a reference
 * to the next item in the stack.
 * @param <T> stored data type
 */
public class StackItem<T> {
    private T item;
    private StackItem<T> next;

    public StackItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public StackItem<T> getNext() {
        return next;
    }

    public void setNext(StackItem<T> next) {
        this.next = next;
    }
}
