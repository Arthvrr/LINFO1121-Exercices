
import java.util.Arrays;

public class ShortestPath {



    public static void main(String[] args) {

        int[][] graph = { //matrice d'adjacence
                {0, 4, 0, 0, 0, 0, 0, 8, 0},
                {4, 0, 8, 0, 0, 0, 0, 11, 0},
                {0, 8, 0, 7, 0, 4, 0, 0, 2},
                {0, 0, 7, 0, 9, 14, 0, 0, 0},
                {0, 0, 0, 9, 0, 10, 0, 0, 0},
                {0, 0, 4, 14, 10, 0, 2, 0, 0},
                {0, 0, 0, 0, 0, 2, 0, 1, 6},
                {8, 11, 0, 0, 0, 0, 1, 0, 7},
                {0, 0, 2, 0, 0, 0, 6, 7, 0}
        };

        dijkstra(graph, 0);
        bellmanFord(graph,0);

    }


    public static void dijkstra(int[][] graph, int src){
        int n = graph.length;
        int INF = Integer.MAX_VALUE;
        int[] dist = new int[n]; //distances des noeuds i depuis la source src
        boolean[] visited = new boolean[n]; //liste de booléens indiquant si le noeud i a été visité ou non

        Arrays.fill(dist, INF); //on pose d'abord la distance de chaque noeud avec la source à +INFINI
        dist[src] = 0; //distance de la source à la source (elle-même) vaut 0

        //Trouver à chaque itération le sommet non-visité le plus proche de la source
        for (int i = 0; i < n - 1; i++){

            //Trouver le sommet non visité avec la distance minimale
            int u = -1; //indice du noeud choisi
            int minDist = INF;

            for (int j = 0; j < n; j++){
                //si noeud j pas encore visité et distance src-j plus petite que minDist, on màj minDist
                if (!visited[j] && dist[j] < minDist){
                    minDist = dist[j];
                    u = j;
                }
            }

            visited[u] = true; //noeud u visité

            //Mettre à jour les distances des voisins
            for (int v = 0; v < n; v++){

                if (!visited[v] //ne pas revisiter un sommet déjà traité
                        && graph[u][v] != 0 //il existe une arête entre u et v
                        && dist[u] != INF //la distance actuelle pour u est valide
                        && dist[u] + graph[u][v] < dist[v]){ //la distance via u est plus courte

                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }

        //Affichage des résultats
        System.out.println("Distance depuis le sommet " + src + ":");
        for (int i = 0; i < n; i++){
            System.out.println("Vers " + i + " = " + dist[i]);
        }
    }


    public static void bellmanFord(int[][] graph, int src) {
        int n = graph.length;
        int INF = Integer.MAX_VALUE;
        int[] dist = new int[n];

        Arrays.fill(dist, INF);
        dist[src] = 0;

        // Boucle principale : relaxation des arêtes
        for (int i = 0; i < n - 1; i++) {
            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {
                    if (graph[u][v] != 0 && dist[u] != INF) {
                        if (dist[u] + graph[u][v] < dist[v]) {
                            dist[v] = dist[u] + graph[u][v];
                        }
                    }
                }
            }
        }

        // Vérification des cycles négatifs
        boolean hasNegativeCycle = false;
        for (int u = 0; u < n; u++) {
            for (int v = 0; v < n; v++) {
                if (graph[u][v] != 0 && dist[u] != INF && dist[u] + graph[u][v] < dist[v]) {
                    hasNegativeCycle = true;
                }
            }
        }

        if (hasNegativeCycle) {
            System.out.println("Le graphe contient un cycle de poids négatif !");
        } else {
            // Affichage des résultats
            System.out.println("Distance depuis le sommet " + src + ":");
            for (int i = 0; i < n; i++) {
                System.out.println("Vers " + i + " = " + dist[i]);
            }
        }
    }

}