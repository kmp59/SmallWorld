/** Linked list for graphs: Provides access to curr */
class GraphList extends LList<Edge> implements java.io.Serializable {
  public Link<Edge> currLink() { return curr; }
  public void setCurr(Link<Edge> who) { curr = who; }
}
