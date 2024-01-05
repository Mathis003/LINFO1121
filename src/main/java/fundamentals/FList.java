package fundamentals;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Author: Pierre Schaus
 *
 * Functional Programming is an increasingly important programming paradigm.
 * In this programming paradigm, data structures are immutable.
 * We are interested here in the implementation of an immutable list
 * called FList allowing to be used in a functional framework.
 * Look at the main method for a small example using this functional list
 *
 * Complete the implementation to pass the tests
 *
 * @param <A>
 */
public abstract class FList<A> implements Iterable<A>
{
    public static void main(String[] args)
    {
        FList<Integer> list = FList.nil();

        for (int i = 0; i < 10; i++) list = list.cons(i);

        list = list.map(i -> i+1);
        // will print 10,9,...,1
        for (Integer i: list) System.out.println(i);

        list = list.filter(i -> i%2 == 0);
        // will print 10,...,6,4,2
        for (Integer i: list) System.out.println(i);
    }

    // return true if the list is not empty, false otherwise
    public final boolean isNotEmpty() { return this instanceof Cons; }

    // return true if the list is empty (Nil), false otherwise
    public final boolean isEmpty() { return this instanceof Nil; }

    // return the length of the list
    public final int length()
    {
        // BEGIN : STUDENT
        int length = 0;
        A head = head();
        while (head != nil()) { head = tail().head(); length++; }
        return length;
        // END : STUDENT
    }

    // return the head element of the list
    public abstract A head();

    // return the tail of the list
    public abstract FList<A> tail();

    // creates an empty list
    public static <A> FList<A> nil() { return (Nil<A>) Nil.INSTANCE; }


    public final FList<A> cons(final A a) { return new Cons(a, this); }

    // return a list on which each element has been applied function f
    public final <B> FList<B> map(Function<A,B> f)
    {
        // BEGIN : STUDENT
        if (isEmpty()) return nil();
        return tail().map(f).cons(f.apply(head()));
        // END : STUDENT
    }

    // return a list on which only the elements that satisfies predicate are kept
    public final FList<A> filter(Predicate<A> f)
    {
        // BEGIN : STUDENT
        if (isEmpty()) return nil();

        if (f.test(head())) return tail().filter(f).cons(head());
        else                return tail().filter(f);
        // END : STUDENT
    }

    // return an iterator on the element of the list
    public Iterator<A> iterator()
    {
        return new Iterator<A>()
        {
            // BEGIN : STUDENT
            FList<A> current = FList.this;

            public boolean hasNext() { return current.isNotEmpty(); }

            public A next()
            {
                if (!hasNext()) { throw new NoSuchElementException(); }
                A elemToReturn = current.head();
                current = current.tail();
                return elemToReturn;
            }

            public void remove() { throw new UnsupportedOperationException(); }
            // END : STUDENT
        };
    }

    private static final class Nil<A> extends FList<A>
    {
        public static final Nil<Object> INSTANCE = new Nil();

        // BEGIN : STUDENT
        @Override
        public A head() { throw new IllegalArgumentException(); }

        @Override
        public FList<A> tail() { throw new IllegalArgumentException(); }
        // END : STUDENT
    }

    private static final class Cons<A> extends FList<A>
    {
        // BEGIN : STUDENT
        private A head;
        private FList<A> tail;

        Cons(A a, FList<A> tail)
        {
            this.head = a;
            this.tail = tail;
        }

        @Override
        public A head() { return this.head; }

        @Override
        public FList<A> tail() { return this.tail; }
        // END : STUDENT
    }
}