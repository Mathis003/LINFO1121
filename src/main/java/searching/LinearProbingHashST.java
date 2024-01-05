package searching;

/**
 * With the given partial implementation of LinearProbingHashST, we ask you to
 * implement the resize, get and put methods
 * You are not allowed to use already existing classes and methods from Java
 */
public class LinearProbingHashST<Key, Value>
{
    private static final int INIT_CAPACITY = 4;

    // Please do not add/remove variables/modify their visibility.
    protected int n;         // number of key-value pairs in the symbol table
    protected int m;         // size of linear probing table
    protected Key[] keys;    // the keys
    protected Value[] vals;  // the values

    /**
     * Initializes an empty symbol table.
     */
    public LinearProbingHashST()
    {
        this(INIT_CAPACITY);
    }

    /**
     * Initializes an empty symbol table with the specified initial capacity.
     *
     * @param capacity the initial capacity
     */
    public LinearProbingHashST(int capacity)
    {
        m = capacity;
        n = 0;
        keys = (Key[])   new Object[m];
        vals = (Value[]) new Object[m];
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() { return n; }

    /**
     * Returns the current capacity of the table
     *
     * @return the current capacity of the table
     */
    public int capacity() { return m; }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() { return size() == 0; }

    /**
     * Returns true if this symbol table contains the specified key.
     *
     * @param  key the key
     * @return {@code true} if this symbol table contains {@code key};
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key)
    {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    // hash function for keys - returns value between 0 and M-1
    protected int hash(Key key) { return (key.hashCode() & 0x7fffffff) % m; }

    /**
     * Resizes the hash table to the given capacity by re-hashing all of the keys
     *
     * @param capacity the capacity
     */
    // BEGIN : STUDENT
    protected void resize(int capacity)
    {
        this.m = capacity;
        n = 0;
        Key[] oldKeys = this.keys;
        Value[] oldValues = this.vals;

        this.keys = (Key[])   new Object[this.m];
        this.vals = (Value[]) new Object[this.m];

        for (int i = 0; i < oldKeys.length; i++)
        {
            if (oldKeys[i] != null) put(oldKeys[i], oldValues[i]);
        }
    }
    // END : STUDENT

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * The load factor should never exceed 50% so make sure to resize correctly
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    // BEGIN : STUDENT
    public void put(Key key, Value val)
    {
        if (key == null || val == null) throw new IllegalArgumentException();

        if (this.m / 2 <= this.n) resize(2 * this.m);

        int location = hash(key);

        while (this.keys[location] != null && !this.keys[location].equals(key)) location = (location + 1) % this.m;

        if (this.keys[location] == null || !this.keys[location].equals(key)) this.n++;

        this.keys[location] = key;
        this.vals[location] = val;
    }
    // END : STUDENT

    /**
     * Returns the value associated with the specified key.
     * @param key the key
     * @return the value associated with {@code key};
     *         {@code null} if no such value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    // BEGIN : STUDENT
    public Value get(Key key)
    {
        if (key == null) throw new IllegalArgumentException();

        int location = hash(key);
        while (this.keys[location] != null && !this.keys[location].equals(key)) location = (location + 1) % this.m;
        return this.vals[location];
    }
    // END : STUDENT

    /**
     * Returns all keys in this symbol table
     */
    public Object[] keys()
    {
        Object[] exportKeys = new Object[n];
        int j = 0;
        for (int i = 0; i < m; i++)
        {
            if (keys[i] != null) exportKeys[j++] = keys[i];
        }
        return exportKeys;
    }
}