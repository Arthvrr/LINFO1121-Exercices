package graphs;

import java.util.*;

/**
 * Author: Alexis Englebert
 * Context: You are operating a power plant in the new city of Louvain-La-Neuve,
 * but lack plans for the city's electrical network.
 * Your goal is to minimize the cost of electrical wires ensuring the city is connected with just one wire.
 *
 * The method 'minimumSpanningTreeCost' is designed to find the minimum cost to connect all cities in a given electrical network.
 * The network is represented as a graph where the nodes are the buildings, the edges are the possible connections
 * and their associated cost.
 *
 * Example:
 * Given a network with three buildings (nodes) and the cost of wires (edges) between them:
 * 0 - 1 (5), 1 - 2 (10), 0 - 2 (20)
 * The minimum cost to connect all the buildings is 15 (5 + 10).
 *
 * Note: The method assumes that the input graph is connected and the input is valid.
 */
public class Electricity {

    public static void main(String[] args) {
        int[][] edges = new int[][]{
                {0, 1, 5},
                {1, 2, 10},
                {0, 2, 20}
        };
        int[][] edges2 = new int[][]{
                {0, 1, 10},
                {1, 4, 8},
                {4, 2, 1},
                {2, 0, 7},
                {2, 5, 4},
                {2, 3, 8},
                {3, 5, 9},
                {5, 0, 1}};
        minimumSpanningCost(3,edges);
        //minimumSpanningCost(6,edges2);
    }

    /**
     * @param n       The number of buildings (nodes) in the network.
     * @param edges   A 2D array where each row represents an edge in the form [building1, building2, cost].
     *                The edges are undirected so (building2, building1, cost) is equivalent to (building1, building2, cost).
     * @return       The minimum cost to connect all cities.
     */
    public static int minimumSpanningCost(int n, int [][] edges) {
        //TODO
        //algorithme de Kruskal
        int result = 0;
        int[] parent = new int[n];
        for (int i = 0; i < n; i++){
            parent[i] = i;
        }

        //on trie edges en fonction du coût --> Kruskal : tri croissant = toujours choisir le moins cher en premier = coût total minimal
        Arrays.sort(edges, (a,b) -> Integer.compare(a[2],b[2]));
        for (int[] edge : edges){
            System.out.println(Arrays.toString(edge));
        }

        for (int[] edge : edges){
            if (find(edge[0],parent) != find(edge[1],parent)){ //si pas dans le même composant, on les lie
                union(edge[0],edge[1], parent);
                result+=edge[2]; //on rajoute le coût minimal à result
            }
        }
        return result;
    }

    /*
    Retourne l'ID stocké dans parent
     */
    public static int find(int x, int[] parent){
        if (parent[x] != x) { //on cherche la racine du composant auquel appartient x
            parent[x] = find(parent[x], parent);
        }
        return parent[x];
    }

    /*
    Fusionne les 2 ensemble en changeant leur ID dans parent
     */
    public static void union(int x, int y, int[] parent){
        int rootX = find(x, parent);
        int rootY = find(y, parent);
        if (rootX != rootY) {
            parent[rootY] = rootX; // on fusionne les composantes
        }
    }
}