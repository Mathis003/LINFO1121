/*
This is not my code !
I did not succeed this exercice :/
*/

package searching;

import java.util.*;

public class Skyline
{
    // BEGIN : STUDENT
    public static class BuildingPoint implements Comparable<BuildingPoint>
    {
        int x;
        boolean isStart;
        int height;

        public BuildingPoint(int x, boolean isStart, int height)
        {
            this.x = x;
            this.isStart = isStart;
            this.height = height;
        }

        @Override
        public int compareTo(BuildingPoint other)
        {
            if (this.x != other.x) return this.x - other.x;
            return (this.isStart ? -this.height : this.height) - (other.isStart ? -other.height : other.height);
        }
    }
    // END : STUDENT

    /**
     *   The buildings are defined with triplets (left, height, right).
     *         int[][] buildings = {{1, 11, 5}, {2, 6, 7}, {3, 13, 9}, {12, 7, 16}, {14, 3, 25}, {19, 18, 22}, {23, 13, 29}, {24, 4, 28}};
     *
     *         [{1,11},{3,13},{9,0},{12,7},{16,3},{19,18},{22,3},{23,13},{29,0}]
     *
     * @param buildings
     * @return  the skyline in the form of a list of "key points [x, height]".
     *          A key point is the left endpoint of a horizontal line segment.
     *          The key points are sorted by their x-coordinate in the list.
     */
    public static List<int[]> getSkyline(int[][] buildings)
    {
        BuildingPoint[] points = new BuildingPoint[buildings.length * 2];
        int index = 0;
        for (int[] building : buildings)
        {
            points[index] = new BuildingPoint(building[0], true, building[1]);
            points[index + 1] = new BuildingPoint(building[2], false, building[1]);
            index += 2;
        }

        Arrays.sort(points);

        TreeMap<Integer, Integer> queue = new TreeMap<>();
        queue.put(0, 1);
        int prevMaxHeight = 0;

        List<int[]> result = new ArrayList<>();

        for (BuildingPoint point : points)
        {
            if (point.isStart)
            {
                queue.compute(point.height, (key, value) -> {
                    if (value != null) return value + 1;
                    return 1;
                });
            } else
            {
                queue.compute(point.height, (key, value) -> {
                    if (value == 1) return null;
                    return value - 1;
                });
            }

            int currentMaxHeight = queue.lastKey();

            if (prevMaxHeight != currentMaxHeight)
            {
                result.add(new int[]{point.x, currentMaxHeight});
                prevMaxHeight = currentMaxHeight;
            }
        }
        return result;
    }
}