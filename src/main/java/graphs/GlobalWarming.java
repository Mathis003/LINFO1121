package graphs;

import java.util.Arrays;

/**
 * In this exercise, we revisit the GlobalWarming
 * class from the sorting package.
 * You are still given a matrix of altitude in
 * parameter of the constructor, with a water level.
 * All the entries whose altitude is under, or equal to,
 * the water level are submerged while the other constitute small islands.
 *
 * For example let us assume that the water
 * level is 3 and the altitude matrix is the following
 *
 *      | 1 | 3 | 3 | 1 | 3 |
 *      | 4 | 2 | 2 | 4 | 5 |
 *      | 4 | 4 | 1 | 4 | 2 |
 *      | 1 | 4 | 2 | 3 | 6 |
 *      | 1 | 1 | 1 | 6 | 3 |
 *
 * If we replace the submerged entries
 * by _, it gives the following matrix
 *
 *      | _ | _ | _ | _ | _ |
 *      | 4 | _ | _ | 4 | 5 |
 *      | 4 | 4 | _ | 4 | _ |
 *      | _ | 4 | _ | _ | 6 |
 *      | _ | _ | _ | 6 | _ |
 *
 * The goal is to implement two methods that
 * can answer the following questions:
 *      1) Are two entries on the same island?
 *      2) How many islands are there
 *
 * Two entries above the water level are
 * connected if they are next to each other on
 * the same row or the same column. They are
 * **not** connected **in diagonal**.
 * Beware that the methods must run in O(1)
 * time complexity, at the cost of a pre-processing in the constructor.
 * To help you, you'll find a `Point` class
 * in the utils package which identified an entry of the grid.
 * Carefully read the expected time complexity of the different methods.
 */
public class GlobalWarming
{
    // BEGIN : STUDENT
    int[][] altitude;
    int waterLevel;
    int[][] id;
    int n;
    int m;
    int nbComponent;

    int SINK_POINT = -1;
    // END : STUDENT

    /**
     * Constructor. The run time of this method is expected to be in
     * O(n x log(n)) with n the number of entry in the altitude matrix.
     *
     * @param altitude the matrix of altitude
     * @param waterLevel the water level under which the entries are submerged
     */
    public GlobalWarming(int[][] altitude, int waterLevel)
    {
        // BEGIN : STUDENT
        this.altitude = altitude;
        this.waterLevel = waterLevel;

        n = altitude.length;
        m = altitude[0].length;
        nbComponent = 0;
        id = new int[n][m];
        for (int i = 0; i < n; i++) Arrays.fill(id[i], SINK_POINT);

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < m; j++)
            {
                if (!isUnderWater(i, j) && id[i][j] == SINK_POINT)
                {
                    id[i][j] = nbComponent;
                    dfs(i, j);
                    nbComponent++;
                }
            }
        }
        // END : STUDENT
    }

    // BEGIN : STUDENT
    private boolean isUnderWater(int x, int y)  { return (altitude[x][y] <= waterLevel); }

    private void dfs(int x, int y)
    {
        int[][] directions = new int[][] {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

        for (int[] dir : directions)
        {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (!(newX < 0 || n <= newX || newY < 0 || m <= newY) && !isUnderWater(newX, newY) && id[newX][newY] == SINK_POINT)
            {
                id[newX][newY] = nbComponent;
                dfs(newX, newY);
            }
        }
    }
    // END : STUDENT

    /**
     * Returns the number of island
     *
     * Expected time complexity O(1)
     */
    // BEGIN : STUDENT
    public int nbIslands() { return nbComponent; }
    // END : STUDENT

    /**
     * Return true if p1 is on the same island as p2, false otherwise
     *
     * Expected time complexity: O(1)
     *
     * @param p1 the first point to compare
     * @param p2 the second point to compare
     */
    // BEGIN : STUDENT
    public boolean onSameIsland(Point p1, Point p2)
    {
        if (isUnderWater(p1.getX(), p1.getY()) || isUnderWater(p2.getX(), p2.getY())) return false;
        return (id[p1.getX()][p1.getY()] == id[p2.getX()][p2.getY()]);
    }
    // END : STUDENT


    /**
     * This class represent a point in a 2-dimension discrete plane. This is used, for instance, to
     * identified cells of a grid
     */
    static class Point
    {
        private final int x;
        private final int y;

        public Point(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        public int getX() { return x; }

        public int getY() { return y; }

        @Override
        public boolean equals(Object o)
        {
            if (o instanceof Point)
            {
                Point p = (Point) o;
                return p.x == this.x && p.y == this.y;
            }
            return false;
        }
    }
}