package graphs;

import java.util.ArrayList;

/**
 * Implement the Digraph.java interface in
 * the Digraph.java class using an adjacency list
 * data structure to represent directed graphs.
 */
public class Digraph {

    int nVertices;
    int nEdges;
    ArrayList<Integer>[] adj; //tableau d'ArrayList où chaque Arraylist contient les sommets voisins

    public Digraph(int V) { //Constructeur
        // TODO
        nVertices = V;
        nEdges = 0;
        adj = new ArrayList[nVertices];

        for (int i = 0; i < nVertices;i++){
            adj[i] = new ArrayList<>(); //on crée une ArrayList pour chaque élément du tableau
        }
    }

    /**
     * The number of vertices
     */
    public int V() {
        // TODO
        return nVertices;
    }

    /**
     * The number of edges
     */
    public int E() {
        // TODO
        return nEdges;
    }

    /**
     * Add the edge v->w
     */
    public void addEdge(int v, int w) {
        // TODO
        adj[v].add(w);
        nEdges++;
    }

    /**
     * The nodes adjacent to node v
     * that is the nodes w such that there is an edge v->w
     */
    public Iterable<Integer> adj(int v) {
        // TODO
        return adj[v];
    }

    /**
     * A copy of the digraph with all edges reversed
     */
    public Digraph reverse() {
        // TODO
        Digraph reverse = new Digraph(nVertices);
        for (int v = 0; v < nVertices; v++){ //parcours de chaque sommet
            for (int w : adj[v]){ //parcours de chaque ArrayList de chaque sommet
                reverse.addEdge(w,v); //opération inverse
            }
        }
        return reverse;
    }
}
