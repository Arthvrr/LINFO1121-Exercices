package graphs;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Sophie and Marc want to reduce the bubbles
 * of contacts in the belgian population
 * to contain an evil virus (weird idea but
 * nevertheless inspired by a true belgian
 * story in 2020, don't ask ...).
 *
 * Help them!
 *
 * The Belgian government has imposed on the
 * population to limit the number of contacts, via "bubbles".
 *
 * The principle is quite simple:
 * If you have a (close) contact with someone,
 * You are then in his bubble, and he is in yours.
 *
 * Let's say the following contacts have taken place: [
 * [Alice, Bob], [Alice, Carole], [Carole, Alice], [Eve, Alice], [Alice, Frank],
 * [Bob, Carole], [Bob, Eve], [Bob, Frank], [Bob, Carole], [Eve, Frank]
 * ].
 *
 * Note that the contacts are two-by-two and
 * can occur several times. The order within
 * of a contact does not matter.
 *
 * The resulting bubbles are :
 *
 * - Alice's bubble = [Bob, Carole, Eve, Frank]
 * - Bob's bubble = [Alice, Carole, Eve, Frank]
 * - Carole's bubble = [Alice, Bob]
 * - Eve's bubble = [Alice, Bob, Frank]
 * - Frank's Bubble = [Alice, Bob, Eve]
 *
 * Note that the relationship is symmetric
 * (if Alice is in Bob's bubble, then Bob is in Alice's bubble)
 * but not transitive (if Bob is in Alice's bubble,
 * then Bob is in Alice's bubble)
 * and Carol is in Bob's bubble, Carol is
 * not necessarily in Alice's.
 *
 * Since at most n people can be in someone's
 * bubble without him being outlaw
 * given a list of contacts, select pairs of people
 * that you will forbid to meet, so that eventually
 * each person has a bubble of NO MORE than n people
 * (not counting themselves).
 * You need to ban AS FEW AS POSSIBLE (pairs of) people to meet.
 *
 * For example, if n = 3, in the example above,
 * you could forbid Alice and Carol to see each other, but also
 * Bob and Carol. This removes 2 links
 * (even though Alice and Carol appear twice in the contacts!).
 * But there is a better solution: prevent Alice and Bob
 * from seeing each other, which only removes one link.
 * Finding an algorithm that solves this problem is complex,
 * that's why we give you a rather vague idea of an algorithm:
 *
 * - As long as there are links between two bubbles
 *   each "too big", remove one of these links;
 * - Then, as long as there are bubbles that are too big,
 *   remove any link connected to one of these bubbles
 *   (removing is equivalent to "adding the link
 *   to the list of forbidden relationships")
 *
 * Implementing this algorithm as it is a bad idea.
 * Think of an optimal way to implement it in the
 * method {@code cleanBubbles}
 *
 * You CANNOT modify the `contacts` list directly (nor the lists inside)
 * If you try, you will receive an UnsupportedOperationException.
 *
 */
public class Bubbles {

    public static void main(String[] args) {
        List<Contact> contacts = Collections.unmodifiableList(Arrays.asList(
                new Contact("Alice", "Bob"),
                new Contact("Alice", "Carole"),
                new Contact("Carole", "Alice"),
                new Contact("Eve", "Alice"),
                new Contact("Alice", "Frank"),
                new Contact("Bob", "Carole"),
                new Contact("Bob", "Eve"),
                new Contact("Bob", "Frank"),
                new Contact("Bob", "Carole"),
                new Contact("Bob", "Eve"),
                new Contact("Bob", "Frank"),
                new Contact("Bob", "Carole"),
                new Contact("Eve", "Frank")
        ));
        cleanBubbles(contacts,3);
    }

    /**
     *
     * @param contacts
     * @param n the number of persons in the population ranges from 0 to n-1 (included)
     * @return a list of people you are going to forbid to see each other.
     *         There MUST NOT be any duplicates.
     *         The order doesn't matter, both within the
     *         ForbiddenRelation and in the list.
     */
    public static List<ForbiddenRelation> cleanBubbles(List<Contact> contacts, int n) {
        // TODO
        HashMap<String,HashSet<String>> map = new HashMap<>();

        for (Contact c : contacts) { //construction du HashMap
            String person = c.a;
            String other = c.b;

            if (!map.containsKey(person)){
                HashSet<String> hashPerson = new HashSet<>();
                hashPerson.add(other);
                map.put(person,hashPerson);
            } else {
                HashSet<String> hashPerson = map.get(person);
                hashPerson.add(other);
                map.put(person,hashPerson);
            }

            if (!map.containsKey(other)){
                HashSet<String> hashOther = new HashSet<>();
                hashOther.add(person);
                map.put(other,hashOther);
            } else {
                HashSet<String> hashOther = map.get(other);
                hashOther.add(person);
                map.put(other,hashOther);
            }
        }
        System.out.println(map);

        ArrayList<ForbiddenRelation> deleted = new ArrayList<>();

        //1. on supprime d'abord 2 personnes ayant une bulle surchargée et qui sont tout 2 dans la bulle de l'autre
        for (Contact c : contacts){
            String person = c.a;
            String other = c.b;

            if (!map.get(person).contains(other)) continue; //si other n'est pas dans bulle de person et inversément, on continue

            if (map.get(person).size() > n && map.get(other).size() > n){ //other dans bulle de person et inversément, si les 2 dépasse n on supprime leur lien
                deleted.add(new ForbiddenRelation(person,other));
                map.get(person).remove(other);
                map.get(other).remove(person);
            }
        }

        //2. on supprime ensuite les liens de 2 personnes si une de ces 2 personnes a encore une bulle surchargée
        for (Contact c : contacts){
            String person = c.a;
            String other = c.b;

            if (!map.get(person).contains(other)) continue; //si other n'est pas dans bulle de person et inversément, on continue

            if (map.get(person).size() > n || map.get(other).size() > n){ //other dans bulle de person et inversément, si un des 2 dépasse n on supprime leur lien
                deleted.add(new ForbiddenRelation(person,other));
                map.get(person).remove(other);
                map.get(other).remove(person);
            }
        }
        return deleted;
    }
}



class Contact {
    public final String a, b;

    public Contact(String a, String b) {
        // We always force a < b for simplicity.
        if(a.compareTo(b) > 0) {
            this.b = a;
            this.a = b;
        }
        else {
            this.a = a;
            this.b = b;
        }
    }
}

class ForbiddenRelation implements Comparable<ForbiddenRelation> {
    public final String a, b;

    public ForbiddenRelation(String a, String b) {
        // We always force a < b for simplicity.
        if(a.compareTo(b) > 0) {
            this.b = a;
            this.a = b;
        }
        else {
            this.a = a;
            this.b = b;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ForbiddenRelation)
            return a.equals(((ForbiddenRelation) obj).a) && b.equals(((ForbiddenRelation) obj).b);
        return false;
    }

    @Override
    public int compareTo(ForbiddenRelation o) {
        if(a.equals(o.a))
            return b.compareTo(o.b);
        return a.compareTo(o.a);
    }
}