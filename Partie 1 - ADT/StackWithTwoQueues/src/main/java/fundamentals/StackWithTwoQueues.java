package fundamentals;

import java.util.ArrayDeque;
import java.util.EmptyStackException;
import java.util.Queue;

/**
 * Author: Pierre Schaus and Auguste Burlats
 * Implement the abstract data type stack using two queues
 * You are not allowed to modify or add the instance variables,
 * only the body of the methods
 */
public class StackWithTwoQueues<E> {

    //QUEUE :
    // - ajout en fin de liste
    // - retrait en début de liste

    //STACK :
    // - ajout en début de liste
    // - retrait en début de liste

    // --> même chose pour le retrait, mais on doit changer l'implémentation de l'ajout

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++){
            System.out.println(i);
        }
    }

    Queue<E> queue1; //queue principale
    Queue<E> queue2; //queue auxiliaire

    public StackWithTwoQueues() {
        queue1 = new ArrayDeque();
        queue2 = new ArrayDeque();
    }

    /**
     * Looks at the object at the top of this stack
     * without removing it from the stack
     */
    public boolean empty() {
         return queue1.isEmpty();
    }

    /**
     * Returns the first element of the stack, without removing it from the stack
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E peek() throws EmptyStackException {
        if (queue1.isEmpty()) throw new EmptyStackException(); //si queue1 vide, erreur
        return queue1.peek();
    }

    /**
     * Remove the first element of the stack and returns it
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E pop() throws EmptyStackException {
        if (queue1.isEmpty()) throw new EmptyStackException(); //si queue1 vide, erreur
        return queue1.remove();
    }

    /**
     * Adds an element to the stack
     *
     * @param item the item to add
     */
    public void push(E item) {

        if (queue1.isEmpty()){ //si queue1 vide on push bêtement dans queue1
            queue1.add(item);
        } else { //si queue1 n'est pas vide

            //1. tant que queue1 n'est pas vide
            while (!queue1.isEmpty()){
                queue2.add(queue1.remove()); //2. on ajoute le head current à queue2 et on l'enlève de queue1
            }

            //3. on ajoute item à queue1 maintenant qu'elle est vide
            queue1.add(item);

            //4. queue1 est maintenant vide, on repush tout de queue2 dans queue1
            while (!queue2.isEmpty()){
                queue1.add(queue2.remove());
            }
        }
    }
}
