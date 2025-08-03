public class BinarySearch {
    public static void main(String[] args) {
        int[] array = {1,2,3,4,5,6,7,8,9};
        int key = 7;
        int result = rank(key, array);
        if (result != -1) {
            System.out.println("La clé " + key + " a été trouvée à l'index " + result);
        } else {
            System.out.println("La clé " + key + " n'a pas été trouvée.");
        }
    }

    public static int rank(int key, int[] a){
        //Array must be sorted
        int lo = 0;
        int hi = a.length-1;

        while (lo <= hi){
            int mid = lo + (hi-lo) / 2;
            if (key < a[mid]) hi = mid-1;
            else if (key > a[mid]) lo = mid+1;
            else return mid;
        }
        return -1; //Not found
    }
}