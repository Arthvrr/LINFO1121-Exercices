package graphs;

import java.util.LinkedList;
import java.util.Queue;


/**
 * Let's consider a forest represented as a 2D grid.
 * Each cell of the grid can be in one of three states:
 *
 * 0 representing an empty spot.
 * 1 representing a tree.
 * 2 representing a burning tree (indicating a wildfire).
 *
 * The fire spreads from a burning tree to its four neighboring cells (up, down, left, and right) if there's a tree there.
 * Each minute, the trees in the neighboring cells of burning tree catch on fire.
 *
 * Your task is to calculate how many minutes it would take for the fire to spread throughout the forest i.e. to burn all the trees.
 *
 * If there are trees that cannot be reached by the fire (for example, isolated trees with no adjacent burning trees),
 * we consider that the fire will never reach them and -1 is returned.
 *
 * The time-complexity of your algorithm must be O(n) with n the number of cells in the forest.
 */
public class Wildfire {

    public static void main(String[] args) {
        int[][] forest1 = {
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 2, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1}
        };
        Wildfire wf = new Wildfire();
        wf.burnForest(forest1);
    }

    static final int EMPTY = 0;
    static final int TREE = 1;
    static final int BURNING = 2;


    /**
     * This method calculates how many minutes it would take for the fire to spread throughout the forest.
     *
     * @param forest
     * @return the number of minutes it would take for the fire to spread throughout the forest,
     *         -1 if the forest cannot be completely burned.
     */
    public int burnForest(int [][] forest) {
        // TODO
        /*
        lancer un BFS à chaque arbre cramé, et propager sur les voisins
         */
        int minutes = 0;
        Queue<Point> queue = new LinkedList<>();

        for (int i = 0; i < forest.length; i++){ //on ajoute à la Queue tout les arbres brulés
            for (int j = 0; j < forest[0].length; j++){
                if (forest[i][j] == BURNING){
                    queue.add(new Point(i,j,0));
                }
            }
        }

        int[] dx = new int[]{0,0,-1,1}; //GAUCHE-DROIT-HAUT-BAS
        int[] dy = new int[]{-1,1,0,0};

        while (!queue.isEmpty()){
            Point current = queue.poll();
            minutes = current.time;

            for (int k = 0; k < 4; k++){
                int newPosX = current.x + dx[k];
                int newPosY = current.y + dy[k];

                if (0 <= newPosX && newPosX < forest.length && //si dans les bornes de X
                    0 <= newPosY && newPosY < forest[0].length && //si dans les bornes de Y
                    forest[newPosX][newPosY] == TREE){ //si l'élément est un arbre pas encore brulé

                    queue.add(new Point(newPosX,newPosY, current.time + 1));
                    forest[newPosX][newPosY] = BURNING; //on change l'état de l'arbre
                }
            }
        }

        for (int i = 0; i < forest.length; i++){ //si on voit encore un arbre, c'est que le feu ne s'est pas propagé partout --> erreur
            for (int j = 0; j < forest[0].length; j++){
                if (forest[i][j] == TREE) return -1;
            }
        }
        return minutes;
    }

    public class Point{
        int x;
        int y;
        int time;

        public Point(int x, int y, int time){
            this.x = x;
            this.y = y;
            this.time = time;
        }
    }
}