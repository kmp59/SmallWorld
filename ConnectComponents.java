
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Random;

public class ConnectComponents {

	public static void main(String[] args) {
		int n = 10000; // number of vertices of random graph
		Random ran = new Random();
		ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < n; ++i)
			edges.add(new ArrayList<Integer>());

		for (int e = 0; e < Math.pow(n, 1.1); ++e) { // add random edge
			int i = ran.nextInt(n);
			int j;
			do {
				j = ran.nextInt(n);
			} while (j == i);
			edges.get(i).add(j);
		}

		for (int k = 0; k < n; ++k)
			Collections.sort(edges.get(k));

		Graphs gr = new Graphs(n);
		for (int i = 0; i < n; ++i) {
			int prev = -1;
			for (int j : edges.get(i)) {
				if (j == prev)
					continue; // skip duplicates
				gr.setEdge(i, j, 1);
				gr.setEdge(j, i, 1);
				prev = j;
			}
		}

		System.out.println("Graph has " + gr.n() + " vertices.");
		System.out.println("Graph has " + gr.e() + " edges.");

		

		graphTraverse(gr);

	}

	/** Breadth first (queue-based) search */
	static int BFS(Graphs G, int start) {
		LinkedList<Integer> Q = new LinkedList<Integer>();
		int size = 1;
		Q.addLast(start);
		G.setMark(start, 1);
		while (Q.size() > 0) { // Process each vertex on Q
			int v = Q.removeFirst();
			// PreVisit(G, v); // Take appropriate action

			// for (int w = G.first(v); w < G.n(); w = G.next(v, w)){
			for (int e : G.neighbors(v)) {
				if (G.getMark(e) == 0) { // Put neighbors on Q
					G.setMark(e, G.getMark(v) + 1);
					Q.addLast(e);
					++size;
				}
			}
			// PostVisit(G, v); // Take appropriate action
		}
		return size;
	}

	static void PreVisit(Graph G, int v) {
		System.out.println("encountered vertex " + v);
	}

	static void PostVisit(Graph G, int v) {
		System.out.println("leaving vertex " + v);
	}

	static void graphTraverse(Graphs G) {
		int connectedComponents = 0;
		int v;
		for (v = 0; v < G.n(); v++)
			G.setMark(v, 0); // Initialize; 0 means "unvisited"
		for (v = 0; v < G.n(); v++)
			if (G.getMark(v) == 0) {
				int size = BFS(G, v);
				System.out.println("cs " + size);
				++connectedComponents;
			}
		System.out.println("Number of connected components= " + connectedComponents);
		for (v = 0; v < G.n(); v++)
			G.setMark(v, 0); // Initialize; 0 means "unvisited"

	}

	/** Depth first search */
	static void DFS(Graphs G, int v) {
		// PreVisit(G, v); // Take appropriate action
		G.setMark(v, 1);
		// for (int w = G.first(v); w < G.n(); w = G.next(v, w))
		for (int w : G.neighbors(v))
			if (G.getMark(w) == 0)
				DFS(G, w);
		// PostVisit(G, v); // Take appropriate action
	}

}
