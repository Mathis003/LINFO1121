package graphs;

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Queue;

/**
 * Author Pierre Schaus
 *
 * Assume the following 5x5 matrix that represent a grid surface:
 * int [][] tab = new int[][] {{1,3,3,1,3},
 *                             {4,2,2,4,5},
 *                             {4,4,5,4,2},
 *                             {1,4,2,3,6},
 *                             {1,1,1,6,3}};
 *
 * Given a global water level, all positions in the matrix
 * with a value <= the water level are flooded and therefore unsafe.
 * So, assuming the water level is 3,
 * all safe points are highlighted between parenthesis:
 *
 *   1 , 3 , 3 , 1 , 3
 *  (4), 2 , 2 ,(4),(5)
 *  (4),(4),(5),(4), 2
 *   1 ,(4), 2 , 3 ,(6)
 *   1 , 1 , 1 ,(6), 3}
 *
 * The method you need to implement is
 * a method that find a safe-path between
 * two positions (row,column) on the matrix.
 * The path assume you only make horizontal or vertical moves
 * but not diagonal moves.
 *
 * For a water level of 4, the shortest path
 * between (1,0) and (1,3) is
 * (1,0) -> (2,0) -> (2,1) -> (2,2) -> (2,3) -> (1,3)
 *
 *
 * Complete the code below so that the {@code  shortestPath} method
 * works as expected
 */
public class GlobalWarmingPaths
{
    int waterLevel;
    int[][] altitude;

    public GlobalWarmingPaths(int[][] altitude, int waterLevel)
    {
        this.waterLevel = waterLevel;
        this.altitude = altitude;
    }

    // BEGIN : STUDENT
    private boolean isUnderWater(int x, int y)  { return (this.altitude[x][y] <= this.waterLevel); }

    private List<Point> getListPath(Point start, Point end, Point[][] edgeTo)
    {
        List<Point> listPoint = new LinkedList<>();
        Point currentP = end;
        while (!start.equals(currentP))
        {
            listPoint.add(currentP);
            currentP = edgeTo[currentP.getX()][currentP.getY()];
        }
        listPoint.add(start);
        Collections.reverse(listPoint);
        return listPoint;
    }
    // END : STUDENT


    /**
     * Computes the shortest path between point p1 and p2
     * @param p1 the starting point
     * @param p2 the ending point
     * @return the list of the points starting
     *         from p1 and ending in p2 that corresponds
     *         the shortest path.
     *         If no such path, an empty list.
     */
    // expected time complexity O(n^2)
    public List<Point> shortestPath(Point p1, Point p2)
    {
        // BEGIN : STUDENT
        if (p1 == null || p2 == null || isUnderWater(p1.getX(), p1.getY()) || isUnderWater(p2.getX(), p2.getY())) return new LinkedList<>();

        if (p1.equals(p2))
        {
            List<Point> listPoint = new LinkedList<>();
            listPoint.add(p1);
            return listPoint;
        }

        int n = this.altitude.length;
        int m = this.altitude[0].length;

        Point[][] edgeTo = new Point[n][m];
        boolean[][] marked = new boolean[n][m];
        marked[p1.getX()][p1.getY()] = true;

        int[][] direct_neighbors = new int[][] {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

        Queue<Point> q = new LinkedList<>();
        q.add(p1);

        while (!q.isEmpty())
        {
            Point curr = q.remove();

            for (int[] dir : direct_neighbors)
            {
                int new_x = curr.getX() + dir[0];
                int new_y = curr.getY() + dir[1];
                if (!(new_x >= n || new_x < 0 || new_y >= m || new_y < 0) && !marked[new_x][new_y] && !isUnderWater(new_x, new_y))
                {
                    Point newPoint = new Point(new_x, new_y);
                    marked[new_x][new_y] = true;
                    edgeTo[new_x][new_y] = curr;
                    q.add(newPoint);

                    if (newPoint.equals(p2)) return getListPath(p1, p2, edgeTo);
                }
            }
        }
        return new LinkedList<>();
        // END : STUDENT
    }


    /**
     * This class represent a point in a 2-dimension discrete plane.
     * This is used to identify the cells of a grid
     * with X = row, Y = column
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

        public int getX()  { return x; }

        public int getY()  { return y; }

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