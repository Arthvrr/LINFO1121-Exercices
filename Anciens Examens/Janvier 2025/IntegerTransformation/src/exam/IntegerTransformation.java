package exam;

import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Given two integers start and end, your task is to determine the minimum number of operations required to transform
 * start into end. You may use the following operations:
 *     - Add 5 to the number
 *     - Subtract 7 to the number
 *     - Multiply the number by 2
 *     - Divide the number by 3 (integer division)
 */
public class IntegerTransformation {

    public static void main(String[] args) {

        System.out.println(countSteps(7,7)); //0
        System.out.println(countSteps(2,4)); //1
        System.out.println(countSteps(10,5)); //2
    }

    /**
     * Given two integers start and end, returns the minimum number of operations required to transform start into end.
     * @param start the initial integer.
     * @param target the target integer.
     * @return the minimum number of operations required to transform start into end.
     */
    public static int countSteps(int start, int target) {
        // TODO
        //trouver le nombre d'étapes minimum pour atteindre un goal avec même coûts entre chaque étapes --> BFS
        //attention pour le nombre d'étapes ! on passe de 0 à 4 voisins, 4 à 16 voisins,...

        if (start == target) return 0;

        HashSet<Integer> visited = new HashSet<>();
        Queue<Combinaison> queue = new LinkedList<>();
        boolean found = false;

        queue.add(new Combinaison(start,0));

        while (!found){
            Combinaison current = queue.poll();

            current.steps++;

            int newAdd = current.number + 5;
            int newSub = current.number - 7;
            int newMul = current.number * 2;
            int newDiv = current.number / 3;

            if (newAdd == target || newSub == target || newMul == target || newDiv == target) return current.steps;

            queue.add(new Combinaison(newAdd,current.steps));
            queue.add(new Combinaison(newSub,current.steps));
            queue.add(new Combinaison(newMul,current.steps));
            queue.add(new Combinaison(newDiv,current.steps));

            visited.add(newAdd);
            visited.add(newSub);
            visited.add(newMul);
            visited.add(newDiv);

        }
        return -1;
    }

    private static class Combinaison{
        int number;
        int steps;

        public Combinaison(int number,int steps){
            this.number = number;
            this.steps = steps;
        }
    }


}