package searching;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * In this exercise, you must compute the skyline defined by a set of buildings.
 * When viewed from far away, the buildings only appear as if they were on a two-dimensionnal line.
 * Hence, they can be defined by three integers: The start of the building (left side), the height
 * of the building and the end of the building (right side).
 * For example, a building defined by (2, 5, 4) would look like
 *
 *   xxx
 *   xxx
 *   xxx
 *   xxx
 *   xxx
 * ________
 *
 * Obviously in practice buildings are not on a line, so they can overlap. If we add a new, smaller,
 * building in front of the previous one, defined by (3, 3, 6), then the view looks like:
 *
 *   xxx
 *   xxx
 *   xyyyy
 *   xyyyy
 *   xyyyy
 * ________
 *
 * The skyline is then define as the line that follows the highest building at any given points.
 * Visually, for the above example, it gives:
 *
 *   sss
 *      
 *      ss
 *        
 *        
 * ________
 *
 * Input:
 * int[][] buildings = {{2, 5, 4}, {3, 3, 6}};
 * Output:
 * {{2,5},{5,3},{7,0}};
 *
 *
 * We ask you to compute, given a set of building, their skyline.
 */
public class Skyline {

    public static void main(String[] args) {
        int[][] input ={{2, 5, 4}, {3, 3, 6}};
        int[][] input2 = {{1, 11, 5}, {2, 6, 7}, {3, 13, 9}, {12, 7, 16}, {14, 3, 25}, {19, 18, 22}, {23, 13, 29}, {24, 4, 28}};
        int[][] input3 = {{0,4,7},{0,8,6},{6,6,12},{6,4,16},{10,5,20},{22,2,26}};
        getSkyline(input);
        getSkyline(input2);
        getSkyline(input3);
    }


    /**
     *   The buildings are defined with triplets (left, height, right).
     *         int[][] buildings = {{1, 11, 5}, {2, 6, 7}, {3, 13, 9}, {12, 7, 16}, {14, 3, 25}, {19, 18, 22}, {23, 13, 29}, {24, 4, 28}};
     *
     *         {{1,11},{3,13},{10,0},{12,7},{17,3},{19,18},{23,13},{30,0}};
     *
     * @param buildings
     * @return  the skyline in the form of a list of "key points [x, height]".
     *          A key point is the left endpoint of a horizontal line segment.
     *          The key points are sorted by their x-coordinate in the list.
     */
    public static List<int[]> getSkyline(int[][] buildings) {

        ArrayList<int[]> result = new ArrayList<>(); //matrice de résultat que l'on va retourner
        TreeMap<Integer,ArrayList<Integer>> map = new TreeMap<>(); //clés : indices 0-n+1 et valeurs : tailles de buildings

        int max_end = 0; //index de fin, pour ajouter le dernier tuple {max_end,0}
        for (int i = 0; i < buildings.length;i++){ //on parcoure toutes les fins de bâtiments pour trouver l'index maximal de l'input
            if (buildings[i][2] > max_end) max_end = buildings[i][2];
        }

        //on ajoute à map tout les indices de 0 à n+1 en clé et en valeur une liste vide
        for (int i = 0; i <= max_end+1; i++){
            map.put(i,new ArrayList<Integer>());
        }

        //on parcoure notre input, et on ajoute pour chaque index les hauteurs possibles
        for (int j = 0; j < buildings.length; j++){
            int idx_start = buildings[j][0];
            int height = buildings[j][1];
            int idx_end = buildings[j][2];

            while (idx_start <= idx_end){
                map.get(idx_start).add(height);
                idx_start++;
            }
        }

        System.out.println(map);

        int tmpHeight = 0; //hauteur temporaire, si changement on save dans result
        int tmpIndex = 0; //index temporaire, si changement à tmpHeight on save dans result

        for (Map.Entry<Integer,ArrayList<Integer>> entry: map.entrySet()){
            Integer key = entry.getKey(); //index actuel itéré
            ArrayList<Integer> values_list = entry.getValue(); //listes de hauteurs à l'index key
            values_list.sort(null); //on trie values_list pour trouver la valeur maximale
            //System.out.println("key : " + key + " & values_list : " + values_list);

            if (values_list.isEmpty() && result.isEmpty()){ //si values_list est vide et que result aussi, on continue on est toujours à hauteur 0 au début
                continue;
            }

            if (!values_list.isEmpty()){ //si values_list n'est pas vide, on a une hauteur positive, on prend la hauteur maximale de values_list
                int current_max_height = values_list.get(values_list.size()-1);
                if (current_max_height == tmpHeight){ //si current_max_height = tmpHeight, on est toujours sur la même hauteur, on laisse comme ça
                    continue;
                } else { //si current_max_height ≠ tmpHeight, on push le tuple {tmpIndex,tmpHeight} et on met à jour tmpHeight et tmpIndex
                    tmpIndex = key;
                    tmpHeight = current_max_height;
                    result.add(new int[]{tmpIndex,tmpHeight});
                }
            } else { //si values_list est vide, on est redescendu à l'étage 0
                if (tmpHeight == 0){ //si tmpHeight = 0, on était déjà à l'étage 0 à l'index précédent, on continue
                    continue;
                } else { //si tmpHeight ≠ 0, on était à un étage positif à l'index précédent, on push dans result {key,0}
                    tmpIndex = key;
                    tmpHeight = 0;
                    result.add(new int[]{tmpIndex,tmpHeight});
                }
            }
        }
        for (int[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
        return result;
    }
}