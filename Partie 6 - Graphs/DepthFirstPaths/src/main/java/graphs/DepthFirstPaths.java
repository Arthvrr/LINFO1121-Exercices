package graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Consider this class, DepthFirstPaths, which computes the paths to any connected node
 * from a source node s in an undirected graph using a DFS path.
 * <p>
 * The Graph class is already implemented and here it is:
 * <p>
 * public class Graph {
 * // @return the number of vertices
 * public int V() { }
 * <p>
 * // @return the number of edges
 * public int E() { }
 * <p>
 * // Add edge v-w to this graph
 * public void addEdge(int v, int w) { }
 * <p>
 * // @return the vertices adjacent to v
 * public Iterable<Integer> adj(int v) { }
 * <p>
 * // @return a string representation
 * public String toString() { }
 * }
 * <p>
 * <p>
 * You are asked to implement all the TODOs of the DepthFirstPaths class.
 */
public class DepthFirstPaths {
    private boolean[] marked; // marked[v] = is there an s-v path?
    private int[] edgeTo;     // edgeTo[v] = last edge on s-v path
    private final int s;

    /**
     * Computes a path between s and every other vertex in graph G
     *
     * @param G the graph
     * @param s the source vertex
     */
    public DepthFirstPaths(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    // Depth first search from v
    private void dfs(Graph G, int v) {
        //TODO
        marked[v] = true; //on marque le noeud v comme visité (éviter boucles infinies dans un graphe cyclique-
        for (int w : G.adj(v)){ //pour tout ses noeuds adjacents de v
            if (!marked[w]){ //si noeud déjà visité on skip
                edgeTo[w] = v; //on enregistre comment on est arrivé à w
                dfs(G,w); //on lance un dfs
            }
        }
    }

    /**
     * Is there a path between the source s and vertex v?
     *
     * @param v the vertex
     * @return true if there is a path, false otherwise
     */
    public boolean hasPathTo(int v) {
        //TODO
        if (s == v) return true; //si source = goal, true d'office
        //si marked[v] = true, il existe au moins un chemin de s à v
        return marked[v]; //(on le met à true pendant le dfs et on part toujours de la même source)
    }

    /**
     * Returns a path between the source vertex s and vertex v, or
     * null if no such path
     *
     * @param v the vertex
     * @return the sequence of vertices on a path between the source vertex
     * s and vertex v, as an Iterable
     */
    public Iterable<Integer> pathTo(int v) {
        //TODO
        if (!hasPathTo(v)) return null; //pas de chemin vers v, on retourne null
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x]){ //on remonte de v vers via edgeTo avec une Stack
            path.push(x); //on remplit la stack, et les premiers éléments ajoutés seront les derniers
        }
        path.push(s); //on push à la fin la source
        return path;
    }

    static class Graph {

        private List<Integer>[] edges;

        public Graph(int nbNodes){
            this.edges = (ArrayList<Integer>[]) new ArrayList[nbNodes]; //Tableau d'ArrayList avec pour chaque sommet i les arêtes de ce même graphe
            for (int i = 0;i < edges.length;i++){
                edges[i] = new ArrayList<>();
            }
        }

        /**
         * @return the number of vertices
         */
        public int V() {
            return edges.length;
        }

        /**
         * @return the number of edges
         */
        public int E() {
            int count = 0;
            for (List<Integer> bag : edges) {
                count += bag.size();
            }
            return count/2;
        }

        /**
         * Add edge v-w to this graph
         */
        public void addEdge(int v, int w) {
            edges[v].add(w);
            edges[w].add(v);
        }

        /**
         * @return the vertices adjacent to v
         */
        public Iterable<Integer> adj(int v) {
            return edges[v];
        }
    }
}
