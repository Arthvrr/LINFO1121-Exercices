package graphs;

import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.HashMap;


/**
 * You are asked to implement the WordTransformationSP
 * class which allows to find the shortest path
 * from a string A to another string B
 * (with the certainty that there is a path from A to B).
 * To do this, we define a rotation(x, y) operation that
 * reverses the order of the letters between the x and y positions (not included).
 * For example, with A=``HAMBURGER``, if we do rotation(A, 4, 8), we get HAMBEGRUR.
 * So you can see that the URGE sub-string
 * has been inverted to EGRU and the rest of the string
 * has remained unchanged: HAMB + EGRU + R = HAMBEGRUR.
 * Let's say that a rotation(x, y) has a cost of y-x.
 * For example going from HAMBURGER to HAMBEGRUR costs 8-4 = 4.
 * The question is what is the minimum cost to reach a string B from A?
 * So you need to implement a public static int minimalCost(String A, String B)
 * function that returns the minimum cost to reach String B
 * from A using the rotation operation.
 */
public class WordTransformationSP {

    public static void main(String[] args) {
        String fromTest = "ABC";
        String toTest = "CBA";

        minimalCost(fromTest,toTest);
    }

    /**
     * Rotate the substring between start and end of a given string s
     * eg. s = HAMBURGER, rotation(s,4,8) = HAMBEGRUR i.e. HAMB + EGRU + R
     *
     * @param s
     * @param start
     * @param end
     * @return rotated string
     */
    public static String rotation(String s, int start, int end) {
        return s.substring(0, start) + new StringBuilder(s.substring(start, end)).reverse().toString() + s.substring(end);
    }

    /**
     * Compute the minimal cost from string "from" to string "to" representing the shortest path
     *
     * @param from
     * @param to
     * @return
     */
    public static int minimalCost(String from, String to) {
        //TODO
        if (from.equals(to)) return 0; //si from = to, on retourne 0 directement
        /*
        coût des transitions est variable et strict. positif, et on cherche le plus petit coût --> Dijkstra
         */

        PriorityQueue<Combinaison> pq = new PriorityQueue<>(); //PriorityQueue avec ordre basé sur les coûts j-i
        HashMap<String,Integer> map = new HashMap<>(); //HashMap qui va contenir les combinaisons string-coût (ex: cba:2)

        pq.add(new Combinaison(from,0));
        map.put(from,0);

        while (!pq.isEmpty()){
            Combinaison current = pq.poll();
            if (current.cost > map.get(current.str)) continue; //si on a déjà un meilleur coût pour le mot courant, on skip

            //dans Dijkstra, une fois que tu extrais un noeud (ici, un mot) de la PriorityQueue, le coût associé est définitivement le plus petit possible pour ce nœud.
            if (current.str.equals(to)) return current.cost; //on peut donc retourner directement

            for (int i = 0; i < from.length(); i++){ //on parcoure toutes les possibilités
                for (int j = i+1; j <= from.length(); j++){

                    String newStr = rotation(current.str,i,j);
                    int newCost = current.cost + (j - i);

                    /*
                    si pas dans le HashMap, on ajoute à la PQ
                    si dans le HashMap :
                        - si meilleur cout, on màj
                        - si moins bon cout, on laisse tomber
                     */
                    if (!map.containsKey(newStr)){ //si le HashMap ne contient pas encore la nouvelle str, on l'ajoute
                        map.put(newStr,newCost);
                        pq.add(new Combinaison(newStr,newCost));
                    } else { //le HashMap possède déjà la nouvelle str en clé
                        if (newCost < map.get(newStr)){ //si meilleur coût, on le met à jour
                            map.put(newStr,newCost);
                            pq.add(new Combinaison(newStr,newCost));
                        }
                    }
                }
            }
        }
        return -1; //pas trouvé
    }

    public static class Combinaison implements Comparable<Combinaison>{

        String str;
        int cost;

        public Combinaison(String str, int cost){
            this.str = str;
            this.cost = cost;
        }

        @Override
        public int compareTo(Combinaison other){
            return this.cost - other.cost;
        }
    }
}