package graphs;

import java.util.*;

/**
     * The class TaskScheduler allows
     * to declare a set of tasks with their dependencies.
     * You have to implement the method:
     *      boolean isValid(List<String> schedule)
     * allowing to verify if the given schedule does
     * not violate any dependency constraint.
     *
     * Example:
     *
     *         TaskScheduler scheduler = new TaskScheduler();
     *         scheduler.addTask("A", Arrays.asList());
     *         scheduler.addTask("B", Arrays.asList("A"));
     *         scheduler.addTask("C", Arrays.asList("A"));
     *         scheduler.addTask("D", Arrays.asList("B", "C"));
     *
     *    The dependency graph is represented as follows:
     *
     *          │─────► B │
     *        A─│         ├─────►D
     *          │─────► C │
     *
     *
     *         assertTrue(scheduler.isValid(Arrays.asList("A", "B", "C", "D")));
     *         assertFalse(scheduler.isValid(Arrays.asList("A", "D", "C", "B"))); // D cannot be scheduled before B
     *
     *  Feel free to use existing java classes.
     */
    public class TaskScheduler {

        public static void main(String[] args) {
            TaskScheduler scheduler = new TaskScheduler();
            scheduler.addTask("A", Arrays.asList());
            scheduler.addTask("B", Arrays.asList("A"));
            scheduler.addTask("C", Arrays.asList("A"));
            scheduler.addTask("D", Arrays.asList("B", "C"));
            List<String> input = Arrays.asList("A", "B", "C", "D");
            scheduler.isValid(input);
        }

        private Map<String, List<String>> graph;


        public TaskScheduler() { //constructeur
            this.graph = new HashMap<>();
        }

        /**
         * Adds a task with the given dependencies to the scheduler.
         * The task cannot be scheduled until all of its dependencies have been completed.
         */
        public void addTask(String task, List<String> dependencies) {
            this.graph.put(task, dependencies);
        }

        /**
         * Verify if the given schedule is valid, that is it does not violate the dependencies
         * and every task in the graph occurs exactly once in it.
         * The time complexity of the method should be in O(V+E) where
         * V = number of tasks, and E = number of requirements.
         * @param schedule a list of tasks to be scheduled in the order they will be executed.
         */
        public boolean isValid(List<String> schedule) {
            //TODO

            HashSet<String> hashSet = new HashSet<>(); //on vérifie qu'on aie pas de doublons dans l'input schedule
            for (int k = 0; k < schedule.size(); k++){
                if (hashSet.contains(schedule.get(k))) return false;
                hashSet.add(schedule.get(k));
            }

            if (schedule.size() <= 1) return true; //si schedule de taille 0 ou 1, retourne true

            if (schedule.size() != graph.size()) return false; //si pas la même taille --> il manque ou il y a un noeud en trop

            HashMap<String,Integer> position = new HashMap<>(); //HashMap qui permet de stocker les index de chaque clé au sein de schedule
            for (int i = 0; i < graph.size(); i++){
                position.put(schedule.get(i),i);
            }

            //on compare chaque clé de graph avec chaque élément de la liste de valeur pour comparer leur index dans position
            for (int j = 0; j < graph.size(); j++){
                String key = schedule.get(j);
                List<String> current = graph.get(key);

                for (int k = 0; k < current.size(); k++){ //on parcoure la liste en valeurs
                    String currentStr = current.get(k);

                    //si l'élément itéré dans la liste (son "noeud parent") est à un index + élevé que la clé, erreur
                    if (position.get(currentStr) > position.get(key)) return false;
                }
            }

            System.out.println(schedule);
            System.out.println(graph);
            System.out.println(position);

            return true;
        }
    }