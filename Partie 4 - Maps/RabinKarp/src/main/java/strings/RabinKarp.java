package strings;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * Author Pierre Schaus
 *
 * We are interested in the Rabin-Karp algorithm.
 * We would like to modify it a bit to determine
 * if a word among a list (!!! all words are of the same length !!!)
 * is present in the text.
 * To do this, you need to modify the Rabin-Karp
 * algorithm which is shown below (page 777 of the book).
 * More precisely, you are asked to modify this class
 * so that it has a constructor of the form:
 * public RabinKarp(String[] pat)
 *
 * Moreover the search function must return
 * the index of the beginning of the first
 * word (among the pat array) found in the text or
 * the size of the text if no word appears in the text.
 *
 * Example: If txt = "Here find interesting
 * exercise for Rabin Karp" and pat={"have", "find", "Karp"}
 * the search function must return 5 because
 * the word "find" present in the text and in the list starts at index 5.
 *
 */
public class RabinKarp {

    private HashMap<Long, String> hashTable = new HashMap<>();

    private int M;
    private long Q;
    private int R = 2048; //taille de l'alphabet
    private long RM; // R^(M-1) % Q

    public RabinKarp(String[] pat) {

        this.M = pat[0].length(); //longueur des mots de pat (tous identique, on prends le premier)
        Q = 4463; //grand nombre premier
        RM = 1; //facteur utilisé pour supprimer un caractère de la fenêtre glissante

        for (int i = 1; i <= M - 1; i++) // Compute R^(M-1) % Q for use
            RM = (R * RM) % Q; // in removing leading digit.

        //TODO
        //hash chaque mot de la liste pat et ajout dans hashTable
        for (int i = 0; i < pat.length; i++) {
            long patHash = hash(pat[i], M);
            this.hashTable.put(patHash, pat[i]);
        }
    }

    //permet de vérifier si un mot de pat se trouve à une position donnée dans txt
    public boolean check(int i, String pattern, String txt){
        //TODO
        return (pattern.equals(txt.substring(i, M + i))); //substring permet d’extraire toutes les sous-chaînes de longueur M dans txt
    }

    private long hash(String key, int M) { //fonction de hashage
        long h = 0;
        for (int j = 0; j < M; j++)
            h = (R * h + key.charAt(j)) % Q;
        return h;
    }

    public int search(String txt) { //cherche pour un match

        int N = txt.length(); //on initialise N à la longueur du texte
        long txtHash = hash(txt, M); //calcul du hash de la première chaîne de caractère

        //vérifie si le mot correspondant au hash des premiers M caractères du texte est trouvé au début du texte
        if (hashTable.containsKey(txtHash) && (check(0, hashTable.get(txtHash), txt))){
            return 0; //match au tout début
        }

        for (int i = M; i < N; i++) { //on parcoure tout le txt --> commence à M car déjà vérifié au-dessus

            //mettre à jour le hash pour la fenêtre glissante du texte de longueur M.
            txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
            txtHash = (txtHash * R + txt.charAt(i)) % Q;

            //vérifie si le hash de la sous_chaîne actuelle est dans la hashtable (même valeur de hash pour les 2)
            if (hashTable.containsKey(txtHash)) {

                //vérifie les deux chaînes de caractères
                if (check(i - M + 1, hashTable.get(txtHash), txt)) {
                    return i - M + 1; // match
                }
            }
        }
        return N; //pas de match trouvé, on retourne la longueur du texte
    }
}