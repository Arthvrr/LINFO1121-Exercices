package fundamentals;

import java.util.*;

/**
 * Author Pierre Schaus
 *
 * The SegmentedList is a linear structure that consists of several segments (or groups) of elements,
 * where each segment can have its own size.
 * It is like a list of lists, but the user interacts with it as a single linear sequence.
 * The iterator will need to traverse each segment in order, element by element,
 * seamlessly presenting the entire structure as a single flat sequence.
 *
 * Be careful with the iterator, it should implement the fail-fast behavior.
 * A fail-fast iterator detects illegal concurrent modification during iteration (see the tests).
 *
 * Example:
 *
 *         SegmentedList<Integer> segmentedList = create();
 *
 *         // Add segments
 *         List<Integer> segment1 = new ArrayList<>();
 *         segment1.add(1); segment1.add(2); segment1.add(3);
 *         segmentedList.addSegment(segment1); // add [1,2,3]
 *
 *         List<Integer> segment2 = new ArrayList<>();
 *         segment2.add(4); segment2.add(5);
 *         segmentedList.addSegment(segment2); // add [4,5]
 *
 *         List<Integer> segment3 = new ArrayList<>();
 *         segment3.add(6); segment3.add(7); segment3.add(8); segment3.add(9);
 *         segmentedList.addSegment(segment3); // add [6,7,8,9]
 *
 *         segmentedList.removeSegment(1); // remove [4,5], the segment in second position
 *
 *         // Iterate through the SegmentedList that is elements 1,2,3,6,7,8,9
 *         for (Integer value : segmentedList) {
 *             System.out.println(value);
 *         }
 *
 *         // Example of using get()
 *         System.out.println("Element at global index 4: " + segmentedList.get(4)); // Should print 5
 *
 * @param <T>
 */
public interface SegmentedList<T> extends Iterable<T> {

    // Add a new segment (list) to the SegmentedList.
    void addSegment(List<T> segment);

    // Remove a segment by its index.
    void removeSegment(int index);

    // Get the total size of the segmented list (across all segments).
    int size();

    // Retrieve an element at a global index (spanning all segments).
    T get(int globalIndex);

    // Static method inside the interface
    static <T> SegmentedList<T> create() {
        return new SegmentedListImpl<>();
    }

}

class SegmentedListImpl<T> implements SegmentedList<T> {

    // TODO: Implement the SegmentedList interface here

    private List<List<T>> segments = new ArrayList<>(); //utilisation d'ArrayList car accès d'index en 0(1)
    int segments_size = 0; //taille totale du segment
    private int modCount = 0; //compteur de modification

    // Add a new segment (list) to the SegmentedList.
    public void addSegment(List<T> segment) {
        segments.add(segment);
        segments_size += segment.size(); //on incrémente segments_size avec le nombre d'éléments stockés dans segment
        modCount++;
    }

    // Remove a segment by its index.
    public void removeSegment(int index) {

        if (0 <= index && index <= segments.size()){
            List<T> current_segment = segments.get(index); //segment actuel
            segments.remove(index);
            segments_size -= current_segment.size(); //on décrémente segments_size avec le nombre d'éléments dans current_segment
            modCount++;

        } //si index pas dans la plage d'index possible, on retourne une erreur OutOfBounds
        else throw new IndexOutOfBoundsException();
    }

    // Get the total size of the segmented list (across all segments).
    public int size() {
         return segments_size;
    }

    // Retrieve an element at a global index (spanning all segments).
    public T get(int globalIndex) {

        //si globalIndex hors des limites
        if (0 > globalIndex || globalIndex > segments_size){
            throw new IndexOutOfBoundsException();
        }

        int counter = 0;

        //on parcoure tout les segments de la liste
        for (List<T> current_segment : segments){
            if (counter + current_segment.size() > globalIndex){
                //on est dans le bon segment
                int good_index = globalIndex - counter;
                return current_segment.get(good_index);
            }
            counter += current_segment.size(); //sinon, on incrémente le counter avec la taille du segment actuel
        }
        return null;
    }

    // Return an iterator for the segmented list.
    @Override
    public Iterator<T> iterator() {
         return new SegmentedListIterator();
    }

    private class SegmentedListIterator implements Iterator<T> {
        private int segmentIndex = 0;
        private int elementIndex = 0;
        private int expectedModCount = modCount;

        @Override
        public boolean hasNext() {

            if (modCount != expectedModCount) throw new ConcurrentModificationException();

            while (segmentIndex < segments.size()){ //continue tant qu'il reste des segments restants
                List<T> current_segment = segments.get(segmentIndex);
                if (elementIndex < current_segment.size()){ //vérifie si il reste des éléments dans le segment itéré
                    return true;
                }
                segmentIndex++;
                elementIndex = 0; //remise de l'index au sein du segment à 0
            }
            return false;
        }

        @Override
        public T next() {

            if (modCount != expectedModCount) throw new ConcurrentModificationException();

            if (!hasNext()){
                throw new NoSuchElementException();
            }
            List<T> current_segment = segments.get(segmentIndex);
            T element = current_segment.get(elementIndex);
            elementIndex++;
            return element;

        }

    }

}
