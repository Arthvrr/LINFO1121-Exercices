package fundamentals;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author Pierre Schaus
 * * i.e. a list for which the last position of the list refers, as the next position,
 * We are interested in the implementation of a circular simply linked list,
 * to the first position of the list.
 *
 * The addition of a new element (enqueue method) is done at the end of the list and
 * the removal (remove method) is done at a particular index of the list.
 *
 * A (single) reference to the end of the list (last) is necessary to perform all operations on this queue.
 *
 * You are therefore asked to implement this circular simply linked list by completing the class see (TODO's)
 * Most important methods are:
 *
 * - the enqueue to add an element;
 * - the remove method [The exception IndexOutOfBoundsException is thrown when the index value is not between 0 and size()-1];
 * - the iterator (ListIterator) used to browse the list in FIFO.
 *
 * @param <Item>
 */
public class CircularLinkedList<Item> implements Iterable<Item> {

    private long nOp = 0; // count the number of operations
    private int n;          // size of the stack
    private Node last;   // trailer of the list

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
    }

    public CircularLinkedList() {
        n = 0; //initialisation des variables d'instances
        last = null; //pas encore de noeuds
    }

    public boolean isEmpty() {
        // TODO
        return n == 0;
    }

    public int size() {
        // TODO
        return n;
    }

    private long nOp() {
        return nOp;
    }



    /**
     * Append an item at the end of the list
     * @param item the item to append
     */
    public void enqueue(Item item) {
        // TODO

        if (n == 0){ //si la circular linkedList est vide, on l'initialise avec le noeud item

            Node first = new Node(); //création d'un noeud first
            first.item = item;
            first.next = first; //pointe vers lui-même
            last = first; //le seul noeud de la CircularLinkedList est last

            nOp++;
            n++;

        } else { //la CircularLinkedList contient au moins 1 noeud

            Node first = last.next; //premier noeud de la CircularLinkedList

            Node newLast = new Node(); //nouveau noeud newLast
            newLast.item = item;
            newLast.next = first;
            last.next = newLast;
            last = newLast; //mise à jour du dernier noeud de la CircularLinkedList

            nOp++;
            n++;
        }
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     */
    public Item remove(int index) {

         if (n == 0) throw new NoSuchElementException(); //si pas de noeud erreur, rien à enlever

         if (index < 0 || index >= n) throw new IndexOutOfBoundsException(); //si index pas dans les bornes

         else if (n == 1){ //si un seul noeud, on fait pointer last à null
             last = null;
             n--;
             nOp++;

         } else { //si plus d'un noeud

             Node before = last;
             Node current = last.next; //on récupère le premier noeud de la CircularLinkedList
             int temp_idx = 0;

             while (temp_idx < index){
                 current = current.next; //on passe au noeud suivant
                 before = before.next;
                 temp_idx++;
             }

             //on est au bon noeud

             //si le noeud a supprimer est le dernier noeud --> màj de last
             if (temp_idx == n - 1){
                 before.next = current.next; //noeud d'avant pointe vers noeud d'après
                 last = before;
             }
             before.next = current.next; //noeud d'avant pointe vers noeud d'après

             n--;
             nOp++;
         }
        return null;
    }

    /**
     * Returns an iterator that iterates through the items in FIFO order.
     * @return an iterator that iterates through the items in FIFO order.
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    /**
     * Implementation of an iterator that iterates through the items in FIFO order.
     * The iterator should implement a fail-fast strategy, that is ConcurrentModificationException
     * is thrown whenever the list is modified while iterating on it.
     * This can be achieved by counting the number of operations (nOp) in the list and
     * updating it everytime a method modifying the list is called.
     * Whenever it gets the next value (i.e. using next() method), and if it finds that the
     * nOp has been modified after this iterator has been created, it throws ConcurrentModificationException.
     */
    private class ListIterator implements Iterator<Item> {

        // TODO You probably need a constructor here and some instance variables

        Node current;
        int remaining;
        long expectedOp;

        public ListIterator(){ //CONSTRUCTEUR
            current = (last != null) ? last.next : null; //premier noeud de la CircularLinkedList, reste null si last == null
            remaining = n; //nombre de noeuds restants à parcourir
            expectedOp = nOp; //nombre d'opérations attendues
        }

        @Override
        public boolean hasNext() {
            if (nOp != expectedOp) throw new ConcurrentModificationException();
            return remaining > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            if (expectedOp != nOp) throw new ConcurrentModificationException();

            Item currentItem = current.item;
            remaining--;
            current = current.next;
            return currentItem;
        }
    }
}