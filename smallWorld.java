import java.util.*;
import java.util.Dictionary;

public class smallWorld {
	static long start1 = System.currentTimeMillis();

	public static void main(String[] args) throws Exception {
		ArrayList<String> act = new ArrayList<>();
		ArrayList<ArrayList<Integer>> edges = new ArrayList<>();
		BST mov_list = new BST();

		String fname = "shortestActors.list.gz";

		RetrieveActors ra = new RetrieveActors(fname); // starts with actors list file

		boolean activator = false;

		String[] tkn;

		long start = System.currentTimeMillis();

		String content;
		//ra = new RetrieveActors("actresses.list.gz");
		for (int file = 0; file < 1;) {
			int index = 0;
			while ((content = ra.getNext()) != null) {

				tkn = content.split("@@@");
				act.add(tkn[0]);

				edges.add(new ArrayList<>());

				for (int i = 1; i < tkn.length; i++) {

					if (tkn[i].substring(0, 2).equals("FM")) { // only keep films

						String mov_name = tkn[i].substring(2);

						if (mov_list.find(mov_name) == null) {
							MovieRecord movie = new MovieRecord(mov_name);
							movie.addActor(index);
							mov_list.insert(mov_name, movie);
						} else {
							MovieRecord movie = (MovieRecord) mov_list.remove(mov_name);
							movie.addActor(index);
							mov_list.insert(mov_name, movie);
						}
					}
				}

				
				index++;
				
				  //if(index == 10000) { break; }
				 
			}
			file++;
			System.out.println("switching files...");
		}
		ra.close();

		long load_file = System.currentTimeMillis();

		System.out.println("Time to read file: " + (load_file - start) + " msecs");

		Graphs graph = new Graphs(act.size());

		while (mov_list.size() != 0) { // retrieve all movies from movie list
			MovieRecord m = (MovieRecord) mov_list.removeAny();

			for (int i = 0; i < m.actors.size(); i++) { // traverse the all actors that worked in movie (m)

				for (int j = i + 1; j < m.actors.size(); j++) // creates adjacency list
					edges.get(m.actors.get(i)).add(m.actors.get(j));
			}
		}

		long adj_list = System.currentTimeMillis();

		System.out.println("Time to create adjacency list " + (adj_list - load_file) + " msecs");

		for (int k = 0; k < edges.size(); ++k) // sorts in ascending order
			Collections.sort(edges.get(k));

		for (int i = 0; i < edges.size(); ++i) {
			int prev = -1;

			for (int j : edges.get(i)) {

				if (j == prev)
					continue; // skip duplicates

				graph.setEdge(i, j, 1);
				graph.setEdge(j, i, 1);
				prev = j;
			}
		}

		long create_graph = System.currentTimeMillis();

		System.out.println("Time to create graph " + (create_graph - adj_list) + " msecs\n");

		/** TEST CASE FOR SINGLE VERTEX - Uncomment code block below **/
		int f =0;
		System.out.println("Distance from " + act.get(f) + ":");
		lvlBFS(f, act.size(), graph);

		// System.out.println(distanceBFS(0, 5, graph));

		/** TEST CASE FOR ALL VERTICES - Uncomment code block below **/

		/*
		 * for(int i = 0; i < graph.n(); i++){ System.out.println("Distance from " +
		 * act.get(i)); graph.lvlBFS(i); System.out.println(); }
		 */
	}

	static int distanceBFS(int start, int end, Graphs graph) {

		LinkedList<Integer> queue = new LinkedList<>();
		boolean visited[] = new boolean[graph.n()];
		int[] level = new int[graph.n()];

		graph.setMark(start, 0);
		queue.add(start);
		visited[start] = true;

		while (queue.size() != 0) {
			int current = queue.poll();

			if (current == end)
				return graph.getMark(current);

			for (int i = graph.first(current); i != graph.n(); i = graph.next(current, i)) {
				if (!visited[i]) {
					visited[i] = true;
					graph.setMark(i, graph.getMark(current) + 1);
					queue.add(i);
					int lvl = graph.getMark(i);
					level[lvl]++;
				}
			}
		}

		System.out.println("These two are not connected.");
		return -1;
	}

	static void lvlBFS(int s, int n, Graphs graph) {
		// Create a queue for BFS
		LinkedList<Integer> queue = new LinkedList<>();
		boolean visited[] = new boolean[graph.n()];
		int[] level = new int[graph.n()];
		int total = 1;

		graph.setMark(s, 0); // mark its current distance as 0
		queue.add(s); // enqueue current node
		visited[s] = true; // marks current node as visited

		while (queue.size() != 0) {
			s = queue.poll(); // Dequeue a vertex from queue
			// System.out.println(s);

			// Get all adjacent vertices of the dequeue vertex s
			// If a adjacent has not been visited, then mark it visited and enqueue it
			for (int i : graph.neighbors(s)) {

				if (!visited[i]) {
					visited[i] = true;
					graph.setMark(i, graph.getMark(s) + 1); // sets child's lvl one level above its parent
					queue.add(i);
					level[graph.getMark(i)]++;

				}
			}
		}
		for (int i = 1; i < graph.n(); i++) {
			if (level[i] != 0) {
				System.out.println("Level " + i + " =====> " + level[i]);
				total = total + level[i];
			}
		}
		System.out.println("# not connected =====>" + (n - total));
		System.out.println(n);
		long end = System.currentTimeMillis();
		System.out.println("Time to finish is:  " + (end - start1));
	}
}
