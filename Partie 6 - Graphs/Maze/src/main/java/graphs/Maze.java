package graphs;

import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Collections;


/**
 * We are interested in solving a maze represented
 * by a matrix of integers 0-1 of size nxm.
 * This matrix is a two-dimensional array.
 * An entry equal to '1' means that there
 * is a wall and therefore this position is not accessible,
 * while '0' means that the position is free.
 * We ask you to write a Java code to discover
 * the shortest path between two coordinates
 * on this matrix from (x1, y1) to (x2, y2).
 * The moves can only be vertical (up/down) or horizontal (left/right)
 * (not diagonal), one step at a time.
 * The result of the path is an Iterable of
 * coordinates from the origin to the destination.
 * These coordinates are represented by integers
 * between 0 and n * m-1, where an integer 'a'
 * represents the position x =a/m and y=a%m.
 * If the start or end position is a wall
 * or if there is no path, an empty Iterable must be returned.
 * The same applies if there is no path
 * between the origin and the destination.
 */
public class Maze {
    public static void main(String[] args) {
        int[][] maze = {
                {0, 1, 0, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 0, 1, 0},
                {1, 1, 0, 1, 0},
                {0, 0, 0, 0, 0}
        };

        Iterable<Integer> path = Maze.shortestPath(maze, 0, 0, 4, 4);

        if (!path.iterator().hasNext()) {
            System.out.println("Pas de chemin trouvé !");
        } else {
            System.out.println("Chemin trouvé :");
            for (int pos : path) {
                int x = Maze.row(pos, maze[0].length);
                int y = Maze.col(pos, maze[0].length);
                System.out.print("(" + x + "," + y + ") ");
            }
        }
    }

    public static Iterable<Integer> shortestPath(int[][] maze, int x1, int y1, int x2, int y2) {
        //TODO
        ArrayList<Integer> result = new ArrayList<>();
        int nRows = maze.length;
        int nCols = maze[0].length;
        if (maze[x1][y1] == 1 || maze[x2][y2] == 1) return result; //si p1 ou p2 mur, on retourne une liste vide
        if (x1 == x2 && y1 == y2){ //si même coordonnées, on retourne juste la liste avec (x1,y1)
            result.add(ind(x1,y1,nCols));
            return result;
        }

        Queue<Integer> queue = new LinkedList<>(); //queue pour le BFS
        HashSet<Integer> visited = new HashSet<>(); //pour stocker les noeuds déjà visité
        HashMap<Integer,Integer> parent = new HashMap<>(); //pour stocker les noeuds parents des noeuds visité pour reconstruire le shortest path

        int[] dx = new int[]{0,1,0,-1}; //GAUCHE-BAS-DROITE-HAUT
        int[] dy = new int[]{-1,0,1,0};

        int start = ind(x1,y1,nCols);
        int goal = ind(x2,y2,nCols);

        queue.add(start);
        visited.add(start);

        boolean found = false;

        while (!queue.isEmpty() && !found){
            int currentNumber = queue.poll();

            for (int i = 0; i < 4; i++){
                int newX = row(currentNumber,nCols) + dx[i];
                int newY = col(currentNumber,nCols) + dy[i];

                //si newNumber dans les limites de la map et ≠ de 1 (mur) et pas dans visité, on ajoute à la queue
                if (0 <= newX && newX < nRows && 0 <= newY && newY < nCols && maze[newX][newY] == 0){

                    int newNumber = ind(newX,newY,nCols);
                    if (!visited.contains(newNumber)){
                        visited.add(newNumber);
                        parent.put(newNumber,currentNumber);
                        if (newNumber == goal){
                            found=true;
                            break;
                        }
                        queue.add(newNumber);
                    }
                }
            }
        }

        if (!found) return result; //si pas trouvé on retourne une liste vide
        int current = goal;
        while (current != start){ //on remonte jusqu'au start
            result.add(current);
            current = parent.get(current);
        }
        result.add(start); //ajouter le noeud de départ
        Collections.reverse(result); //on reverse result pour bien avoir start-->goal
        return result;
    }

    public static int ind(int x, int y, int lg) { //convertit les données en un indice unique
        return x * lg + y;
    }

    public static int row(int pos, int mCols) { //récupére la ligne à partir d'un entier pos
        return pos / mCols;
    }

    public static int col(int pos, int mCols) { //récupérer la colonne à partir d'un entier pos
        return pos % mCols;
    }
}