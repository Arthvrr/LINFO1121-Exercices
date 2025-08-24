package exam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Given a set of n players, EACH WITH A DIFFERENT score at an arcade game,
 * you want to know what would be the rank of a given score among those.
 *
 * Your first task is to implement correctly the compareTo(Player o)
 * so that the sorting algorithm in the constructor
 * sorts the givens players in decreasing order of their score.
 *
 * Your second task is to implement the getRank(int score) method
 * to find the rank of a given score among the players.
 *
 * !!!! You cannot call the get(int i) more than 2 x log(n) times !!!
 * So that the time complexity is O(log(n)).
 *
 * The highest score is rank 0 and the lowest score is rank n-1.
 *
 * If the queried score is equal to a score that is stored in the players
 * they should have the same rank.
 *
 */
public class ArcadeRanking {

    public static void main(String[] args) {
        Player p1 = new Player(10,"A");
        Player p2 = new Player(20,"B");
        Player p3 = new Player(30,"C");
        Player p4 = new Player(40,"D");
        Player p5 = new Player(50,"E");

    List<Player> playersList = new ArrayList<Player>();
    playersList.add(p1);
    playersList.add(p2);
    playersList.add(p3);
    playersList.add(p4);
    playersList.add(p5);


    Players players = new PlayersImplem(playersList);

    getRank(players,25);


    }

    public static class PlayersImplem implements Players{
        private final List<Player> players;

        public PlayersImplem(List<Player> players){
            Collections.sort(players); //on trie la liste dans l'ordre décroissant
            this.players = players;
        }

        @Override
        public int size(){
            return players.size();
        }

        @Override
        public Player get(int i){
            return players.get(i);
        }

        @Override
        public String toString(){
            return players.toString();

        }
    }

    /**
     *  Stores the players in decreasing order of their score
     *  and allow to retrieve them in their sorted order with a get method.
     *  You don't need to modify this interface nor to implement it.
     *  All you know is that if you have correctly
     *  implemented the compareTo method in Player,
     *  the players are sorted in decreasing order of their score.
     *  It means that get(0) returns the player with the highest score.
     *  and get(size()-1) returns the player with the lowest score.
     */
    interface Players {
        int size();
        Player get(int i);
    }


    /**
     * Return the rank among the given players for the given score.
     *
     * In your algorithm, you cannot call the get(int i) method of players
     * more than 2 x log(n) times so that the time complexity is in O(log(n)).
     *
     * The highest score is rank 0 and the lowest score is rank n-1.
     * If the queried score is equal to a score that is stored in the players
     * they should have the same rank.
     * @param players the set of players, sorted in decreasing order of their score
     *                so that get(0) returns the player with the highest score.
     * @param score the score to find the rank of
     */
    public static int getRank(Players players, int score) {
        // TODO
        System.out.println(players);

        int lo = 0;
        int hi = players.size() - 1;

        while (lo <= hi){
            int mid = lo + (hi - lo) / 2;
            System.out.println("mid : " + mid);
            Player current = players.get(mid);

            if (score == current.score){
                return mid;
            } else if (score < current.score){ //si plus petit on va à droite
                lo = mid+1;
            } else { //si plus grand on va à gauche
                hi = mid-1;
            }

        }
        System.out.println("result : " + lo);
        return lo;
    }
}

class Player implements Comparable<Player> {
    public final int score;
    public final String pseudo;

    public Player(int score, String pseudo) {
        this.score = score;
        this.pseudo = pseudo;
    }

    /**
     * Compare two Players objects, so that they can
     * be sorted in decreasing order of their score
     * @param o the other Player to compare to
     */
    @Override
    public int compareTo(Player o) {
        // TODO
        return o.score-this.score; //inverse de la comparaison de base
    }

    @Override
    public boolean equals(Object obj) {
        return pseudo.equals(((Player) obj).pseudo);
    }

    @Override
    public int hashCode() {
        return pseudo.hashCode();
    }

    @Override
    public String toString() {
        return pseudo + ": " + score;
    }
}