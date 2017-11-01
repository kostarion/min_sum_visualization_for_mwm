import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

/**
 * Created by dtochilkin on 29.10.17.
 */
public class BipartiteGraph {
    private Graph g;
    private int n;

    public BipartiteGraph(int n, int maxWeight) {
        System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        g = new SingleGraph("bipartite");
        g.addAttribute("ui.stylesheet", "edge {text-alignment: along;}");
        g.addAttribute("ui.stylesheet", "node {text-alignment: above;}");
        this.n = n;
        for (int i = 1; i <= n; ++i) {
            Node a = g.addNode("a" + i);
            a.addAttribute("xy", 0, 1.5*n - 1.5*i);
//            a.addAttribute("ui.style", "text-alignment: above;");
            Node b = g.addNode("b" + i);
            b.addAttribute("xy", 10, 1.25*n - i);
//            b.addAttribute("ui.style", "text-alignment: above;");
        }
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <=n; ++j) {
                Edge e = g.addEdge("a"+i+"b"+j, "a"+i, "b"+j);
                e.addAttribute("weight", (int)(Math.random()*maxWeight));
//                e.addAttribute("ui.style", "text-alignment: along;");
            }
        }
        for (Node node : g)
            node.setAttribute("ui.label", node.getId());
        for (Edge e : g.getEachEdge())
            e.setAttribute("ui.label", "" + (int) e.getNumber("weight"));
    }

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

    public Viewer display() {
        Viewer viewer = g.display();
        viewer.disableAutoLayout();
        return viewer;
    }

    public Graph getGraph() {
        return g;
    }
}
