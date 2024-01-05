package searching;

import java.util.HashMap;

/**
 * We are interested in the implementation of an LRU cache,
 * i.e. a (hash)-map of limited capacity where the addition of
 * a new entry might induce the suppression of the Least Recently Used (LRU)
 * entry if the maximum capacity is exceeded.
 *
 * Your LRU cache implements the same two methods as a MAP :
 * - put(key, elem) inserts the given element in the cache,
 *      this element becomes the most recently used element
 *      and if needed (the cache is full and the key not yet present),
 *      the least recently used element is removed.
 * - get(key) returns the entry with the given key from the cache,
 *      this element becomes the most recently used element
 *
 * These methods need to be implemented with an expected time complexity of O(1).
 * You are free to choose the type of data structure that you consider
 * to best support this cache. You can also use data-structures from Java.
 *
 * Hint for your implementation:
 *       Use a doubly linked list to store the elements from the least
 *       recently used (head) to the most recently used (tail).
 *       If needed the element to suppress is the head of the list.
 *
 *       Use java.util.HashMap with the <key,node> where node is a reference to the node
 *       in the doubly linked list.
 *
 *       Of course, at every put/get the list will need to be updated so that
 *       the "accessed node" is placed at the end of the list.
 *
 *       Feel free to use existing java classes.
 *
 *  Example of usage of an LRU cache with capacity of 3:
 *  // step 0:
 *  put(A,7)  // map{(A,7)}  A is the LRU
 *  // step 1:
 *  put(B,10) // map{(A,7),(B,10)}  A is the LRU
 *  // step 2:
 *  put(C,5)  // map{(A,7),(B,10),(C,5)}  A is the LRU
 *  // step 3:
 *  put(D,8)  // map{(B,10),(C,5),(D,8)}  A is suppressed, B is the LRU
 *  // step 4:
 *  get(B)    // C is the LRU
 *  // step 5
 *  put(E,9)  // map{(B,10),(D,8),(E,9)} D is the LRU
 *  // step 6
 *  put(D,3)  // map{(B,10),(D,3),(E,9)} B is the LRU
 *  // step 7
 *  get(B)    // map{(B,10),(D,3),(E,9)} E is the LRU
 *  // step 8
 *  put(A,2)  // map{(B,10),(D,3),(A,2)} D is the LRU
 *
 *  Feel free to use existing java classes from Java
 */
public class LRUCache<K, V>
{
    private int capacity;
    HashMap<K, Node<K, V>> map;
    Node<K, V> first;
    Node<K, V> last;

    public LRUCache(int capacity)
    {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.first = null;
        this.last = null;
    }

    // BEGIN : STUDENT
    public V get(K key)
    {
        Node<K, V> node = map.getOrDefault(key, null);
        if (node == null) return null;

        remove(node);
        addFront(node);

        return node.value;
    }
    // END : STUDENT

    // BEGIN : STUDENT
    public void put(K key, V value)
    {
        Node<K, V> node = map.getOrDefault(key, null);

        if (node != null)
        {
            node.value = value;

            remove(node);
            addFront(node);
        }
        else
        {
            Node n = new Node(key, value);

            addFront(n);
            map.put(key, n);

            if (map.size() > capacity)
            {
                map.remove(this.last.key);
                remove(this.last);
            }
        }
    }
    // END : STUDENT

    // BEGIN : STUDENT
    private void remove(Node node)
    {
        if (node.prev != null) node.prev.next = node.next;
        else this.first = node.next;

        if (node.next != null) node.next.prev = node.prev;
        else this.last = node.prev;
    }

    private void addFront(Node node)
    {
        node.next = this.first;
        node.prev = null;

        if (this.first != null) this.first.prev = node;

        this.first = node;

        if (this.last == null) this.last = this.first;
    }
    // END : STUDENT

    // BEGIN : STUDENT
    // To Debug Only
    private void printLinkedList()
    {
        Node<K, V> curr = this.first;
        while (curr != null)
        {
            System.out.println("key : " + curr.key + "    value : " + curr.value);
            curr = curr.next;
        }
    }
    // END : STUDENT

    // BEGIN : STUDENT
    private static class Node<K, V>
    {
        K key;
        V value;
        Node<K, V> next;
        Node<K, V> prev;

        private Node(K key, V value)
        {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
        }

        private K getKey() { return this.key; }

        private V getValue() { return this.value; }
    }
    // END : STUDENT
}