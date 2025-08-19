# Résolutions des exercices

Réalisé par mes propres soins dans le cadre du cours **Algorithmique et Structure de Données (LINFO1121)** à l'UCLouvain.

## Références

Réponses du professeur : [Lien GitHub](https://github.com/pschaus/algorithms_exercises/tree/main/src/main/java)

---

## Partie 1 - Abstract Data Types, Complexity, Java Collections; Stacks, Queues and Linked Lists

### Objectifs

- Faire la distinction entre les notations `∼`, `O`, `Θ` et `Ω`, connaître leurs propriétés et définitions.
- Décrire précisément les propriétés des types abstraits :
  - **Pile (Stack)**
  - **File (Queue)**
- Décrire les différents types de listes chaînées.
- Distinguer entre un type de données abstrait et son implémentation.
- Implémenter et évaluer une implémentation d’une pile à l’aide :
  - D’une liste simplement chaînée.
  - D’une liste doublement chaînée.
- Utiliser des tests unitaires (avec **JUnit**) pour tester et prouver le bon fonctionnement d’un programme.
- Utiliser les différentes collections présentes dans le langage **Java**, à l’aide de la documentation.

### À Lire 

- **Chapitre 1, section 1 :** Quelques rappels sur Java et la programmation en général.
- **Chapitre 1, section 2 :** Abstraction des données.
- **Chapitre 1, section 3 :** Piles, files, sacs, listes chaînées.
- **Chapitre 1, section 4 :** Analyse des algorithmes.

---

## Partie 2 - Sorting Algorithms

### Objectifs

- Décrire de manière précise et rigoureuse les concepts présentés dans le chapitre du livre de référence traitant des algorithmes de tri.
- Implémenter et évaluer des algorithmes classiques de tri.
- Connaître leurs avantages, inconvénients et propriétés.

### À Lire

- **Chapitre 1, section 1 :** Quelques rappels sur Java et la programmation en général (partie sur la recherche binaire).
- **Chapitre 2, section 1 :** Tri élémentaire.
- **Chapitre 2, section 2 :** Tri fusion (Merge Sort).
- **Chapitre 2, section 3 :** Tri rapide (Quick Sort).
- **Chapitre 2, section 5 :** Applications des algorithmes de tri.
- **Chapitre 5, section 5.1 :** Tris de chaînes de caractères (String Sorts).

---

## Partie 3 – Arbres binaires de recherche et tables de symboles ordonnées

### Objectifs

- Décrire de manière précise et rigoureuse les concepts liés aux **arbres binaires de recherche** et aux **tables de symboles ordonnées**.
- Implémenter des algorithmes basés sur des **arbres de recherche**.
- Évaluer et implémenter les **représentations classiques** des arbres de recherche.

### À Lire

- **Chapitre 3, section 1 :** Arbres binaires de recherche (Binary Search Trees).
- **Chapitre 3, section 2 :** Suppression dans les arbres.
- **Chapitre 3, section 3 :** Équilibrage partiel et performances.
- **Chapitre 5, section 2 :** Représentations symboliques (Symbol Tables).

---

## Partie 4 – Tables de hachage, tries et algorithme de Rabin-Karp

### Objectifs

- Décrire de manière précise et rigoureuse les concepts liés aux **tables de hachage** et aux **tries**.
- Implémenter des algorithmes basés sur des **tables de hachage**.
- Implémenter des algorithmes basés sur des **tries**.
- Concevoir des **fonctions de hachage** appropriées pour différents types d’objets.
- Résoudre des problèmes simples de stockage et d’accès à l’information avec des tables de hachage.
- Évaluer et implémenter les représentations classiques des tables de hachage.
- Décrire et implémenter l’algorithme **Rabin-Karp** pour la recherche de texte dans un corpus.
- Décrire et implémenter des **bitsets** (non couverts dans le livre).

### À Lire

- **Chapitre 3.4 :** Tables de hachage.
- **Chapitre 3.5 :** Fonctions de hachage et implémentations.
- **Chapitre 5.2 :** Tries.
- **Chapitre 5.3 :** Algorithme de recherche par empreinte Rabin-Karp (partie sur fingerprint search uniquement).

---

## Partie 5 – Union-Find, files de priorité et compression de données

### Objectifs

- Décrire de manière précise et rigoureuse les concepts liés aux **structures Union-Find**, aux **files de priorité**, et à la **manipulation/compression de données textuelles**.
- Implémenter un algorithme de **compression de texte** basé sur le **codage de Huffman**.
- Évaluer et implémenter une représentation efficace d’une **file de priorité** basée sur un **tas (heap)**.
- Concevoir et implémenter un programme de **compression/décompression de texte**.

### À Lire

- **Chapitre 1.5 :** Union-Find.
- **Chapitre 2.4 :** Heap.
- **Chapitre 5.5 :** Compression de Huffman.

---

## Partie 6 – Graphes

### Objectifs

- Décrire avec précision et rigueur les concepts liés aux **graphes**.
- Évaluer et implémenter les **représentations classiques** des graphes.
- Choisir une **représentation appropriée** d’un graphe selon les opérations à effectuer.
- Implémenter des **algorithmes de parcours et de manipulation de graphes**, en particulier :
  - parcours en **profondeur** (Depth First Search) et en **largeur** (Breadth First Search),
  - calcul des **composantes connexes** (le calcul des composantes fortement connexes n’est pas couvert dans ce cours),
  - **détection de cycles**,
  - **tri topologique**,
  - calcul des **arbres couvrants de poids minimum** (**Kruskal et Prim**),
  - calcul des **plus courts chemins** (**Dijkstra, Bellman-Ford**).

### À Lire

- **Chapitres 4.1, 4.2, 4.3, 4.4** : Graphes