package graphs;

import java.util.Queue;
import java.util.LinkedList;

/**
 * In this exercise, we revisit the GlobalWarming
 * class from the sorting package.
 * You are still given a matrix of altitude in
 * parameter of the constructor, with a water level.
 * All the entries whose altitude is under, or equal to,
 * the water level are submerged while the other constitute small islands.
 *
 * For example let us assume that the water
 * level is 3 and the altitude matrix is the following
 *
 *      | 1 | 3 | 3 | 1 | 3 |
 *      | 4 | 2 | 2 | 4 | 5 |
 *      | 4 | 4 | 1 | 4 | 2 |
 *      | 1 | 4 | 2 | 3 | 6 |
 *      | 1 | 1 | 1 | 6 | 3 |
 * 
 * If we replace the submerged entries
 * by _, it gives the following matrix
 *
 *      | _ | _ | _ | _ | _ |
 *      | 4 | _ | _ | 4 | 5 |
 *      | 4 | 4 | _ | 4 | _ |
 *      | _ | 4 | _ | _ | 6 |
 *      | _ | _ | _ | 6 | _ |
 *
 * The goal is to implement two methods that
 * can answer the following questions:
 *      1) Are two entries on the same island?
 *      2) How many islands are there
 *
 * Two entries above the water level are
 * connected if they are next to each other on
 * the same row or the same column. They are
 * **not** connected **in diagonal**.
 * Beware that the methods must run in O(1)
 * time complexity, at the cost of a pre-processing in the constructor.
 * To help you, you'll find a `Point` class
 * in the utils package which identified an entry of the grid.
 * Carefully read the expected time complexity of the different methods.
 */
public class GlobalWarming {

    public static void main(String[] args) {
        int [][] altitude = new int[][] {{1, 3, 3, 1, 3},
                {4, 2, 2, 4, 5},
                {4, 4, 1, 4, 2},
                {1, 4, 2, 3, 6},
                {1, 1, 1, 6, 3}};
        int waterLevel = 3;
        GlobalWarming gb = new GlobalWarming(altitude,waterLevel);
        int nIles = gb.nbIslands();
        System.out.println("nombre d'îles total " + nIles);

    }

    int[][] altitude;
    int waterLevel;
    private int nbIslands;
    private int[][] islands;
    private int currentIslandID;

    /**
     * Constructor. The run time of this method is expected to be in 
     * O(n x log(n)) with n the number of entry in the altitude matrix.
     *
     * @param altitude the matrix of altitude
     * @param waterLevel the water level under which the entries are submerged
     */
    public GlobalWarming(int [][] altitude, int waterLevel) {
        //TODO
        this.altitude = altitude;
        this.waterLevel = waterLevel;
        nbIslands = 0;
        islands = new int[altitude.length][altitude[0].length]; //tableau de même taille qu'altitude initialisés de 0
        currentIslandID = 1;

        //on parcoure tout les points de la map et si besoin, lancement d'un BFS
        for (int i = 0; i < altitude.length; i++){
            for (int j = 0; j < altitude[0].length; j++){
                if (altitude[i][j] > waterLevel && islands[i][j] == 0){ //si point au dessus de waterLevel et pas encore visitée, BFS
                    bfsIsland(altitude,i,j);
                    currentIslandID++; //on incrémente le nombre d'îles total et l'ID de l'île
                    nbIslands++;
                }
            }
        }
        for (int i = 0; i < islands.length; i++) {
            for (int j = 0; j < islands[0].length; j++) {
                System.out.print(islands[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Returns the number of island
     *
     * Expected time complexity O(1)
     */
    public int nbIslands() {
        //TODO
        return nbIslands;
    }

    /**
     * Return true if p1 is on the same island as p2, false otherwise
     *
     * Expected time complexity: O(1)
     *
     * @param p1 the first point to compare
     * @param p2 the second point to compare
     */
    public boolean onSameIsland(Point p1, Point p2) {
        //TODO
        return (islands[p1.getX()][p1.getY()] == islands[p2.getX()][p2.getY()] && //les 2 points ont le même ID dans islands
                islands[p1.getX()][p1.getY()] != 0 && //p1 et p2 différent de 0 (sinon pas une île)
                islands[p2.getX()][p2.getY()] != 0);
    }

    /*
    Fonction qui va effectuer le BFS dans la matrice altitude
    Met à jour la valeur de nbIslands
    Note les ids des cases d'îles dans islands
     */
    public void bfsIsland(int[][] altitude, int x, int y){
        Queue<Point> queue = new LinkedList<>();
        int n_lignes = altitude.length;
        int n_colonnes = altitude[0].length;
        int[] pos_x = new int[]{-1,0,1,0};
        int[] pos_y = new int[]{0,1,0,-1}; //GAUCHE-BAS-DROITE-HAUT
        queue.add(new Point(x,y)); //on ajoute le premier point à la queue
        islands[x][y] = currentIslandID;

        while (!queue.isEmpty()){ //continuer tant qu'elle n'est pas vide
            Point currPoint = queue.poll();
            for (int i = 0; i < 4; i++){
                int newPosX = currPoint.getX() + pos_x[i];
                int newPosY = currPoint.getY() + pos_y[i];

                //si dans les limites de la map & au-dessus de waterLevel
                if (0 <= newPosX && newPosX < n_lignes && 0 <= newPosY && newPosY < n_colonnes && waterLevel < altitude[newPosX][newPosY] && islands[newPosX][newPosY] == 0){
                    islands[newPosX][newPosY] = currentIslandID;
                    queue.add(new Point(newPosX,newPosY)); //on l'ajoute à sa queue pour regarder ses 4 voisins
                }
            }
        }
    }

    /**
     * This class represent a point in a 2-dimension discrete plane. This is used, for instance, to
     * identified cells of a grid
     */
    static class Point {

        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Point) {
                Point p = (Point) o;
                return p.x == this.x && p.y == this.y;
            }
            return false;
        }
    }
}