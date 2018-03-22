import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Stack;

public class TimeToGraduate {

  static ArrayList<String> courses;
  static Stack<String> sequence;

  public static void main(String[] args) {

    Graphm graph = new Graphm(17);
    courses = new ArrayList<String>();
    sequence = new Stack<String>();

    courses.add("CS100"); // 0
    courses.add("CS113"); // 1
    courses.add("CS114"); // 2
    courses.add("CS252"); // 3
    courses.add("CS280"); // 4
    courses.add("CS241"); // 5
    courses.add("CS332"); // 6
    courses.add("CS341"); // 7
    courses.add("CS288"); // 8
    courses.add("CS431"); // 9
    courses.add("CS435"); // 10
    courses.add("CS490"); // 11
    courses.add("CS491"); // 12
    courses.add("CS107"); // 13
    courses.add("CS207"); // 14
    courses.add("CS407"); // 15
    courses.add("IS350"); // 16

    // graph.Init(17);
    graph.setEdge(0, 1, 1);
    graph.setEdge(1, 2, 1);
    graph.setEdge(1, 3, 1);
    graph.setEdge(1, 16, 1);
    graph.setEdge(2, 4, 1);
    graph.setEdge(2, 6, 1);
    graph.setEdge(2, 8, 1);
    graph.setEdge(2, 9, 1);
    graph.setEdge(2, 5, 1);
    graph.setEdge(4, 7, 1);
    graph.setEdge(5, 10, 1);
    graph.setEdge(5, 7, 1);
    graph.setEdge(8, 10, 1);
    graph.setEdge(11, 12, 1);
    graph.setEdge(13, 14, 1);
    graph.setEdge(14, 15, 1);
    graph.setEdge(13, 15, 1);

    /*
    graphTraverse(graph);
    for (int i = 0; i < graph.n(); ++i)
      System.out.println(courses.get(i) + ": dist: " + graph.getMark(i));
      */

    topSort(graph);
    while (!sequence.isEmpty())
      System.out.println(sequence.pop());
  }

  /*
   * Perform topological sort of directed acyclic graph.
   */
  static void topSort(Graph G) {
    for (int v = 0; v < G.n(); v++)
      G.setMark(v, 0); // Initialize
    for (int v = 0; v < G.n(); v++)
      if (G.getMark(v) == 0)
        DFS(G, v); // And have Post-process push v onto stack
  }

  static void graphTraverse(Graph G) {
    int v;
    for (v = 0; v < G.n(); v++)
      G.setMark(v, 0); // Initialize; 0 means "unvisited"
    for (v = 0; v < G.n(); v++)
      if (G.getMark(v) == 0)
        BFS(G, v);
  }

  /** Depth first search */
  static void DFS(Graph G, int v) {
    PreVisit(G, v); // Take appropriate action
    G.setMark(v, 1);
    for (int w = G.first(v); w < G.n(); w = G.next(v, w))
      if (G.getMark(w) == 0)
        DFS(G, w);
    PostVisit(G, v); // Take appropriate action
  }

  /** Breadth first (queue-based) search */
  static void BFS(Graph G, int start) {
    LinkedList<Integer> Q = new LinkedList<Integer>();
    Q.addLast(start);
    G.setMark(start, 1);
    int d = 0;
    while (Q.size() > 0) { // Process each vertex on Q
      ++d;
      System.out.println("Distance = " + d);
      int v = Q.removeFirst();
      PreVisit(G, v); // Take appropriate action
      for (int w = G.first(v); w < G.n(); w = G.next(v, w))
        if (G.getMark(w) == 0) { // Put neighbors on Q
          G.setMark(w, G.getMark(v) + 1);
          Q.addLast(w);
        }
      PostVisit(G, v); // Take appropriate action
    }
  }

  static void PreVisit(Graph G, int v) {
    System.out.println("encountered vertex " + courses.get(v));
  }

  static void PostVisit(Graph G, int v) {
    System.out.println("leaving vertex " + courses.get(v));
    sequence.push(courses.get(v));
  }
}