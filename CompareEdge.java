import java.util.Comparator;

class CompareEdge implements Comparator<Edge>{
 
  @Override
  public int compare(Edge e1, Edge e2) {
    return e1.vertex()-e2.vertex();
  }
}
