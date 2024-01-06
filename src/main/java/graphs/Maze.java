package graphs;

import java.util.*;

/**
 * We are interested in solving a maze represented
 * by a matrix of integers 0-1 of size nxm.
 * This matrix is a two-dimensional array.
 * An entry equal to '1' means that there
 * is a wall and therefore this position is not accessible,
 * while '0' means that the position is free.
 * We ask you to write a Java code to discover
 * the shortest path between two coordinates
 * on this matrix from (x1, y1) to (x2, y2).
 * The moves can only be vertical (up/down) or horizontal (left/right)
 * (not diagonal), one step at a time.
 * The result of the path is an Iterable of
 * coordinates from the origin to the destination.
 * These coordinates are represented by integers
 * between 0 and n * m-1, where an integer 'a'
 * represents the position x =a/m and y=a%m.
 * If the start or end position is a wall
 * or if there is no path, an empty Iterable must be returned.
 * The same applies if there is no path
 * between the origin and the destination.
 */
public class Maze
{
    static final int WALL = 1;

    public static Iterable<Integer> shortestPath(int[][] maze, int x1, int y1, int x2, int y2)
    {
        Stack<Integer> stack = new Stack<>();

        if (maze[x1][y1] == WALL || maze[x2][y2] == WALL) return stack;

        LinkedList<Integer> queue = new LinkedList<>();
        int size = maze.length * maze[0].length;
        int start = x1 * maze[0].length + y1;
        int end = x2 * maze[0].length + y2;

        if (start == end) { stack.push(start); return stack; }

        boolean found_end = false;
        int[] directions = new int[] {1, -1, maze[0].length, -maze[0].length};
        int[] edgedTo = new int[size];
        boolean[] marked = new boolean[size];

        marked[start] = true;
        queue.offer(start);

        while (!queue.isEmpty())
        {
            int curentPos = queue.remove();
            boolean enter;

            for (int direction : directions)
            {
                enter = true;
                if (direction == 1 && ((curentPos + 1) % maze[0].length == 0)) enter = false;
                if (direction == -1 && (curentPos % maze[0].length == 0))      enter = false;
                int newPos = curentPos + direction;

                if (enter && newPos < size && newPos >= 0 && maze[row(newPos, maze[0].length)][col(newPos, maze[0].length)] != WALL && !marked[newPos])
                {
                    edgedTo[newPos] = curentPos;
                    marked[newPos] = true;
                    queue.add(newPos);
                    if (newPos == end) found_end = true;
                }
            }
        }

        if (found_end)
        {
            int current = end;
            while(current != start)
            {
                stack.push(current);
                current = edgedTo[current];
            }
            stack.push(start);
        }

        Stack<Integer> stack_final = new Stack<>();
        while (!stack.isEmpty()) stack_final.push(stack.pop());
        return stack_final;
    }

    public static int ind(int x, int y, int lg)
    { return x * lg + y; }

    public static int row(int pos, int mCols)  { return pos / mCols; }

    public static int col(int pos, int mCols) { return pos % mCols; }
}