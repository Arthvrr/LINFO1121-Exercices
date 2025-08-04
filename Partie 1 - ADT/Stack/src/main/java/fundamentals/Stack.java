package fundamentals;

import java.util.EmptyStackException;

/**
 * Author: Pierre Schaus
 *
 * You have to implement the interface using
 * - a simple linkedList as internal structure
 * - a growing array as internal structure
 */
public interface Stack<E> {

    /**
     * Looks at the object at the top of this stack
     * without removing it from the stack
     */
    public boolean empty();

    /**
     * Returns the first element of the stack, without removing it from the stack
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E peek() throws EmptyStackException;

    /**
     * Remove the first element of the stack and returns it
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E pop() throws EmptyStackException;

    /**
     * Adds an element to the stack
     *
     * @param item the item to add
     */
    public void push(E item);

}

/**
 * Implement the Stack interface above using a simple linked list.
 * You should have at least one constructor without argument.
 * You are not allowed to use classes from java.util
 * @param <E>
 */
class LinkedStack<E> implements Stack<E> {

    private Node<E> top;        // the node on the top of the stack
    private int size;        // size of the stack

    // helper linked list class
    private class Node<E> {
        private E item;
        private Node<E> next;

        public Node(E element, Node<E> next) { //Constructeur
            this.item = element;
            this.next = next;
        }
    }

    @Override
    public boolean empty() {
        // TODO Implement empty method
        return size == 0;
    }

    @Override
    public E peek() throws EmptyStackException {
        // TODO Implement peek method
        if (empty()) throw new EmptyStackException(); //si pas d'éléments, erreur
        return top.item;
    }

    @Override
    public E pop() throws EmptyStackException {
        // TODO Implement pop method
        if (empty()) throw new EmptyStackException(); //si pas d'éléments, erreur
        Node newFirst = top.next; //second noeud devient le nouveau premier noeud
        Node<E> oldFirst = top; //ancien premier noeud, que l'on va enlever et retourner
        top = newFirst;
        size--;
        return oldFirst.item;
    }

    @Override
    public void push(E item) {
        // TODO Implement push method
        Node<E> newFirst = new Node<>(item,top); //nouveau noeud newFirst, avec comme élément item et comme prochain noeud top
        top = newFirst;
        size++;
    }
}


/**
 * Implement the Stack interface above using an array as internal representation
 * The capacity of the array should double when the number of elements exceed its length.
 * You should have at least one constructor without argument.
 * You are not allowed to use classes from java.util
 * @param <E>
 */
class ArrayStack<E> implements Stack<E> {

    private E[] array;        //capacité de l'array
    private int size;        //nombre d'éléments dans la stack

    public ArrayStack() { //Constructeur
        array = (E[]) new Object[10]; //taille de 10 au départ
    }

    @Override
    public boolean empty() {
        // TODO Implement empty method
        return size == 0;
    }

    @Override
    public E peek() throws EmptyStackException {
        // TODO Implement peek method
        if (empty()) throw new EmptyStackException(); //si pas d'éléments, erreur
        return array[0];
    }

    @Override
    public E pop() throws EmptyStackException {
        // TODO Implement pop method
        if (empty()) throw new EmptyStackException(); //si pas d'éléments, erreur
        E toRemove = array[0];
        size--;
        E[] newArray = (E[]) new Object[array.length];
        for (int i = 0; i < size; i++){
            newArray[i] = array[i+1];
        }
        array = newArray;
        return toRemove;
    }

    @Override
    public void push(E item) {
        // TODO Implement push method
        if (size == array.length){ //si plus de place dans l'array, on double sa taille
            E[] newArray = (E[]) new Object[array.length * 2]; //nouvel array de taille array.length * 2
            for (int i = 0; i < size; i++){
                newArray[i] = array[i];
            }
            array = newArray;
        }

        if (size == 0){ //si encore aucun élément dans la stack, on push juste à l'index 0
            array[0] = item;
            size++;
        } else { //si au moins un élément dans la stack, on push à l'index 0 et on décale le reste d'un index
            E[] newArray = (E[]) new Object[array.length];
            newArray[0] = item; //ajout de l'élément en début de Stack
            for (int i = 0; i < size; i++){
                newArray[i+1] = array[i];
            }
            array = newArray;
            size++;
        }
    }
}