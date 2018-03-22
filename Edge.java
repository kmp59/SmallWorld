
/** Edge class for Adjacency List graph representation */
class Edge implements java.io.Serializable {
  private int vert, wt;

  public Edge(int v, int w) // Constructor
  {
    vert = v;
    wt = w;
  }

  public int vertex() {
    return vert;
  }

  public int weight() {
    return wt;
  }
  
  public void setWeight(int w){
    wt = w;
  }
}
