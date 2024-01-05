package fundamentals;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author Pierre Schaus
 *
 * We are interested in the implementation of a circular simply linked list,
 * i.e. a list for which the last position of the list refers, as the next position,
 * to the first position of the list.
 *
 * The addition of a new element (enqueue method) is done at the end of the list and
 * the removal (remove method) is done at a particular index of the list.
 *
 * A (single) reference to the end of the list (last) is necessary to perform all operations on this queue.
 *
 * You are therefore asked to implement this circular simply linked list by completing the class see (TODO's)
 * Most important methods are:
 *
 * - the enqueue to add an element;
 * - the remove method [The exception IndexOutOfBoundsException is thrown when the index value is not between 0 and size()-1];
 * - the iterator (ListIterator) used to browse the list in FIFO.
 *
 * @param <Item>
 */
public class CircularLinkedList<Item> implements Iterable<Item>
{
    private long nOp = 0;  // count the number of operations
    private int n;         // size of the stack
    private Node last;     // trailer of the list

    // helper linked list class
    private class Node
    {
        private Item item;
        private Node next;
    }

    // BEGIN : STUDENT
    public CircularLinkedList()
    {
        n = 0;
        last = new Node();
        last.next = last;
    }

    public boolean isEmpty() { return n == 0; }

    public int size() { return n; }

    private long nOp() { return nOp; }

    /**
     * Append an item at the end of the list
     * @param item the item to append
     */
    public void enqueue(Item item)
    {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (isEmpty()) last.next = last;
        else
        {
            last.next = oldLast.next;
            oldLast.next = last;
        }
        nOp++; n++;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     */
    public Item remove(int index)
    {
        nOp++;
        if (index < 0 || index >= size()) throw new ArrayIndexOutOfBoundsException();
        if (isEmpty())                    return null;

        Node toRemove;
        Item itemToRemove;

        if (index == 0)
        {
            toRemove = last.next;
            itemToRemove = toRemove.item;
            if (size() == 1) { last.next = null; last = null; }
            else               last.next = last.next.next;
        }
        else
        {
            Node previous = last;
            for (int i = 0; i < index; i++) previous = previous.next;
            toRemove = previous.next;
            itemToRemove = toRemove.item;
            previous.next = toRemove.next;
            if (index == size() - 1) last = previous;
        }
        toRemove.next = null;
        toRemove = null;
        n--;
        return itemToRemove;
    }


    /**
     * Returns an iterator that iterates through the items in FIFO order.
     * @return an iterator that iterates through the items in FIFO order.
     */
    public Iterator<Item> iterator() { return new ListIterator(); }

    /**
     * Implementation of an iterator that iterates through the items in FIFO order.
     * The iterator should implement a fail-fast strategy, that is ConcurrentModificationException
     * is thrown whenever the list is modified while iterating on it.
     * This can be achieved by counting the number of operations (nOp) in the list and
     * updating it everytime a method modifying the list is called.
     * Whenever it gets the next value (i.e. using next() method), and if it finds that the
     * nOp has been modified after this iterator has been created, it throws ConcurrentModificationException.
     */
    private class ListIterator implements Iterator<Item>
    {
        long nOpFixed;
        Node current;
        int incr;

        public ListIterator()
        {
            incr = 0;
            nOpFixed = nOp;
            if (n <= 1) current = last;
            else        current = last.next;
        }

        @Override
        public boolean hasNext() { return incr < size(); }

        @Override
        public Item next()
        {
            if (!hasNext())      throw new NoSuchElementException();
            if (nOpFixed != nOp) throw new ConcurrentModificationException();
            Item item = current.item;
            current = current.next;
            incr++;
            return item;
        }
    }
    // END : STUDENT
}