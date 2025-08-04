import java.util.Arrays;
import java.util.Collections;

public class Sorting {

    public static void main(String[] args) {


        Comparable[] testNumber = {9,8,7,6,5,4,3,2,1};
        Comparable[] testString = {"E","X","A","M","P","L","E"};

        Comparable[] toTest = testNumber; //change here

        quickSort(toTest);
        System.out.println(Arrays.toString(toTest));
    }

    private static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j){
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    /*
    sélectionne à chaque itération le plus petit élément restant et le swap avec l'élément itéré
     */
    public static void selectionSort(Comparable[] a){
        int N = a.length;
        for (int i = 0; i < N; i++){
            int min = i;
            for (int j = i+1; j < N; j++){
                if (less(a[j],a[min])){
                    min = j;
                }
                exch(a,i,min);
            }
        }
    }

    /*
    va insérer au bon endroit (bon ordre) parmi les éléments déjà itéré l'élément itéré actuellement
     */
    public static void insertionSort(Comparable[] a){
        int N = a.length;
        for (int i = 0; i < N; i++){
            for (int j = i; j > 0 && less(a[j],a[j-1]);j--){
                exch(a,j,j-1);
            }
        }
    }

    public static void shellSort(Comparable[] a){
        int N = a.length;
        int h = 1;
        while (h < N/3) h = 3*h + 1;
        while (h >= 1){
            for (int i = h; i < N; i++){
                for (int j = i; j >= h && less(a[j],a[j-h]); j -= h){
                    exch(a,j,j-h);
                }
            }
            h /= 3;
        }
    }

    public static void mergeSort(Comparable[] a){
        Comparable[] aux = new Comparable[a.length];
        sort(a,0,a.length-1); //appel à la fonction auxiliaire sort
    }

    private static void merge(Comparable[] a, int lo, int mid, int hi){
        int i = lo; //pointeur de début partie gauche
        int j = mid+1; //pointeur de début partie droite

        Comparable[] aux = new Comparable[a.length]; //copie de a

        for (int k = lo; k <= hi; k++){
            aux[k] = a[k];
        }

        for (int k = lo; k <= hi; k++){
            if (i > mid) a[k] = aux[j++]; //tout les éléments de gauche ont été placés, on copie ceux de droite
            else if (j > hi) a[k] = aux[i++]; //tout les éléments de droite ont été placés, on copie ceux de gauche
            else if (less(aux[j],aux[i])) a[k] = aux[j++]; //élément de droite plus petit, on le place dans a[k]
            else a[k] = aux[i++]; //élément de gauche est plus petit ou égal, on le place
        }
    }

    private static void sort(Comparable[] a, int lo, int hi){
        if (hi <= lo) return;
        int mid = lo + (hi-lo) / 2;
        sort(a,lo,mid);
        sort(a,mid+1,hi);
        merge(a,lo,mid,hi);
    }

    public static void quickSort(Comparable[] a){
        Collections.shuffle(Arrays.asList(a));
        sorting(a,0,a.length-1);
    }

    private static void sorting(Comparable[] a,int lo, int hi){
        if (hi <= lo) return;
        int j = partition(a,lo,hi);
        sorting(a,lo,j-1);
        sorting(a,j+1,hi);
    }

    private static int partition(Comparable[] a, int lo, int hi){
        int i = lo;
        int j = hi+1;
        Comparable v = a[lo];
        while (true){
            while (less(a[++i], v)) if (i == hi) break;
            while (less(v, a[--j])) if (j == lo) break;
            if (i >= j) break;
            exch(a, i, j) ;
        }
        exch(a,lo,j);
        return j;
    }
}