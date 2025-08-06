package sorting;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Olympic Games organizers need to allocate facilities for the athletes' training sessions.
 * Each team has a schedule of training sessions with a start and end time
 * 
 * To organize the athletes' training smoothly, the organizing committee must know
 * the minimum number of facilities they need so that the teams can train without overlap.
 * Each team has given the organizers their training slots,
 * represented by two integers timestamps: the start (included) and end time (not included!) of their session,
 * Given the training sessions' time, write the `minFacilitiesRequired` function,
 * which returns the minimum number of required facilities.
 *
 * Example Input with its visual representation:
 *
 * int[][] sessions = {
 *     {12, 20},//   --------
 *     {14, 25},//     -----------
 *     {19, 22},//          ---
 *     {25, 30},//                -----
 *     {26, 30},//                 ----
 * };
 *
 * In this example, the minimum number of facilities required is 3
 * as at time 19, there are 3 sessions (intervals) overlapping,
 * namely [12, 20), [14, 25), and [19, 22).
 *
 *
 * More formally, the goal is to minimize k such that for all time t,
 * the number of sessions that overlap at time t is at most k
 *
 * The computation must be performed in O(n.log(n)) time complexity
 * where n is the number of training sessions.
 *
 *
 */
public class TrainingSessions {
    public static void main(String[] args) {
        int[][] sessions = {
                {9, 12},//          ---
                {3, 6}, //    ---
                {1, 4}, //  ---
                {2, 5}, //   ---
                {7, 8}, //        -
        };

        int[][] sessions2 = {
                {1, 4},   //  ---
                {4, 10},   //    ------
                {1, 10}    // ---------
        };

        TrainingSessions ts = new TrainingSessions();
        int result = ts.minFacilitiesRequired(sessions2);
    }

    /**
     * Determines the minimum number of facilities required to accommodate
     * all the training sessions without overlap.
     *
     * @param sessions a non-null array of int arrays where each int array represents
     *                 a session with start time and end time.
     * @return the minimum number of facilities required.
     */
    public int minFacilitiesRequired(int[][] sessions) {
        // TODO
        /*
        créer un dictionnaire avec toutes les valeurs en clé et leur counter en valeur (O(n))
        parcourir le dictionnaire et retourner le maximum
         */
        int maxi = 0; //entier que l'on va retourner

        ArrayList<Integer> start = new ArrayList<>(); //on stocke chaque heure de début et de fin dans une liste respective
        ArrayList<Integer> end = new ArrayList<>();

        for (int i = 0; i < sessions.length; i++){
            start.add(sessions[i][0]); //ajout des heures de début dans start & de fin dans end
            end.add(sessions[i][1]);
        }

        start.sort(null); //on trie les 2 tableaux
        end.sort(null);

        System.out.println(start);
        System.out.println(end);

        int start_idx = 0;
        int end_idx = 0;
        int tmp = 0;

        while (start_idx < start.size() && end_idx < end.size()){ //tant que start_idx & end_idx sont plus petit que la len de start & end
            if (start.get(start_idx) < end.get(end_idx)){ //si le start à start_idx est plus petit que l'end actuel, on rajoute +1 à tmp
                tmp++;
                start_idx++; //on avance dans les heures de start
                maxi = Math.max(tmp,maxi); //on met à jour maxi si tmp l'a dépassé
            } else { //si le start à start_idx est plus grand que l'end actuel, on passe à un nouvel end
                tmp--; //on décrémente de 1 le nombre de sessions, une session à été fermée
                end_idx++;
            }
        }
        System.out.println(maxi);
        return maxi;
    }
}
