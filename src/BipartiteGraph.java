import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

/**
 * Class representing weighted bipartite graph.
 */
public class BipartiteGraph {
    private Graph g;
    private int n;

    /**
     * Constructs a bipartite weighted graph. Weight on the edges of the graph are
     * initialized randomly by random numbers from 0 to {@param maxWeight}.
     * @param n number of nodes in each part of the graph, so 2n vertices overall.
     * @param maxWeight maximum possible weight on the edge of the graph.
     */
    public BipartiteGraph(int n, int maxWeight) {
        System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        g = new SingleGraph("bipartite");
        g.addAttribute("ui.stylesheet", "edge {text-alignment: along;}");
        g.addAttribute("ui.stylesheet", "node {text-alignment: above;}");
        this.n = n;
        // Initialize nodes.
        for (int i = 1; i <= n; ++i) {
            Node a = g.addNode("a" + i);
            a.addAttribute("xy", 0, 1.5*n - 1.5*i);
            Node b = g.addNode("b" + i);
            b.addAttribute("xy", 10, 1.25*n - i);
        }
        // Initialize edges.
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <=n; ++j) {
                Edge e = g.addEdge("a"+i+"b"+j, "a"+i, "b"+j);
                e.addAttribute("weight", (int)(Math.random()*maxWeight));
            }
        }
        for (Node node : g)
            node.setAttribute("ui.label", node.getId());
        for (Edge e : g.getEachEdge())
            e.setAttribute("ui.label", "" + (int) e.getNumber("weight"));
    }

    /**
     * Visualizes matching by highlighting edges.
     * @param matching the input matching.
     */
    public void highlight_matching(int[] matching) {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                g.getEdge("a"+(i+1)+"b"+(j+1)).addAttribute("ui.style", "fill-color: grey;");
                g.getEdge("a"+(i+1)+"b"+(j+1)).addAttribute("ui.style", "text-color: black;");
            }
        }
        for (int i = 0; i < n; ++i) {
            g.getEdge("a"+(i+1)+"b"+(matching[i]+1))
                    .addAttribute("ui.style", "fill-color: red;");
            g.getEdge("a"+(i+1)+"b"+(matching[i]+1))
                    .addAttribute("ui.style", "text-color: red;");
        }
    }

    /**
     * Disables automatic layout from proper graph drawing.
     * @return viewer object.
     */
    public Viewer display() {
        Viewer viewer = g.display();
        viewer.disableAutoLayout();
        return viewer;
    }

    public Graph getGraph() {
        return g;
    }
}
