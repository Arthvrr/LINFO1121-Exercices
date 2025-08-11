package searching;

import java.util.HashMap;

/**
 * We are interested in the implementation of an LRU cache,
 * i.e. a (hash)-map of limited capacity where the addition of
 * a new entry might induce the suppression of the Least Recently Used (LRU)
 * entry if the maximum capacity is exceeded.
 *
 * Your LRU cache implements the same two methods as a MAP :
 * - put(key, elem) inserts the given element in the cache,
 *      this element becomes the most recently used element
 *      and if needed (the cache is full and the key not yet present),
 *      the least recently used element is removed.
 * - get(key) returns the entry with the given key from the cache,
 *      this element becomes the most recently used element
 *
 * These methods need to be implemented with an expected time complexity of O(1).
 * You are free to choose the type of data structure that you consider
 * to best support this cache. You can also use data-structures from Java.
 *
 * Hint for your implementation:
 *       Use a doubly linked list to store the elements from the least
 *       recently used (head) to the most recently used (tail).
 *       If needed the element to suppress is the head of the list.
 *
 *       Use java.util.HashMap with the <key,node> where node is a reference to the node
 *       in the doubly linked list.
 *
 *       Of course, at every put/get the list will need to be updated so that
 *       the "accessed node" is placed at the end of the list.
 *
 *       Feel free to use existing java classes.
 *
 *  Example of usage of an LRU cache with capacity of 3:
 *  // step 0:
 *  put(A,7)  // map{(A,7)}  A is the LRU
 *  // step 1:
 *  put(B,10) // map{(A,7),(B,10)}  A is the LRU
 *  // step 2:
 *  put(C,5)  // map{(A,7),(B,10),(C,5)}  A is the LRU
 *  // step 3:
 *  put(D,8)  // map{(B,10),(C,5),(D,8)}  A is suppressed, B is the LRU
 *  // step 4:
 *  get(B)    // C is the LRU
 *  // step 5
 *  put(E,9)  // map{(B,10),(D,8),(E,9)} D is the LRU
 *  // step 6
 *  put(D,3)  // map{(B,10),(D,3),(E,9)} B is the LRU
 *  // step 7
 *  get(B)    // map{(B,10),(D,3),(E,9)} E is the LRU
 *  // step 8
 *  put(A,2)  // map{(B,10),(D,3),(A,2)} D is the LRU
 *
 *  Feel free to use existing java classes from Java
 */
public class LRUCache<K,V> {

    class Node<K,V> {
        K key;
        V value;
        Node<K,V> prev;
        Node<K,V> next;

        Node(K key, V value){
            this.key = key;
            this.value = value;
        }
    }

    private int capacity; //capacité maximale de la liste
    private int current_capacity;
    HashMap<K,Node<K,V>> map;
    Node head; //LRU (noeud sentinelle)
    Node tail; //MRU (noeud sentinelle)

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.current_capacity = 0;
        this.map = new HashMap<>();
        this.head = new Node(null,null);
        this.tail = new Node(null,null);
        head.next = tail;
        tail.prev = head;
    }

    /*
    supprime le noeud et l'ajoute en fin de liste
     */
    public void moveToEnd(Node node){
        removeNode(node);
        addNode(node);
    }

    /*
    ajoute le noeud en fin de liste (MRU)
     */
    public void addNode(Node node){
        Node prevLast = tail.prev;
        prevLast.next = node;
        node.next = tail;
        tail.prev = node;
        node.prev = prevLast;
    }

    /*
    supprime un noeud à un endroit quelconque de la liste
     */
    public void removeNode(Node node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
    }

    /*
    supprime le noeud en début de liste (LRU)
     */
    public void removeFirst(){
        if (head.next != tail){
            Node first = head.next;
            removeNode(first);
            map.remove(first.key);
            current_capacity--;
        }
    }

    public V get(K key) {
        if (map.isEmpty()) return null;
        if (!map.containsKey(key)) return null;

        Node<K,V> node = map.get(key);
        moveToEnd(node);
        return node.value;
    }

    public void put(K key, V value) {
        if (map.containsKey(key)){ //si map a déjà cette valeur, on la màj et on envoie en fin de liste le noeud
            Node node = map.get(key);
            node.value = value;
            moveToEnd(node);
        } else {
            if (current_capacity == capacity){ //si capacité max atteinte, on enlève le LRU (premier noeud)
                removeFirst();
            }
            Node newNode = new Node(key,value);
            map.put(key,newNode);
            current_capacity++;
            addNode(newNode);
        }
    }
}