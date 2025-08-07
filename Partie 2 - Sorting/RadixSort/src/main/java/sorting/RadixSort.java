package sorting;

import java.util.Arrays;

/**
 * Radix sort implementation.
 * Complete the code to pass the test on positive numbers.
 * You can assume that all numbers are non-negative as a first step.
 *
 * As a second step, adapt the code to handle negative numbers.
 * You can also add tests for negative numbers.
 * Remind that negative numbers use the two's complement representation.
 * For example, the number -3 is represented in 4 bits as follows:
 *    1. Positive binary representation of 3: 0011
 *    2. Invert the bits: 1100
 *    3. Add 1: 1100 + 0001 = 1101
 * Thus, -3 in 4-bit two's complement is represented as 1101.
 *
 * What is the time complexity of your algorithm?
 * How does it compare in practice to the other sorting algorithms?
 *
 * @author Pierre Schaus and Harold Kiossous
 */
public class RadixSort {

    public static void main(String[] args) {
        int test = 1;
        int testPos = 1;
        //System.out.println(getBit(test,testPos));

        int[] A = new int[]{5, 10, 1, 75, 0, 2};
        int[] aux = new int[A.length];
        stableSortOnBit(A,0,aux);

    }

    // Radix Sort method (we assume all numbers are non-negative)
    public static void sort(int[] A) {
        int maxBits = getMaxBits(A);
        int [] aux = new int[A.length];
        for (int i = 0; i < maxBits; i++) {
            stableSortOnBit(A, i, aux);
        }
    }


    /**
     * Stable Sort the array A based on the i-th bit
     * In order to have the best time complexity, your algorithm should run in O(n)
     * where n is the size of the array A.
     * @param A the array to sort based on the i-th bit
     * @param bitPosition
     * @param aux is an auxiliary array of the same size as A that you can use in your algorithm
     */
    private static void stableSortOnBit(int[] A, int bitPosition, int[] aux) {
        // TODO
        if (A.length <= 1){ //si A est vide ou ne contient que 1 nombre, on ne fait rien
            return;
        }

        int zero = 0; //nombre d'éléments ayant un bit valant 0 à bitPosition
        for (int i = 0; i < A.length; i++){
            if (getBit(A[i],bitPosition) == 0){
                zero++;
            }
        }

        int zeroIdx = 0; //index de départ pour caler les nombres ayant un bit 0 à bitPosition
        int oneIdx = zero; //index de départ pour caler les nombres ayant un bit 1 à bitPosition

        for (int j = 0; j < A.length; j++){
            if (getBit(A[j],bitPosition) == 0){ //si j-ième nombre a un 0
                aux[zeroIdx] = A[j];
                zeroIdx++;
            } else { //si j-ième nombre a un 1
                aux[oneIdx] = A[j];
                oneIdx++;
            }
        }

        //A = aux ne marche pas ! En Java, quand tu fais A = aux;, tu changes juste la variable locale A, mais le tableau original ne bouge pas du tout. C’est comme si tu renommais temporairement aux → A
        //A = aux;

        //copie de aux dans A
        for (int k =0; k < A.length; k++){
            A[k] = aux[k];
        }
    }

    // Helper method to get the bit at the given position
    // For example, getBit(5, 0) returns 1
    //              getBit(5, 1) returns 0
    //              getBit(5, 2) returns 1
    //              getBit(5, 3) returns 0
    private static int getBit(int number, int bitPosition) {

        String binary = Integer.toBinaryString(number); //récupère la représentation en bit
        if (bitPosition >= binary.length()) return 0; //si la bitPosition dépasse le nombre de bits du nombre, retourner 0
        /*
        charAt retourne un char en code ASCII ('1' = 49 & '0' = 48) donc
        on convertit le caractère numérique (comme '0' et '1') en sa valeur entière avec -'0'
        et on doit inverser l'index de bitPosition
        index des bits : n n-1 4 3 2 1 0 --> on doit inverser
        technique pour convertir un nombre '9' en 9 : int res = char - '0';
         */
        //return binary.charAt(binary.length() - 1 - bitPosition) - '0';

        char bit = binary.charAt(binary.length() - 1 - bitPosition); //récupère le char du bit au bon index
        return Character.getNumericValue(bit); //conversion char --> int
    }

    // Helper Method to find the maximum number of bits required
    private static int getMaxBits(int[] A) {
        int max = 0;
        for (int num : A) {
            max = Math.max(max, num);
        }
        return 32 - Integer.numberOfLeadingZeros(max);
    }
}