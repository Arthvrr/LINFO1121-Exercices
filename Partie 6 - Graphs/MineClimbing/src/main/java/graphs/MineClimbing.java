package graphs;

import java.util.Arrays;
import java.util.PriorityQueue;


/**
 * You just bought yourself the latest game from the Majong™
 * studio (recently acquired by Macrosoft™): MineClimb™.
 * In this 3D game, the map is made up of size 1
 * dimensional cube blocks, aligned on a grid,
 * forming vertical columns of cubes.
 * There are no holes in the columns, so the state
 * of the map can be represented as a matrix M of size n⋅m
 * where the entry M_{i,j} is the height of
 * the cube column located at i,j (0 ≤ i < n, 0 ≤ j < m).
 * You can't delete or add cubes, but you do have
 * climbing gear that allows you to move from one column to another
 * (in the usual four directions (north, south, east, west),
 * but not diagonally). The world of MineClimb™ is round:
 * the position north of (0,j) is (n-1,j), the position
 * west of (i,0) is (i,m-1) , and vice versa.
 * <p>
 * The time taken to climb up or down a column depends on
 * the difference in height between the current column and the next one.
 * Precisely, the time taken to go from column (i,j)
 * to column (k,l) is |M_{i,j}-M_{k,l}|
 * <p>
 * Given the map of the mine, an initial position
 * and an end position, what is the minimum time needed
 * to reach the end position from the initial position?
 */
public class MineClimbing {

    public static class Cell implements Comparable<Cell>{
        int x;
        int y;
        int cost;

        Cell(int x, int y, int cost){
            this.x = x;
            this.y = y;
            this.cost = cost;
        }

        @Override
        public int compareTo(Cell other) {
            return Integer.compare(this.cost,other.cost);
        }
    }

    /**
     * Returns the minimum distance between (startX, startY) and (endX, endY), knowing that
     * you can climb from one mine block (a,b) to the other (c,d) with a cost Math.abs(map[a][b] - map[c][d])
     * <p>
     * Do not forget that the world is round: the position (map.length,i) is the same as (0, i), etc.
     * <p>
     * map is a matrix of size n times m, with n,m > 0.
     * <p>
     * 0 <= startX, endX < n
     * 0 <= startY, endY < m
     */
    public static int best_distance(int[][] map, int startX, int startY, int endX, int endY) {
        // TODO
        /*
        trouver le chemin le moins cher entre un point X et Y --> Dijkstra
         */
        int rows = map.length;
        int cols = map[0].length;
        int[][] dist = new int[rows][cols]; //matrice qui va garder le cout minimal pour atteindre chaque cellule depuis le départ

        for (int i = 0; i < rows; i++) { //on initialise tout les coûts à +INFINI au départ
            for (int j = 0; j < cols; j++) {
                dist[i][j] = Integer.MAX_VALUE;
            }
        }

        PriorityQueue<Cell> pq = new PriorityQueue<>(); //PriorityQueue pour toujours traiter le point avec le coût minimal connu en premier
        dist[startX][startY] = 0; //distance de 0 entre la source et elle-même
        pq.add(new Cell(startX,startY,0));

        int[] dx = new int[]{0,0,-1,1}; //GAUCHE-DROIT-HAUT-BAS
        int[] dy = new int[]{-1,1,0,0};

        while (!pq.isEmpty()){
            Cell current = pq.poll();

            if (current.cost > dist[current.x][current.y]) continue; //si on a déjà un meilleur chemin, on ignore

            for (int i = 0; i < 4; i++){
                int newPosX = (current.x + dx[i] + rows) % rows; //pour gérer les index circulaires
                int newPosY = (current.y + dy[i] + cols) % cols;

                int newCost = dist[current.x][current.y] + Math.abs(map[current.x][current.y] - map[newPosX][newPosY]);

                if (newCost < dist[newPosX][newPosY]){
                    dist[newPosX][newPosY] = newCost; //màj du cout (plus petit)
                    pq.add(new Cell(newPosX,newPosY,newCost));
                }
            }
        }
        return dist[endX][endY];
    }
}