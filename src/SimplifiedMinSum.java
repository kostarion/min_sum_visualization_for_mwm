import org.graphstream.graph.Graph;

public class SimplifiedMinSum {
    private double[][] m_ab;
    private double[][] m_ba;
    private double[][] old_m_ab;
    private double[][] old_m_ba;
    private double[][] W;
    private int[] matching;
    private int n;
    private boolean converged_last_time;

    public SimplifiedMinSum(Graph g) {
        converged_last_time = false;
        n = g.getNodeCount() / 2;
        W = new double[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                W[i][j] = (int)g.getEdge("a" + (i + 1) + "b" + (j + 1)).getAttribute("weight")
                        + Math.random()*0.01;
            }
        }
        m_ab = new double[n][n];
        m_ba = new double[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                m_ab[i][j] = W[i][j];
                m_ba[j][i] = W[i][j];
            }
        }
        matching = new int[n];
    }

    public boolean step(int steps) {
        old_m_ab = m_ab.clone();
        old_m_ba = m_ba.clone();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                double max_ba = 0;
                for (int l = 0; l < n; ++l) {
                    if (l != j) {
                        max_ba = Math.max(max_ba, old_m_ba[l][i]);
                    }
                }
                m_ab[i][j] = W[i][j] - max_ba;
            }
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                double max_ab = 0;
                for (int l = 0; l < n; ++l) {
                    if (l != i) {
                        max_ab = Math.max(max_ab, old_m_ab[l][j]);
                    }
                }
                m_ba[j][i] = W[i][j] - max_ab;
            }
        }

        // Step 5;
        boolean converged = true;
        for (int i = 0; i < n; ++i) {
            double max_belief = 0.;
            int max_belief_ind = 0;
            for (int j = 0; j < n; ++j) {
                if (m_ba[j][i] > max_belief) {
                    max_belief = m_ba[j][i];
                    max_belief_ind = j;
                }
            }
            if (matching[i] != max_belief_ind) converged = false;
            matching[i] = max_belief_ind;
        }


        boolean terminate = converged & converged_last_time;
        converged_last_time = converged;

        if (terminate) {
            return terminate;
        }

        System.out.println("\nIteration #" + (steps+1) + ":");
        System.out.print("\tMatching: ");
        int weight = 0;
        for (int i = 0; i < n; ++i) {
            weight += Math.round(W[i][matching[i]]);
            System.out.print("" + (i+1) + "->" + (matching[i]+1) + " \t");
        }
        System.out.println("\n\tWeight of the matching: " + weight + ".");
        return terminate;
    }

    public int[] get_matching() {
        return matching;
    }
}
