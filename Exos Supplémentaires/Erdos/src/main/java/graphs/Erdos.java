package graphs;

import java.util.*;

/**
 * The erdos number is a "collaborative distance" metric to Paul Erdos (a prolific mathematician)
 * based on co-authorship of mathematical articles.
 * It is computed as follows:
 * - Erdos has, by definition an erdos-number of 0.
 * - For each other author, we look at all his/her co-authors in each article.
 *   If n is the minimum erdos-number from all his co-authors, then this author has an erdos-number of n+1.
 *
 * For example:
 *
 * Given this set of co-authors relations:
 *
 * 		{ "Paul Erdös", "Edsger W. Dijkstra" }
 * 		{ "Edsger W. Dijkstra", "Alan M. Turing" }
 * 		{ "Edsger W. Dijkstra", "Donald Knuth" }
 * 		{ "Donald Knuth", "Stephen Cook", "Judea Pearl" }
 *
 * 	The erdos number of Paul Erdos is 0, of Edsger W. Dijkstra is 1, of Alan M. Turing is 2, of Donald Knuth is 2, of Stephen Cook is 3.
 *
 * 	Debug your code on the small examples in the test suite.
 */
public class Erdos {

	public static void main(String[] args) {
		ArrayList<String []> authors = new ArrayList<>();
		authors.add(new String [] { "Paul Erdös", "Edsger W. Dijkstra" });
		authors.add(new String [] { "Edsger W. Dijkstra", "Alan M. Turing" });
		authors.add(new String [] { "Edsger W. Dijkstra", "Donald Knuth" });
		authors.add(new String [] { "Donald Knuth", "Stephen Cook", "Judea Pearl" });

		Erdos erdos1 = new Erdos(authors);
	}

	public static final String erdos = "Paul Erdös";
	public HashMap<String,ArrayList<String>> map;
	public HashMap<String,Integer> result;
	public HashSet<String> visited;
	/**
	 * Constructs an Erdos object and computes the Erdős numbers for each author.
	 *
	 * The constructor should run in O(n*m^2) where n is the number of co-author relations,
	 * and m the maximum number of co-authors in one article.
	 *
	 * @param articlesAuthors An ArrayList of String arrays, where each array represents the list of authors of a single article.
	 */
	public Erdos(ArrayList<String []> articlesAuthors) {
		// TODO
		/*
		Problème du plus court chemin dans un graphe non-pondéré --> BFS
		 */

		result = new HashMap<>();
		result.put(erdos,0);
		visited = new HashSet<>();

		map = new HashMap<>(); //on crée le graphe : un HashMap contenant le nom de chaque auteur avec en valeur une liste de ses co-auteurs
		for (String[] article : articlesAuthors){
			for (int i = 0; i < article.length; i++){
				for (int j = i+1; j < article.length; j++){
					String author1 = article[i];
					String author2 = article[j];

					map.putIfAbsent(author1,new ArrayList<>());
					map.get(author1).add(author2);
					map.putIfAbsent(author2,new ArrayList<>());
					map.get(author2).add(author1);

				}
			}
		}

		Queue<Person> queue = new LinkedList<>();
		queue.add(new Person(erdos,0));
		visited.add(erdos);

		while (!queue.isEmpty()){
			Person current = queue.poll();

			ArrayList<String> list = map.get(current.name);

			for (int i = 0; i < list.size(); i++){ //on ajoute à la Queue tout les voisins du noeud courant, sauf si on les a déjà visités
				if (visited.contains(list.get(i))) continue;
				queue.add(new Person(list.get(i), current.distance+1));
				visited.add(list.get(i));
				result.put(list.get(i), current.distance+1); //en BFS, le premier chemin visité est toujours le plus court, donc on save direct
			}
		}
	}

	/**
	 * Returns the Erdős number of a given author.
	 * This method is expected to run in O(1).
	 * @param author The name of the author whose Erdős number is to be found.
	 * @return The Erdős number of the specified author. If the author is not in the network, returns -1.
	 */
	public int findErdosNumber(String author) {
		// TODO
		if (!result.containsKey(author)) return -1;
		return result.get(author);
	}

	private class Person{
		String name;
		int distance;

		public Person(String name, int distance){
			this.name = name;
			this.distance = distance;
		}
	}
}