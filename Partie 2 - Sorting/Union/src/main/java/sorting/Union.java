package sorting;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * Author Pierre Schaus
 *
 * Given an array of (closed) intervals, you are asked to implement the union operation.
 * This operation will return the minimal array of sorted intervals covering exactly the union
 * of the points covered by the input intervals.
 * For example, the union of intervals [7,9],[5,8],[2,4] is [2,4],[5,9].
 * The Interval class allowing to store the intervals is provided
 * to you.
 *
 */
public class Union {

    public static void main(String[] args) {
        Union.Interval i1 = new Union.Interval(1, 2);
        Union.Interval i2 = new Union.Interval(3, 4);
        Union.Interval[] result = Union.union(new Union.Interval[]{i1, i2});

        System.out.println(Union.union(result));
    }

    /**
     * A class representing an interval with two integers. Hence the interval is
     * [min, max].
     */
    public static class Interval implements Comparable<Union.Interval> {

        public final int min;
        public final int max;

        public Interval(int min, int max) {
            assert(min <= max);
            this.min = min;
            this.max = max;
        }

        @Override
        public boolean equals(Object obj) {
            return ((Union.Interval) obj).min == min && ((Union.Interval) obj).max == max;
        }

        @Override
        public String toString() {
            return "["+min+","+max+"]";
        }

        @Override
        public int compareTo(Union.Interval o) {
            if (min < o.min) return -1;
            else if (min == o.min) return max - o.max;
            else return 1;
        }
    }

    /**
     * Returns the union of the intervals given in parameters.
     * This is the minimal array of (sorted) intervals covering
     * exactly the same points than the intervals in parameter.
     * 
     * @param intervals the intervals to unite.
     */
    public static Interval[] union(Interval[] intervals) {
        // TODO

        if (intervals.length <= 1){ //si nombre d'intervalle plus petit ou égal à 1, on retourne l'intervalle directement
            return intervals;
        }

        ArrayList<Interval> result = new ArrayList<>();
        Arrays.sort(intervals); //on trie les intervalle par leur min

        int current_min = intervals[0].min;
        int current_max = intervals[0].max;

        for (int i = 1; i < intervals.length; i++){
            if (intervals[i].min > current_max){ //si minimum intervalle itéré est plus grand que le max actuel, on ferme l'intervalle

                result.add(new Interval(current_min,current_max)); //on l'ajoute à result
                current_min = intervals[i].min; //on met à jour les current_min et max
                current_max = intervals[i].max;

            } else { //si minimum intervalle itéré est plus petit que le max actuel, on poursuit l'intervalle
                current_max = Math.max(current_max,intervals[i].max);
            }
        }
        result.add(new Interval(current_min, current_max)); //on ajoute le tout dernier intervalle
        return result.toArray(new Interval[0]); //convertir une ArrayList<Interval> en un tableau Interval[]
    }
}