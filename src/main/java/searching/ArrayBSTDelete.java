/*
My solution is more complex than expected.
Check first the solution's teacher.
That was the Mid-Term exercise (difficult).
 */

package searching;

import java.util.ArrayList;

/**
 *  We study a BST representation using an arrayList internal representation.
 *  Rather than using a Linked-Node Data-Structure, the left/right children
 *  links will be encoded with indices in array lists.
 *  More exactly, in this data-structure each node
 *  is represented by an index i (that corresponds to the ith added element)
 *  The left  node of node i, if any, is located
 *        at index idxLeftNode.get(i) otherwise idxLeftNode.get(i) == NONE
 *  The right node of node i, if any is located
 *       at index idxRightNode.get(i) otherwise idxRightNode.get(i) == NONE
 *
 *  The tree below is the result of putting (key,value) pairs (12,A),(15,B),(5,C),(8,d),(1,E) (in this order)
 *
 *                12(A)
 *                / \
 *               /   \
 *             5(C)  15(B)
 *             / \
 *          1(E)  8(D)
 *
 *   The state of internal array list after those put operations is
 *    values:        A,  B, C, D, E
 *    keys:         12, 15, 5, 8, 1
 *    idxLeftNode:   2, -1, 4,-1,-1
 *    idxRightNode:  1, -1, 3,-1,-1
 *
 * We ask you to augment the data-structure with a *delete* method.
 *
 * ⚠️There is one strong requirement on your design ⚠️:
 *   The memory consumed since the creation of the BST is in O(K)
 *   where K is the number different keys used
 *   since the creation of the data-structure.
 *   More concretely, the size of the "keys" vector cannot be more than K
 *
 *  You are
 *  - not allowed to import or use other java classes than ArrayList or the ones already present.
 *  - allowed to modify all the existing methods and add instance variables
 *
 *  HINT 💡: Consider reusing key locations of previously deleted keys without
 *           trying to reorganize the BST.
 */
public class ArrayBSTDelete<Key extends Comparable<Key>, Value>
{
    // The next four array lists should always have exactly equal sizes
    public ArrayList<Key> keys;
    public ArrayList<Value> values;

    public ArrayList<Integer> idxLeftNode;  // idxLeftNode[i] = index of left node of i
    public ArrayList<Integer> idxRightNode; // idxRightNode[i] = index of right node of i

    final int NONE = -1;

    public ArrayBSTDelete()
    {
        keys = new ArrayList<>();
        values = new ArrayList<>();
        idxLeftNode = new ArrayList<>();
        idxRightNode = new ArrayList<>();
    }

    private void addNode(Key key, Value val)
    {
        keys.add(key);
        values.add(val);
        idxLeftNode.add(NONE);
        idxRightNode.add(NONE);
    }

    /**
     * Insert the entry in the BST, replace the value if the key is already present
     * in O(h) where h is the height of the tree
     * @param key a key that is present or not in the BST
     * @param val the value that must be attached to this key
     * @return true if the key was added, false if already present and the value has simply been erased
     */
    public boolean put(Key key, Value val)
    {
        if (!values.isEmpty())
        {
            int i = 0;
            ArrayList<Integer> idxChild;
            do {
                int cmp = key.compareTo(keys.get(i));
                if (cmp == 0) { values.set(i,val); return false; }
                else
                {
                    idxChild = cmp < 0 ? idxLeftNode : idxRightNode;
                    int next = idxChild.get(i);
                    if (next == NONE) idxChild.set(i,keys.size());
                    i = next;
                }
            } while (i != NONE);
        }
        addNode(key,val);
        return true;
    }

    /**
     * Return the value attached to this key, null if the key is not present
     * in O(h) where h is the height of the tree
     * @param key the key to search
     * @return the value attached to this key, null if the key is not present
     */
    public Value get(Key key)
    {
        int i = getNodeIndex(key);
        if (i == NONE) return null;
        return values.get(i);
    }

    /**
     * Retrieves the node index with key if present,
     * NONE if not present
     */
    private int getNodeIndex(Key key)
    {
        int i = 0;
        while (i != NONE)
        {
            int cmp = key.compareTo(keys.get(i));
            if (cmp == 0)     return i;
            else if (cmp < 0) i = idxLeftNode.get(i);
            else              i = idxRightNode.get(i);
        }
        return NONE;
    }

    /**
     * Delete the key (and its associated value) from the BST.
     * @param key the key to delete
     * @return true if the key was deleted, false if the key was not present
     */
    // BEGIN : STUDENT
    public boolean delete(Key key)
    {
        int idx = getNodeIndex(key);
        if (idx == NONE) return false;

        int parentIdx = getParentIdx(key);
        int idx_leftChild = idxLeftNode.get(idx);
        int idx_RightChild = idxRightNode.get(idx);
        int minRightTreeIdx = NONE;
        boolean removeNodeToDeleteIdx = true;

        if ((idx_leftChild == NONE) && (idx_RightChild == NONE))  setParentLink(idx, parentIdx, NONE);
        else if (idx_RightChild == NONE)                          setParentLink(idx, parentIdx, idx_leftChild);
        else if (idx_leftChild == NONE)                           setParentLink(idx, parentIdx, idx_RightChild);
        else
        {
            int rootRightTreeIdx = idxRightNode.get(idx);
            minRightTreeIdx = getMinIdx(rootRightTreeIdx);
            int parent_minRightTreeIdx = getParentIdx(keys.get(minRightTreeIdx));
            keys.set(idx, keys.get(minRightTreeIdx));
            values.set(idx, values.get(minRightTreeIdx));
            int rightSubTree_minIdx = idxRightNode.get(minRightTreeIdx);
            setParentLink(minRightTreeIdx, parent_minRightTreeIdx, rightSubTree_minIdx);
            removeNodeToDeleteIdx = false;
        }

        removeElement(removeNodeToDeleteIdx ? idx : minRightTreeIdx);
        updateIdx_Array(removeNodeToDeleteIdx ? idx : minRightTreeIdx);
        return true;
    }

    private void removeElement(int idx)
    {
        idxLeftNode.remove(idx);
        idxRightNode.remove(idx);
        keys.remove(idx);
        values.remove(idx);
    }

    private void setParentLink(int nodeToDeleteIdx, int parentIdx, int newLinkedNodeIdx)
    {
        if (idxLeftNode.get(parentIdx) == nodeToDeleteIdx)  idxLeftNode.set(parentIdx, newLinkedNodeIdx);
        else                                                idxRightNode.set(parentIdx, newLinkedNodeIdx);
    }

    private void updateIdx_Array(int idx)
    {
        int leftIdx;
        int rightIdx;
        for (int j = 0; j < idxLeftNode.size(); j++)
        {
            leftIdx = idxLeftNode.get(j);
            if (leftIdx > idx) idxLeftNode.set(j, leftIdx - 1);

            rightIdx = idxRightNode.get(j);
            if (rightIdx > idx) idxRightNode.set(j, rightIdx - 1);
        }
    }

    private int getMinIdx(int rootIdx)
    {
        while (idxLeftNode.get(rootIdx) != NONE) rootIdx = idxLeftNode.get(rootIdx);
        return rootIdx;
    }

    private int getParentIdx(Key key)
    {
        int i = 0;
        int parentIdx = NONE;
        while (i != NONE)
        {
            int cmp = key.compareTo(keys.get(i));
            if (cmp == 0)     return parentIdx;
            else if (cmp < 0) { parentIdx = i; i = idxLeftNode.get(i); }
            else              { parentIdx = i; i = idxRightNode.get(i); }
        }
        return NONE;
    }
    // END : STUDENT

    public static void main(String[] args)
    {
        ArrayBSTDelete<Integer,Character> bst = new ArrayBSTDelete<>();

        bst.put(12,'A');
        bst.put(15,'B');
        bst.put(5,'C');
        bst.put(8,'D');
        bst.put(1,'E');

        for (int i = 0; i < 10; i++)
        {
            bst.delete(15);
            bst.put(15,'C');
        }
    }
}