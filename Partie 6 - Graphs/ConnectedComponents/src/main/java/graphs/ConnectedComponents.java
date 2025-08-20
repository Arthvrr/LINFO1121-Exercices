package graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * You are asked to implement the ConnectedComponent class given a graph.
 * The Graph class available in the code is the one of the Java class API.
 * <p>
 * public class ConnectedComponents {
 * <p>
 * public static int numberOfConnectedComponents(Graph g){
 * // TODO
 * return 0;
 * }
 * }
 * Hint: Feel free to add methods or even inner-class (private static class)
 *       to help you but don't change the class name or the signature of the numberOfConnectedComponents method.
 *
 *
 */
public class ConnectedComponents {

    public static void main(String[] args) {
        ConnectedComponents.Graph graph = new ConnectedComponents.Graph(6);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        System.out.println(numberOfConnectedComponents(graph));
    }

    /**
     * @return the number of connected components in g
     */
    public static int numberOfConnectedComponents(Graph g) {
        // TODO
        int counter = 0;
        boolean[] marked = new boolean[g.V()];
        for (int v = 0; v < g.V(); v++){
            if (!marked[v]){
                dfs(g, v, marked); //on lance le dfs à partir du noeud v (DFS GLOBAL, pour trouver un nouveau composant connexe)
                counter++;
            }
        }
        return counter;
    }

    private static void dfs(Graph g, int v, boolean[] marked){
        marked[v] = true; //on marque le noeud actuel comme visité
        for (int w : g.adj(v)){ //on parcoure tout les voisins de v
            if (!marked[w]){
                dfs(g,w, marked); //DFS LOCAL, permet de marquer à true tout les noeuds voisins, on ne découvre pas de nouveaux composants ici
            }
        }
    }

    static class Graph {

        private List<Integer>[] edges;

        public Graph(int nbNodes){
            this.edges = (ArrayList<Integer>[]) new ArrayList[nbNodes];
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