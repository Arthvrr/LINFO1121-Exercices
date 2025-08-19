import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;


public class GraphSearch {

    public static void main(String[] args) {

        HashMap<String, List<String>> graph = new HashMap<>();
        graph.put("A", Arrays.asList("B", "C"));
        graph.put("B", Arrays.asList("D", "E"));
        graph.put("C", Arrays.asList("F"));
        graph.put("D", new ArrayList<>());
        graph.put("E", Arrays.asList("F"));
        graph.put("F", new ArrayList<>());

        //Lancement DFS
        System.out.println("DFS :");
        HashSet<String> visited = new HashSet<>();
        dfs(graph, "A", visited);

        //Lancement BFS
        System.out.println("BFS :");
        bfs(graph, "A");
    }

    public static void dfs(HashMap<String, List<String>> graph, String start, HashSet<String> visited) {
        if (visited.contains(start)) return;

        visited.add(start);
        System.out.println(start); //visite du noeud

        for (String neighbor : graph.get(start)) {
            if (!visited.contains(neighbor)) {
                dfs(graph, neighbor, visited);
            }
        }
    }

    public static void bfs(HashMap<String, List<String>> graph, String start) {
        HashSet<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(start);

        while (!queue.isEmpty()) {
            String node = queue.poll();

            if (!visited.contains(node)) {
                System.out.println(node); //visite du noeud
                visited.add(node);

                //on ajoute tous les voisins
                queue.addAll(graph.get(node));
            }
        }
    }

}