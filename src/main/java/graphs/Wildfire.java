package graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Let's consider a forest represented as a 2D grid.
 * Each cell of the grid can be in one of three states:
 *
 * 0 representing an empty spot.
 * 1 representing a tree.
 * 2 representing a burning tree (indicating a wildfire).
 *
 * The fire spreads from a burning tree to its four neighboring cells (up, down, left, and right) if there's a tree there.
 * Each minute, the trees in the neighboring cells of burning tree catch on fire.
 *
 * Your task is to calculate how many minutes it would take for the fire to spread throughout the forest i.e. to burn all the trees.
 *
 * If there are trees that cannot be reached by the fire (for example, isolated trees with no adjacent burning trees),
 * we consider that the fire will never reach them and -1 is returned.
 *
 * The time-complexity of your algorithm must be O(n) with n the number of cells in the forest.
 */
public class Wildfire
{
    static final int EMPTY = 0;
    static final int TREE = 1;
    static final int BURNING = 2;

    /**
     * This method calculates how many minutes it would take for the fire to spread throughout the forest.
     *
     * @param forest
     * @return the number of minutes it would take for the fire to spread throughout the forest,
     *         -1 if the forest cannot be completely burned.
     */
    public int burnForest(int[][] forest)
    {
        Queue<Ceil> q = new LinkedList<>();

        int nberTree = 0;
        for (int i = 0; i < forest.length; i++)
        {
            for (int j = 0; j < forest[i].length; j++)
            {
                if (forest[i][j] == BURNING)    q.add(new Ceil(i, j, 0));
                else if (forest[i][j] == TREE)  nberTree++;
            }
        }

        int[][] directions = new int[][] {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        while (!q.isEmpty())
        {
            Ceil currCeil = q.poll();

            for (int[] dir : directions)
            {
                int newX = currCeil.x + dir[0];
                int newY = currCeil.y + dir[1];

                if (newX < 0 || newX >= forest.length || newY < 0 || newY >= forest[0].length) continue;

                if (forest[newX][newY] == TREE)
                {
                    forest[newX][newY] = BURNING;
                    nberTree--;
                    if (nberTree == 0) return currCeil.time + 1;
                    q.add(new Ceil(newX, newY, currCeil.time + 1));
                }
            }
        }
        return -1;
    }

    public class Ceil
    {
        int x;
        int y;
        int time;

        public Ceil(int x, int y, int time)
        {
            this.x = x;
            this.y = y;
            this.time = time;
        }
    }
}