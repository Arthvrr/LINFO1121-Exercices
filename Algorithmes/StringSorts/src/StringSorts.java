public class StringSorts {
    public static void main(String[] args) {

        //KEY-INDEXED COUNTING
        Item[] items = {
                new Item(2, "B"),
                new Item(1, "A"),
                new Item(3, "D"),
                new Item(1, "C")
        };

        KeyIndexedCounting.sort(items, 4); // R = 4 (valeurs de clé : 0 à 3)

        for (Item item : items) {
            System.out.println(item);
        }

        //LSD STRING SORTING
        String[] stringsLSD = {
                "dab",
                "cab",
                "abc",
                "acb",
                "bac",
                "bca",
                "cba"
        };

        LSD.sort(stringsLSD, 3); // W = longueur des chaînes

        System.out.println("LSD sort:");
        for (String s : stringsLSD) {
            System.out.println(s);
        }

        //MSD STRING SORTING
        String[] stringsMSD = {
                "dab",
                "cab",
                "abc",
                "acb",
                "bac",
                "bca",
                "cba"
        };

        MSD.sort(stringsMSD);

        System.out.println("MSD sort:");
        for (String s : stringsMSD) {
            System.out.println(s);
        }

    }

    public static class Item {
        int key;
        String value;

        Item(int key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key + " - " + value;
        }
    }

    public class KeyIndexedCounting {

        public static void sort(Item[] a, int R) {
            int N = a.length;
            int[] count = new int[R + 1];
            Item[] output = new Item[N];

            // 1. Compter combien d'éléments ont chaque clé
            for (int i = 0; i < N; i++) {
                count[a[i].key + 1]++;
            }

            // 2. Calculer les positions de départ dans le tableau trié
            for (int r = 0; r < R; r++) {
                count[r + 1] += count[r];
            }

            // 3. Placer les éléments dans le tableau trié
            for (int i = 0; i < N; i++) {
                int position = count[a[i].key]++;
                output[position] = a[i];
            }

            // 4. Copier le tableau trié dans l'original
            for (int i = 0; i < N; i++) {
                a[i] = output[i];
            }
        }
    }

    public class LSD {
        public static void sort(String[] a, int W) {
            int N = a.length;
            int R = 256; // alphabet étendu ASCII
            String[] aux = new String[N];

            for (int d = W - 1; d >= 0; d--) {
                int[] count = new int[R + 1];

                // 1. Compter les fréquences
                for (int i = 0; i < N; i++) {
                    int c = a[i].charAt(d);
                    count[c + 1]++;
                }

                // 2. Cumuler les fréquences
                for (int r = 0; r < R; r++) {
                    count[r + 1] += count[r];
                }

                // 3. Placer dans aux[]
                for (int i = 0; i < N; i++) {
                    int c = a[i].charAt(d);
                    aux[count[c]++] = a[i];
                }

                // 4. Copier aux[] dans a[]
                for (int i = 0; i < N; i++) {
                    a[i] = aux[i];
                }
            }
        }
    }

    public static class MSD {
        private static final int R = 256; // Alphabet ASCII étendu
        private static final int CUTOFF = 15; // Pour insertion sort si sous-tableau petit

        public static void sort(String[] a) {
            String[] aux = new String[a.length];
            sort(a, 0, a.length - 1, 0, aux);
        }

        private static void sort(String[] a, int lo, int hi, int d, String[] aux) {
            if (hi <= lo + CUTOFF) {
                insertion(a, lo, hi, d);
                return;
            }

            int[] count = new int[R + 2]; // +2 pour gérer -1 (fin de chaîne)

            // 1. Compter les fréquences (avec +2 pour inclure -1)
            for (int i = lo; i <= hi; i++) {
                int c = charAt(a[i], d);
                count[c + 2]++;
            }

            // 2. Cumuler
            for (int r = 0; r < R + 1; r++) {
                count[r + 1] += count[r];
            }

            // 3. Placer dans aux[]
            for (int i = lo; i <= hi; i++) {
                int c = charAt(a[i], d);
                aux[count[c + 1]++] = a[i];
            }

            // 4. Copier aux[] vers a[]
            for (int i = lo; i <= hi; i++) {
                a[i] = aux[i - lo];
            }

            // 5. Appels récursifs
            for (int r = 0; r < R; r++) {
                sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
            }
        }

        // Retourne le caractère à la position d, ou -1 si trop court
        private static int charAt(String s, int d) {
            if (d < s.length()) return s.charAt(d);
            else return -1;
        }

        // Insertion sort pour petits sous-tableaux (utile dans MSD)
        private static void insertion(String[] a, int lo, int hi, int d) {
            for (int i = lo; i <= hi; i++) {
                for (int j = i; j > lo && less(a[j], a[j - 1], d); j--) {
                    String temp = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = temp;
                }
            }
        }

        private static boolean less(String v, String w, int d) {
            return v.substring(d).compareTo(w.substring(d)) < 0;
        }
    }
}