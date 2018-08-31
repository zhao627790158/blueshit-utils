package trace.api;

import java.util.Collection;
import java.util.LinkedList;

public class LimitLinkedList<E> extends LinkedList<E> {
    private int max;

    public LimitLinkedList(int max) {
        this.max = max;
    }

    public LimitLinkedList(Collection<? extends E> c, int max) {
        this(max);
        addAll(c);
    }

    @Override
    public boolean add(E element) {
        return size() < max && super.add(element);
    }

    @Override
    public void add(int index, E element) {
        if (size() < max) {
            super.add(index, element);
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return (size() + c.size() < max) && super.addAll(c);
    }

    @Override
    public void addFirst(E e) {
        if (size() < max) {
            super.addFirst(e);
        }
    }

    /**
     * Appends the specified element to the end of this list.
     * <p/>
     * <p>This method is equivalent to {@link #add}.
     *
     * @param e the element to add
     */
    @Override
    public void addLast(E e) {
        if (size() < max) {
            super.addLast(e);
        }
    }

}