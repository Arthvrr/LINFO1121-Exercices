package graphs;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * In case of an emergency at the Olympic games,
 * it’s crucial to ensure that all spectators can be evacuated efficiently.
 *
 * Each venue is represented as a node in an undirected graph.
 * The pathways between them are represented as weighted edges.
 * A set of exits are also represented as nodes in the graph.
 *
 * The goal is to determine the shortest path from each venue to the nearest exit.
 *
 * To enable people to find the shortest path from any venue to the nearest exit,
 * we are going to place one directional arrow at each venue indicating the
 * next venue to follow on the shorted to reach the nearest exit from that node.
 *
 * Hint: You can use Dijkstra's algorithm with minor adaptations (starting from the exits) to solve this problem.
 *
 * The expected time-complexity if O((V + E) log V) where V is the number nodes and E is the number of edges.
 *
 * Look at the test cases for more details on the input and output format as well as some examples.
 */
public class Evacuation {

    public static void main(String[] args) {

        int[][] graph = {
                {0, 1, 0, 0, 0},
                {1, 0, 1, 1, 0},
                {0, 1, 0, 1, 1},
                {0, 1, 1, 0, 0},
                {0, 0, 1, 0, 0}
        };
        int[] exits1 = {0, 3};
        findShortestPaths(graph,exits1);
    }

    /**
     * @param graph the graph representing the venues (and exits), pathways
     *        The graph is represented as an adjacency matrix,
     *              where graph[i][j] is the weight of the edge between i and j.
     *              If there is no edge between i and j, graph[i][j] = 0.
     * @param exits the nodes of the graph representing the exits
     * @return an array of integers, where the i-th element is the index of the next venue to visit
     * to reach the nearest exit from the i-th venue. If the i-th venue is an exit, the value is -1.
     */
    public static int[] findShortestPaths(int[][] graph, int[] exits) {
        /*
        trouver le plus court chemin avec des poids différents --> Dijkstra
         */

        int[] dist = new int[graph.length]; //contient les distances minimales de chaque noeud vers une exit
        int[] next = new int[graph.length]; //contient l'ID de l'exit la plus proche, ou -1 si une exit

        for (int i = 0; i < graph.length; i++){ //on pose toutes les valeurs à +INF
            dist[i] = Integer.MAX_VALUE;
        }

        PriorityQueue<Node> queue = new PriorityQueue<>();

        for (int j = 0; j < exits.length; j++){
            int currentElem = exits[j];
            queue.add(new Node(currentElem, 0)); //on met déjà dans la PQ tout les noeuds exit pour lancer la recherche
            dist[currentElem] = 0; //on pose la distance à 0 pour une exit dans dist
            next[currentElem] = -1; //on pose -1 pour les noeuds exit dans next
        };

        while (!queue.isEmpty()){
            Node current = queue.poll();

            for (int k = 0; k < graph[0].length; k++){ //on parcoure toute la ligne

                if (graph[current.elem][k] > 0){ //si on a trouvé un noeud voisin au noeud current

                    if (dist[k] < current.cost) continue; //si on a déjà un coût plus petit, on laisse tomber

                    int newCost = current.cost + graph[current.elem][k];

                    if (newCost < dist[k]){ //si le nouveau coût est plus bas que le coût stocké dans dist, on màj
                        dist[k] = newCost;
                        next[k] = current.elem; //on sauvegarde dans next le noeud d'où on vient
                        queue.add(new Node(k,newCost)); //on ajoute à la PQ le noeud avec le nouveau coût
                    }
                }
            }
        }
        System.out.println(Arrays.toString(dist));
        System.out.println(Arrays.toString(next));
        return next;
    }

    private static class Node implements Comparable<Node>{
        int elem;
        int cost;

        public Node(int elem, int cost){
            this.elem = elem;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node other){
            return this.cost - other.cost;
        }
    }
}