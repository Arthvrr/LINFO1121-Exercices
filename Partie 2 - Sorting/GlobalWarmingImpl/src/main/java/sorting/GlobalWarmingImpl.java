package sorting;

import java.util.ArrayList;

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

abstract class GlobalWarming {

    public static void main(String[] args) {
        int [][] tab = new int[][] {{1,4,1},
                                    {2,0,3},
                                    {2,5,3}};

    GlobalWarmingImpl gb = new GlobalWarmingImpl(tab);
    gb.nbSafePoints(2);


    }

    protected final int[][] altitude;

    /**
     * @param altitude is a n x n matrix of int values representing altitudes (positive or negative)
     */
    public GlobalWarming(int[][] altitude) {
        this.altitude = altitude;
    }

    /**
     *
     * @param waterLevel
     * @return the number of entries in altitude matrix that would be above
     *         the specified waterLevel.
     *         Warning: this is not the waterLevel given in the constructor/
     */
    public abstract int nbSafePoints(int waterLevel);

}


public class GlobalWarmingImpl extends GlobalWarming {

    private ArrayList<Integer> copy;


    public GlobalWarmingImpl(int[][] altitude) { //CONSTRUCTEUR
        super(altitude);
        // TODO
        // expected pre-processing time in the constructor : O(n^2 log(n^2))
        /*
        on fait une copie de la matrice altitude
        on la trie
        et on compte le nombre de valeurs < à waterLevel
         */
        copy = new ArrayList<>();

        for (int i = 0; i < altitude.length; i++){
            for (int j = 0; j < altitude[0].length; j++){
                copy.add(altitude[i][j]);
            }
        }
        copy.sort(null); //on trie le tableau
        System.out.println(copy);
    }

    /**
     * Returns the number of safe points given a water level
     *
     * @param waterLevel the level of water
     */
    public int nbSafePoints(int waterLevel) {
        // TODO
        // expected time complexity O(log(n^2)) --> BinarySearch, pas le choix
        int lo = 0;
        int hi = copy.size() - 1;
        int result = -1;
        int firstSafeIndex = copy.size(); //par défaut tous sont safe

        while (lo <= hi){
            int mid = lo + (hi-lo) / 2;
            //System.out.println("lo :" + lo + " mid : " + mid + " hi :" + hi);

            if (copy.get(mid) > waterLevel){ //on est au bon endroit, on regarde juste si il y a pas une occurence + à gauche
                firstSafeIndex = mid;
                hi = mid-1; //on regarde si il y a pas un meilleur à gauche
            } else lo = mid+1; //on se déplace à droite

        }
        return copy.size() - firstSafeIndex;
    }
}