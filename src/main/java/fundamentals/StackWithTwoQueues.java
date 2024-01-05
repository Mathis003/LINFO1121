package fundamentals;

import java.util.ArrayDeque;
import java.util.EmptyStackException;
import java.util.Queue;

/**
 * Author: Pierre Schaus and Auguste Burlats
 * Implement the abstract data type stack using two queues
 * You are not allowed to modify or add the instance variables,
 * only the body of the methods
 */
public class StackWithTwoQueues<E>
{
    Queue<E> queue1;
    Queue<E> queue2;

    public StackWithTwoQueues()
    {
        queue1 = new ArrayDeque();
        queue2 = new ArrayDeque();
    }

    // BEGIN : STUDENT
    /**
     * Looks at the object at the top of this stack
     * without removing it from the stack
     */
    public boolean empty() { return queue1.isEmpty(); }

    /**
     * Returns the first element of the stack, without removing it from the stack
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E peek() throws EmptyStackException
    {
        if (empty()) throw new EmptyStackException();
        return queue1.peek();
    }

    /**
     * Remove the first element of the stack and returns it
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E pop() throws EmptyStackException
    {
        if (empty()) throw new EmptyStackException();
        return queue1.poll();
    }

    /**
     * Adds an element to the stack
     *
     * @param item the item to add
     */
    public void push(E item)
    {
        while (!queue1.isEmpty()) queue2.add(queue1.poll());
        queue1.add(item);
        while (!queue2.isEmpty()) queue1.add(queue2.poll());
    }
    // END : STUDENT
}