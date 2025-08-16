package sorting;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.NoSuchElementException;

/**
 * Imagine a data structure that supports :
 * - insertion in logarithmic time
 * - the median retrieval in constant time
 * - median deletion in logarithmic time
 *
 * Hint: You can use two heaps to solve this problem.
 *       There is no need to implement your own heap, you can
 *       use the PriorityQueue class from Java.
 *       Think about the clas invariant that you need to maintain
 *       after each insertion and deletion.
 *
 * When the number of stored element is odd, the median value is the value
 * at rank n+1/2 where n is the number of element
 * For example if the values {0, 1, 2, 3, 5} are stored, the median value is 2
 * When the number of stored element is even, you will arbitrarily consider that the median value
 * is the value at rank n/2 + 1
 * For example if the values {0, 1, 3, 5} are stored, the median value is 3
 */
public class MedianHeap {

    PriorityQueue<Integer> left;
    PriorityQueue<Integer> right;

    public MedianHeap(int initialSize) {
        // TODO
        left = new PriorityQueue<>(Comparator.reverseOrder()); //ordre décroissant
        right = new PriorityQueue<>(); //ordre croissant
    }

    /**
     * Add a new value in the structure
     * The expected time complexity is O(log(n)) with n the size of the heap
     *
     * @param value the added value
     */
    public void insertion(int value) {
        // TODO
        if (right.isEmpty()) right.add(value); //si tableau droit vide, on ajoute dans right
        else if (value < right.peek()) left.add(value); //si value < head droit, on ajoute à left
        else right.add(value); //si value >= head droit, on ajoute à right
        rebalance(); //rebalancer les 2 PQ si besoin
    }

    public void rebalance(){
        //si right est plus grand de 2 de left
        if (left.size() + 1 < right.size()){
            left.add(right.poll()); //on ajoute à gauche le top de right
        }
        //si left est plus grand que right
        if (left.size() > right.size()){
            right.add(left.poll()); //on ajoute à droite le top de left
        }
    }

    /**
     * Retrieve the median value of the stored elements
     * Complexity O(1)
     * @return the median
     */
    public int findMedian() {
        // TODO
        if (left.isEmpty() && right.isEmpty()) throw new NoSuchElementException();
        return right.peek(); //on retourne le top de right
    }

    /**
     * Delete and return the median value
     * Complexity O(log(n)) with n the number of stored elements
     * @return the median value
     */
    public int deleteMedian() {
        // TODO
        if (left.isEmpty() && right.isEmpty()) throw new NoSuchElementException();
        int mediane = right.poll();
        rebalance();
        return mediane;
    }
}