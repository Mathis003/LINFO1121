package sorting;

import java.util.Arrays;

/**
 * Author Pierre Schaus
 *
 * Assume the following 5x5 matrix that represent a grid surface:
 * int [][] tab = new int[][] {{1,3,3,1,3},
 *                             {4,2,2,4,5},
 *                             {4,4,1,4,2},
 *                             {1,4,2,3,6},
 *                             {1,1,1,6,3}};
 *
 * Each entry in the matrix represents an altitude point at the corresponding grid coordinate.
 * The goal is to implement a GlobalWarmingImpl class that extends the GlobalWarming abstract class described below.
 *
 * Given a global water level, all positions in the matrix with a value <= the water level are flooded and therefore unsafe.
 * So, assuming the water level is 3, all safe points are highlighted between parenthesis:
 *
 *   1 , 3 , 3 , 1 , 3
 *  (4), 2 , 2 ,(4),(5)
 *  (4),(4), 1 ,(4), 2
 *   1 ,(4), 2 , 3 ,(6)
 *   1 , 1 , 1 ,(6), 3}
 *
 * The method you need to implement is nbSafePoints
 * - calculating the number of safe points for a given water level
 *
 * Complete the code below. Pay attention to the expected time complexity of each method.
 * To meet this time complexity, you need to do some pre-computation in the constructor.
 * Feel free to create internal classes in GlobalWarmingImpl to make your implementation easier.
 * Feel free to use any method or data structure available in the Java language and API.
 */

abstract class GlobalWarming
{
    protected final int[][] altitude;

    /**
     * @param altitude is a n x n matrix of int values representing altitudes (positive or negative)
     */
    public GlobalWarming(int[][] altitude) { this.altitude = altitude; }

    /**
     *
     * @param waterLevel
     * @return the number of entries in altitude matrix that would be above
     *         the specified waterLevel.
     *         Warning: this is not the waterLevel given in the constructor/
     */
    public abstract int nbSafePoints(int waterLevel);
}


public class GlobalWarmingImpl extends GlobalWarming
{
    // BEGIN : STUDENT
    int[] flattenArray;
    // END : STUDENT

    // Expected pre-processing time in the constructor : O(n^2 log(n^2))
    public GlobalWarmingImpl(int[][] altitude)
    {
        super(altitude);
        // BEGIN : STUDENT
        flattenArray = new int[altitude.length * altitude.length];
        for (int i = 0; i < altitude.length; i++)
        {
            for (int j = 0; j < altitude.length; j++) flattenArray[i * altitude.length + j] = altitude[i][j];
        }
        Arrays.sort(flattenArray);
        // END : STUDENT
    }

    /**
     * Returns the number of safe points given a water level
     *
     * @param waterLevel the level of water
     */
    // Expected time complexity : O(log(n^2))
    // BEGIN : STUDENT
    public int nbSafePoints(int waterLevel) { return flattenArray.length - binarySearch(waterLevel); }

    private int binarySearch(int waterLevel)
    {
        int lo = 0;
        int hi = flattenArray.length - 1;

        if (waterLevel < flattenArray[lo])  return flattenArray.length - (hi + 1);
        if (waterLevel >= flattenArray[hi]) return flattenArray.length - lo;

        while (true)
        {
            int mid = (lo + hi) / 2;
            if (flattenArray[mid] <= waterLevel) lo = mid + 1;
            else
            {
                if (mid == 0 || flattenArray[mid - 1] <= waterLevel) return mid;
                hi = mid - 1;
            }
        }
    }
    // END : STUDENT
}