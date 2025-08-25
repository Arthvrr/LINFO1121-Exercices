package sorting;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Let a be an array of integers.
 * In this exercise we are interested in finding
 * the two entries i and j such that a[i] + a[j] is the closest from a target x.
 * In other words, there are no entries k,l such that |x - (a[i] + a[j])| > |x - (a[k] + a[l])|.
 * Note that we can have i = j.
 * Your program must return the values a[i] and a[j].
 *
 * For example let a = [5, 10, 1, 75, 150, 151, 155, 18, 75, 50, 30]
 *
 * then for x = 20, your program must return the array [10, 10],
 *      for x = 153 you must return [1, 151] and
 *      for x = 170 you must return [18, 151]
 */
public class ClosestPair {

    public static void main(String[] args) {
        int[] a = new int[]{5,10,1,150,151,155,18,50,30};
        closestPair(a,-1);
    }

    /**
      * Finds the pair of integers which sums up to the closest value of x
      *
      * @param a the array in which the value are looked for
      * @param x the target value for the sum
      */
    public static int[] closestPair(int [] a, int x) {
        // TODO

        int[] closest = new int[2];
        Arrays.sort(a);
        int minDiff = Integer.MAX_VALUE;

        for (int i = 0; i < a.length; i++){
            for (int j = i; j < a.length; j++){

                int delta = Math.abs(x - (a[i] + a[j]));

                if (delta < minDiff){
                    minDiff = delta;
                    closest[0] = a[i];
                    closest[1] = a[j];
                }
            }
        }
        System.out.println(Arrays.toString(closest));
        return closest;
    }
}