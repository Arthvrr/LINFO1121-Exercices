package graphs;

import java.util.*;

/**
 * Author Pierre Schaus
 *
 * Assume the following 5x5 matrix that represent a grid surface:
 * int [][] tab = new int[][] {{1,3,3,1,3},
 *                             {4,2,2,4,5},
 *                             {4,4,5,4,2},
 *                             {1,4,2,3,6},
 *                             {1,1,1,6,3}};
 *
 * Given a global water level, all positions in the matrix
 * with a value <= the water level are flooded and therefore unsafe.
 * So, assuming the water level is 3,
 * all safe points are highlighted between parenthesis:
 *
 *   1 , 3 , 3 , 1 , 3
 *  (4), 2 , 2 ,(4),(5)
 *  (4),(4),(5),(4), 2
 *   1 ,(4), 2 , 3 ,(6)
 *   1 , 1 , 1 ,(6), 3}
 *
 * The method you need to implement is
 * a method that find a safe-path between
 * two positions (row,column) on the matrix.
 * The path assume you only make horizontal or vertical moves
 * but not diagonal moves.
 *
 * For a water level of 4, the shortest path
 * between (1,0) and (1,3) is
 * (1,0) -> (2,0) -> (2,1) -> (2,2) -> (2,3) -> (1,3)
 *
 *
 * Complete the code below so that the {@code  shortestPath} method
 * works as expected
 */
public class GlobalWarmingPaths {

    public static void main(String[] args) {
        int [][] tab = new int[][]{
                {1,3,3,1,3},
                {4,2,2,4,5},
                {4,4,1,4,2},
                {1,4,2,3,6},
                {1,1,1,6,3}};
        Point start = new Point(1,0);
        Point end = new Point(3,1);
        GlobalWarmingPaths gwp = new GlobalWarmingPaths(tab,3);

        gwp.shortestPath(start,end);
    }

    int waterLevel;
    int [][] altitude;

    public GlobalWarmingPaths(int[][] altitude, int waterLevel) {
        this.waterLevel = waterLevel;
        this.altitude = altitude;
        //TODO
    }


    /**
     * Computes the shortest path between point p1 and p2
     * @param p1 the starting point
     * @param p2 the ending point
     * @return the list of the points starting
     *         from p1 and ending in p2 that corresponds
     *         the shortest path.
     *         If no such path, an empty list.
     */
    public List<Point> shortestPath(Point p1, Point p2) {
        //TODO
        // expected time complexity O(n^2)

        if (p1.getX() == p2.getX() && p1.getY() == p2.getY()){ //si mêmes coordonnées retourner une liste vide
            ArrayList<Point> onePoint = new ArrayList<>();
            if (altitude[p1.getX()][p1.getY()] > waterLevel) onePoint.add(p1); //on ajoute le point uniquement si au dessus du niveau de la mer
            return onePoint;

        }
        List<Point> result = new ArrayList<>();
        Queue<Point> queue = new LinkedList<>();
        HashMap<Point,Point> map = new HashMap<>(); //pour stocker noeud parent-enfant
        HashSet<Point> visited = new HashSet<>(); //pour retenir les liens déjà visités
        boolean found = false;

        int[] dx = new int[]{0,0,1,-1}; //GAUCHE-DROITE-BAS-HAUT
        int[] dy = new int[]{-1,1,0,0};

        queue.add(p1); //on ajoute le point de départ de la Queue
        visited.add(p1);
        while (!queue.isEmpty()){
            Point currentPoint = queue.poll();

            for (int i = 0; i < 4; i++){

                int newPosX = currentPoint.getX() + dx[i];
                int newPosY = currentPoint.getY() + dy[i];

                if (p2.getX() == newPosX && p2.getY() == newPosY){ //trouvé
                    queue.clear();
                    found = true;
                    map.put(new Point(newPosX, newPosY), currentPoint); //ajout clé : nouveau noeud valeur : parent dans le HashMap
                    break;
                }

                if (0 <= newPosX && newPosX < altitude.length && //si dans les bornes de X
                        0 <= newPosY && newPosY < altitude[0].length && //si dans les bornes de Y
                        altitude[newPosX][newPosY] > waterLevel && //si au-dessus du niveau de l'eau
                        !visited.contains(new Point(newPosX,newPosY))){ //si pas encore dans les noeuds visités
                    visited.add(new Point(newPosX,newPosY)); //on ajoute le noeud au set des noeuds visités
                    queue.add(new Point(newPosX,newPosY)); //on l'ajoute à la queue
                    map.put(new Point(newPosX,newPosY),currentPoint); //ajout clé : nouveau noeud valeur : parent dans le HashMap
                }
            }
        }

        if (!found){ //si pas de chemin correct trouvé vers p2, on retourne une liste vide
            return new ArrayList<>();
        }

        //reconstruction du chemin goal-->start pour ensuite inverser la liste
        System.out.println(map);
        result.add(p2); //on ajoute le goal au départ
        Point current = map.get(p2);
        while (current != p1){
            result.add(current);
            current = map.get(current);
        }
        result.add(p1); //on ajoute le point de départ à la fin de la liste
        Collections.reverse(result); //on reverse la liste pour avoir start-->goal
        System.out.println(result);
        return result;
    }

    /**
     * This class represent a point in a 2-dimension discrete plane.
     * This is used to identify the cells of a grid
     * with X = row, Y = column
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

        @Override
        public int hashCode() {
            // On génère un code de hachage basé sur les coordonnées (x,y).
            // Cela garantit que deux points ayant les mêmes coordonnées
            // auront le même hashCode, ce qui est essentiel pour
            // que les HashMap / HashSet fonctionnent correctement
            // en cohérence avec equals().
            return Objects.hash(x, y);
        }

        @Override
        public String toString(){
            return "Point:(" + x + "," + y + ")";
        }
    }
}
