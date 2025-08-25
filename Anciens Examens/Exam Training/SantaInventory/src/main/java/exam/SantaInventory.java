package exam;

/**
 * Santa needs to calculate the median price of gifts he will deliver this year.
 * The gift prices are stored in a unique data structure known as the 'magical Christmas search tree'.
 *
 * Each node in this tree represents a gift price (as the key) and the quantity of gifts at that price (as the value).
 * The goal is to implement two methods:
 * - put (to add gift prices to the tree) and
 * - median (to find the median price of the gifts).
 *
 * For example, consider the following magical Christmas search tree:
 *
 *                               [150, 4]
 *                                /     \
 *                               /       \
 *                              /         \
 *                             /           \
 *                        [100, 10]       [300, 2]
 *                                         /   \
 *                                        /     \
 *                                       /       \
 *                                      /         \
 *                                   [200, 8]     [500, 1]
 *
 * This tree represents a total of 25 gifts. The median price is the 13th price in the sorted list of gift prices.
 * In this example, the sorted list of prices is:
 * 100 (10 times), 150 (4 times), 200 (8 times), 300 (2 times), 500 (once). The 13th price in this list is 150.
 * Thus, the median price of the gifts is 150.
 *
 * Note: It's assumed that the total number of gifts is always an odd number.
 *
 * Hint: you may need to add a size attribute to the Node class to keep track of the total number of gifts in the subtree.
 */

public class SantaInventory {

    public static void main(String[] args) {
        SantaInventory inventory = new SantaInventory();
        inventory.put(20, 4);
        inventory.put(1, 10);
        inventory.put(35, 2);
        inventory.put(40, 1);
        inventory.put(5, 8);
        inventory.put(1, 10);
        inventory.median();

    }

    private Node root; // root of BST
    public int size; //nombre de noeuds
    public int totalToys; //nombre total de jouets

    private class Node {
        private int toyPrice; // Price of the toy
        private int count; // Number of time a toy with price `toyPrice` has been added in the tree
        private Node left, right; // left and right subtrees
    }

    /**
     * Inserts a new toy price into the magical Christmas search tree or updates the count of an existing toy price.
     * This method is part of the implementation of the magical Christmas search tree where each node
     * represents a unique toy price and the number of toys available at that price.
     *
     * If the tree already contains the toy price, the method updates the count of toys at that price.
     * If the toy price does not exist in the tree, a new node with the toy price and count is created.
     *
     * @param toyPrice The price of the toy to be added or updated in the tree.
     * @param count    The number of toys added to the magical tree. If the toy price already exists,
     *                 this count is added to the existing count.
     */
    public void put(int toyPrice, int count) {
        if (root == null){ //si arbre vide au départ
            root = new Node();
            root.toyPrice = toyPrice;
            root.count = count;
            size++;
            totalToys += count;
        } else put(root,toyPrice,count);

    }

    public void put(Node node, int toyPrice, int count){

        if (node.toyPrice == toyPrice){
            totalToys += count;
            node.count += count; //si même prix, on met à jour le nombre de jouets ayant ce prix-là
        }

        else if (toyPrice < node.toyPrice){ //si prix plus bas, on va à gauche dans le tree

            if (node.left == null){ //si noeud gauche null, on insère ici
                node.left = new Node();
                node.left.toyPrice = toyPrice;
                node.left.count = count;
                size++;
                totalToys += count;
            } else put(node.left,toyPrice,count); //sinon on continue de descendre dans le tree

        } else { //si prix plus haut, on va à droite dans le tree
            if (node.right == null){ //si noeud droit null, on insère ici
                node.right = new Node();
                node.right.toyPrice = toyPrice;
                node.right.count = count;
                size++;
                totalToys += count;
            } else put(node.right,toyPrice,count); //sinon on continue de descendre dans le tree
        }
    }

    /**
     * Calculates the median price of the toys in the magical Christmas search tree.
     *
     * The median is determined by the size of the tree. If the tree is empty, it throws an IllegalArgumentException.
     *
     * Note: The method assumes that the total number of toys (the sum of counts of all prices) is odd.
     * The median is the price at the middle position when all toy prices are listed in sorted order.
     *
     * @return The median price of the toys.
     * @throws IllegalArgumentException if the tree is empty.
     */
    public int median() {
        if (root == null) throw new IllegalArgumentException();
        return inOrder(root, new int[1]); //tableau de taille 1 pour stocker l'entier counter
    }

    public int inOrder(Node node, int[] counter){
        if (node == null) return -1;
        int leftResult = inOrder(node.left, counter); //on parcoure d'abord le sous-arbre gauche
        if (leftResult != -1) return leftResult; //si médiane trouvée dans la branche gauche, on retourne

        counter[0] += node.count;
        if (counter[0] >= (totalToys/2)+1) return node.toyPrice;

        return inOrder(node.right,counter); //si médiane toujours pas trouvée on va voir dans le sous-arbre de droite
    }
}