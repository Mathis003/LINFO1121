package sorting;

import java.util.Arrays;

/**
 * Let a be an array of integers.
 * In this exercise we are interested in finding
 * the two entries i and j such that a[i] + a[j] is the closest from a target x.
 * In other words, there are no entries k,l such that |x - (a[i] + a[j])| > |x - (a[k] + a[l])|.
 * Note that we can have i = j.
 * Your program must return the values a[i] and a[j].
 *
 * For example let a = [5, 10, 1, 75, 150, 151, 155, 18, 75, 50, 30]
 *  [1, 5, 10, 18, 30, 50, 75, 75, 150, 151, 155]
 *  153 = target
 *
 * then for x = 20, your program must return the array [10, 10],
 *      for x = 153 you must return [1, 151] and
 *      for x = 170 you must return [18, 151]
 */
public class ClosestPair
{
    /**
     * Finds the pair of integers which sums up to the closest value of x
     *
     * @param a the array in which the value are looked for
     * @param x the target value for the sum
     */
    public static int[] closestPair(int[] a, int x)
    {
        // BEGIN : STUDENT
        Arrays.sort(a);

        int lo = 0;
        int hi = a.length - 1;

        int first  = a[lo];
        int second = a[hi];
        int nearest_target = Math.abs(x - (first + second));

        while (lo <= hi)
        {
            if (Math.abs(x - (a[lo] + a[hi])) < nearest_target)
            {
                nearest_target = Math.abs(x - (a[lo] + a[hi]));
                first  = a[lo];
                second = a[hi];
            }

            if (a[lo] + a[hi] < x) lo++;
            else                   hi--;
        }
        return new int[] {first, second};
        // END : STUDENT
    }
}