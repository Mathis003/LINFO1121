package graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * You just bought yourself the latest game from the Majong™
 * studio (recently acquired by Macrosoft™): MineClimb™.
 * In this 3D game, the map is made up of size 1
 * dimensional cube blocks, aligned on a grid,
 * forming vertical columns of cubes.
 * There are no holes in the columns, so the state
 * of the map can be represented as a matrix M of size n⋅m
 * where the entry M_{i,j} is the height of
 * the cube column located at i,j (0 ≤ i < n, 0 ≤ j < m).
 * You can't delete or add cubes, but you do have
 * climbing gear that allows you to move from one column to another
 * (in the usual four directions (north, south, east, west),
 * but not diagonally). The world of MineClimb™ is round:
 * the position north of (0,j) is (n-1,j), the position
 * west of (i,0) is (i,m-1) , and vice versa.
 * <p>
 * The time taken to climb up or down a column depends on
 * the difference in height between the current column and the next one.
 * Precisely, the time taken to go from column (i,j)
 * to column (k,l) is |M_{i,j}-M_{k,l}|
 * <p>
 * Given the map of the mine, an initial position
 * and an end position, what is the minimum time needed
 * to reach the end position from the initial position?
 */
public class MineClimbing
{
    /**
     * Returns the minimum distance between (startX, startY) and (endX, endY), knowing that
     * you can climb from one mine block (a,b) to the other (c,d) with a cost Math.abs(map[a][b] - map[c][d])
     * <p>
     * Do not forget that the world is round: the position (map.length,i) is the same as (0, i), etc.
     * <p>
     * map is a matrix of size n times m, with n,m > 0.
     * <p>
     * 0 <= startX, endX < n
     * 0 <= startY, endY < m
     */
    public static int best_distance(int[][] map, int startX, int startY, int endX, int endY)
    {
        // BEGIN : STUDENT
        if (startX == endX && startY == endY) return 0;

        int[][] distTo = new int[map.length][map[0].length];

        for (int i = 0; i < map.length; i++)
        {
            for (int j = 0; j < map[0].length; j++)
            {
                distTo[i][j] = Integer.MAX_VALUE;
            }
        }
        distTo[startX][startY] = 0;

        Queue<int[]> q = new LinkedList<>();
        q.add(new int[] {startX, startY});

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        while (!q.isEmpty())
        {
            int[] position = q.remove();
            int pos_x = position[0];
            int pos_y = position[1];

            for (int[] direction : directions)
            {
                int newX = pos_x + direction[0];
                int newY = pos_y + direction[1];

                if (newX == map.length) newX = 0;
                else if (newX == -1)    newX = map.length - 1;

                if (newY == map[0].length) newY = 0;
                else if (newY == -1)       newY = map[0].length - 1;

                int extra_cost = Math.abs(map[pos_x][pos_y] - map[newX][newY]);
                if (distTo[pos_x][pos_y] + extra_cost < distTo[newX][newY])
                {
                    distTo[newX][newY] = distTo[pos_x][pos_y] + extra_cost;
                    q.add(new int[] {newX, newY});
                }
            }
        }
        return distTo[endX][endY];
        // END : STUDENT
    }
}