package graphs;

import java.util.ArrayList;

/**
 * Implement the Digraph.java interface in
 * the Digraph.java class using an adjacency list
 * data structure to represent directed graphs.
 */
// BEGIN : STUDENT
public class Digraph
{
    private int V;
    private int E;
    private ArrayList<ArrayList> adj;

    public Digraph(int V)
    {
        this.V = V;
        this.E = 0;
        this.adj = new ArrayList<ArrayList>(V);
        for (int i = 0; i < this.V; i++) this.adj.add(new ArrayList<>());
    }

    /**
     * The number of vertices
     */
    public int V() { return V; }

    /**
     * The number of edges
     */
    public int E() { return E; }

    /**
     * Add the edge v->w
     */
    public void addEdge(int v, int w)  { this.adj.get(v).add(w); this.E++; }

    /**
     * The nodes adjacent to node v
     * that is the nodes w such that there is an edge v->w
     */
    public Iterable<Integer> adj(int v)  { return (Iterable<Integer>) this.adj.get(v); }

    /**
     * A copy of the digraph with all edges reversed
     */
    public Digraph reverse()
    {
        Digraph newDigraph = new Digraph(this.V);
        for (int v = 0; v < this.V; v++)
        {
            for (int w : adj(v))
            {
                newDigraph.addEdge(w, v);
            }
        }
        return newDigraph;
    }
}
// END : STUDENT