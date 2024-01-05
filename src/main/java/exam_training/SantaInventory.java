package exam_training;

import java.util.Stack;

/**
 * Santa needs to calculate the median price of gifts he will deliver this year.
 * The gift prices are stored in a unique data structure known as the 'magical Christmas search tree'.
 *
 * Each node in this tree represents a gift price (as the key) and the quantity of gifts at that price (as the value).
 * The goal is to implement two methods:
 * - put (to add gift prices to the tree) and
 * - median (to find the median price of the gifts).
 *
 * For example, consider the following magical Christmas search tree:
 *
 *
 *
 *                               [150, 4]
 *                                /     \
 *                               /       \
 *                              /         \
 *                             /           \
 *                        [100, 10]       [300, 2]
 *                                         /   \
 *                                        /     \
 *                                       /       \
 *                                      /         \
 *                                   [200, 8]     [500, 1]
 *
 * This tree represents a total of 25 gifts. The median price is the 13th price in the sorted list of gift prices.
 * In this example, the sorted list of prices is:
 * 100 (10 times), 150 (4 times), 200 (8 times), 300 (2 times), 500 (once). The 13th price in this list is 150.
 * Thus, the median price of the gifts is 150.
 *
 * Note: It's assumed that the total number of gifts is always an odd number.
 *
 * Hint: you may need to add a size attribute to the Node class to keep track of the total number of gifts in the subtree.
 */

public class SantaInventory
{
    private Node root; // root of BST

    private class Node
    {
        private int toyPrice;     // Price of the toy
        private int count;        // Number of time a toy with price `toyPrice` has been added in the tree
        private Node left, right; // left and right subtrees

        // BEGIN : STUDENT
        private int nbSubGifts;

        private Node(int toyPrice, int count, int nbSubGifts, Node left, Node right)
        {
            this.toyPrice = toyPrice;
            this.count = count;
            this.nbSubGifts = nbSubGifts;
            this.left = left;
            this.right = right;
        }
        // END : STUDENT
    }

    // BEGIN : STUDENT
    // To Debug Only
    public void printTree()
    {
        printTree(root);
    }

    private void printTree(Node root)
    {
        if (root == null) return;

        printTree(root.left);
        System.out.println("root.toyPrice : " + root.toyPrice + "  ET root.count : " + root.count + "  ET root.nbSubGifts : " + root.nbSubGifts);
        printTree(root.right);
    }
    // END : STUDENT

    /**
     * Inserts a new toy price into the magical Christmas search tree or updates the count of an existing toy price.
     * This method is part of the implementation of the magical Christmas search tree where each node
     * represents a unique toy price and the number of toys available at that price.
     *
     * If the tree already contains the toy price, the method updates the count of toys at that price.
     * If the toy price does not exist in the tree, a new node with the toy price and count is created.
     *
     * @param toyPrice The price of the toy to be added or updated in the tree.
     * @param count    The number of toys added to the magical tree. If the toy price already exists,
     *                 this count is added to the existing count.
     */
    public void put(int toyPrice, int count)
    {
        // BEGIN : STUDENT
        root = search(root, toyPrice, count);
        // END : STUDENT
    }

    // BEGIN : STUDENT
    private Node search(Node root, int toyPrice, int count)
    {
        if (root == null) return new Node(toyPrice, count, 0, null, null);

        if (root.toyPrice == toyPrice) { root.count += count; return root; }

        root.nbSubGifts += count;

        if (root.toyPrice < toyPrice) root.right = search(root.right, toyPrice, count);
        else                          root.left  = search(root.left, toyPrice, count);

        return root;
    }
    // END : STUDENT

    /**
     * Calculates the median price of the toys in the magical Christmas search tree.
     *
     * The median is determined by the size of the tree. If the tree is empty, it throws an IllegalArgumentException.
     *
     * Note: The method assumes that the total number of toys (the sum of counts of all prices) is odd.
     * The median is the price at the middle position when all toy prices are listed in sorted order.
     *
     * @return The median price of the toys.
     * @throws IllegalArgumentException if the tree is empty.
     */
    public int median()
    {
        // BEGIN : STUDENT
        if (root == null) throw new IllegalArgumentException("The tree is empty !");

        int nbGifts = root.count + root.nbSubGifts;
        int nbMedian = (nbGifts % 2 == 0) ? nbGifts / 2 : (nbGifts + 1) / 2;
        int currentNbGifts = 0;

        Stack<Node> stack = new Stack<>();
        Node current = root;

        while (current != null || !stack.isEmpty())
        {
            while (current != null)
            {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            currentNbGifts += current.count;
            if (currentNbGifts >= nbMedian) return current.toyPrice;
            current = current.right;
        }
        return -1; // Will never be the case
        // END : STUDENT
    }
}