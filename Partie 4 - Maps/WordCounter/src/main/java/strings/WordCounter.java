package strings;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implement the class WordCounter that counts the number of occurrences
 * of each word in a given piece of text.
 * Feel free to use existing java classes.
 */
public class WordCounter implements Iterable<String> {

    public TreeMap<String,Integer> map;

    public WordCounter() {
        map = new TreeMap<>();
    }

    /**
     * Add the word so that the counter of the word is increased by 1
     */
    public void addWord(String word) {
        map.put(word,map.getOrDefault(word,0)+1);
    }

    /**
     * Return the number of times the word has been added so far
     */
    public int getCount(String word) {
        if (!map.containsKey(word)) return 0; //mot pas présent dans le dictionnaire
        return map.get(word);
    }

    // iterate over the words in ascending lexicographical order
    @Override
    public Iterator<String> iterator() {
         return map.keySet().iterator();
    }

    public Iterator<Integer> values_iterator(){
        return map.values().iterator();
    }

    public Iterator<Map.Entry<String,Integer>> entry_iterator() {
        return map.entrySet().iterator();
    }

}