package searching;

import java.util.ArrayList;


/**
 *  We study a BST representation using an arrayList internal representation.
 *  Rather than using a Linked-Node Data-Structure, the left/right children
 *  will be encoded with indices in array lists.
 *  More exactly, in this data-structure each node
 *  is represented by an index i (that correspond to the ith added element)
 *  The left  node of node i, if any, is located
 *        at index idxLeftNode.get(i) otherwise idxLeftNode.get(i) == NONE
 *  The right node of node i, if any is located
 *       at index idxRightNode.get(i) otherwise idxRightNode.get(i) == NONE
 *
 *  The tree below is the result of putting (key,value) pairs (12,A),(15,B),(5,C),(8,D),(1,E) (in this order)
 *
 *                12(A)
 *                / \
 *               /   \
 *             5(C)  15(B)
 *             / \
 *          1(E)  8(D)
 *
 *   The state of internal array list after those put operations is
 *    values:        A, B, C, D, E
 *    keys:        12, 15, 5, 8, 1
 *    idxLeftNode:  2, -1, 4,-1,-1
 *    idxRightNode: 1, -1, 3,-1,-1
 *
 *  You can implement the get method before the put method if you prefer since
 *  the two methods will be graded separately.
 *
 *  You cannot add of change the fields already declared, nor change
 *  the signatures of the methods in this
 *  class but feel free to add methods if needed.
 *
 */
public class ArrayBST<Key extends Comparable<Key>, Value> {

    // The next four array lists should always have exactly equal size after a put

    public ArrayList<Key> keys;
    public ArrayList<Value> values;

    public ArrayList<Integer> idxLeftNode; // idxLeftNode[i] = index of left node of i
    public ArrayList<Integer> idxRightNode; // idxRightNode[i] = index of right node of i

    final int NONE = -1;

    public ArrayBST() {
        keys = new ArrayList<>();
        values = new ArrayList<>();
        idxLeftNode = new ArrayList<>();
        idxRightNode = new ArrayList<>();
    }

    /**
     * Insert the entry in the BST, replace the value if the key is already present
     * in O(h) where h is the height of the tree
     * @param key a key that is present or not in the BST
     * @param val the value that must be attached to this key
     * @return true if the key was added, false if already present and the value has simply been erased
     */
    public boolean put(Key key, Value val) {
        //TODO

        if (keys.isEmpty()){ //si arbre vide au départ
            keys.add(key); //insertion en fin de liste pour les 4 listes
            values.add(val);
            idxLeftNode.add(NONE);
            idxRightNode.add(NONE);
            return true; //clé insérée comme convenu
        }
        else { //arbre pas vide

            if (keys.contains(key)){ //si la clé est déjà présente dans l'arbre, on doit juste remplacer par la nouvelle valeur
                int idxKey = keys.indexOf(key); //on récupère l'index correct de la clé
                values.set(idxKey,val); //on remplace l'ancienne valeur par la nouvelle (val)
                return false; //clé non-insérée, false

            } else { //la clé n'est pas présente dans l'arbre, on doit l'insérer
                keys.add(key); //insertion en fin de liste pour les 4 listes
                values.add(val);
                idxLeftNode.add(NONE);
                idxRightNode.add(NONE);

                //on doit maintenant changer la valeur de l'index du fils gauche ou fils droit pour le noeud parent du nouveau noeud
                int currentIndex = 0;
                int idxToPut = values.size()-1; //valeur à changer dans idxLeftNode ou idxRightNode

                while (true) {
                    Key currentRoot = keys.get(currentIndex);

                    if (key.compareTo(currentRoot) > 0) { //si la clé est plus grande que la racine, sous-arbre droit
                        if (idxRightNode.get(currentIndex) == NONE){ //place libre à droite, on insère ici
                            idxRightNode.set(currentIndex,idxToPut); //on met à jour l'index du fils gauche
                            break;
                        } else currentIndex = idxRightNode.get(currentIndex);

                    } else { //si la clé est plus petite que la racine, sous-arbre gauche
                        if (idxLeftNode.get(currentIndex) == NONE){ //place libre à gauche, on insère ici
                            idxLeftNode.set(currentIndex,idxToPut); //on met à jour l'index du fils droit
                            break;
                        } else currentIndex = idxLeftNode.get(currentIndex);
                    }
                }
            }
            return true; //clé bien insérée comme convenu
        }
    }

    /**
     * Return the value attached to this key, null if the key is not present
     * in O(h) where h is the height of the tree
     * @param key
     * @return the value attached to this key, null if the key is not present
     */
    public Value get(Key key) {
        //TODO
        if (keys.isEmpty()) return null; //si arbre vide retourner null
        int indexKey = keys.indexOf(key); //on récupère l'index correct de la clé
        if (indexKey == -1) return null; //si l'index retourné est -1, la clé n'est pas présente on retourne null
        return values.get(indexKey);
    }
}