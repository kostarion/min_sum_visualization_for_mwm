import org.graphstream.graph.Graph;

/**
 * Implementation of the min-sum algorithm for maximum weight matching.
 */
public class MinSum {
    // For storing messages from 'a' nodes to 'b' nodes.
    private double[][][] m_ab;
    // For storing messages from 'a' nodes to 'a' nodes.
    private double[][][] m_ba;
    // For keeping messages from 'a' nodes to 'b' nodes on the previous step.
    private double[][][] old_m_ab;
    // For keeping messages from 'b' nodes to 'a' nodes on the previous step.
    private double[][][] old_m_ba;
    // Weight matrix.
    private double[][] W;
    // Believe values for each 'a' node;
    private double[][] b_a;
    // Believe values for each 'b' node;
    private double[][] b_b;
    // Current matching.
    private int[] matching;
    private int n;

    public MinSum(Graph g) {
        n = g.getNodeCount() / 2;
        W = new double[n][n];
        // Convert weights on edges to a weight matrix.
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                // To each node we add small random number in order to provide uniqueness of the solution.
                W[i][j] = (int)g.getEdge("a" + (i + 1) + "b" + (j + 1)).getAttribute("weight")
                        + Math.random()*0.01;
            }
        }
        m_ab = new double[n][n][n];
        m_ba = new double[n][n][n];
        // Initialize messages with weights on the edges.
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                m_ab[i][j][i] = W[i][j];
                m_ba[i][j][i] = W[j][i];
            }
        }
        b_a = new double[n][n];
        b_b = new double[n][n];
        matching = new int[n];
    }

    /**
     * Runs one iteration of the algorithm.* @param steps order number of the step. Needed for proper logging.
     * @return true if algorithm has converged.
     */
    public boolean step() {
        old_m_ab = m_ab.clone();
        old_m_ba = m_ba.clone();
        double[][] sum_bap = new double[n][n];
        for (int l = 0; l < n; ++l) {
            for (int i = 0; i < n; ++i) {
                for (int p = 0; p < n; ++p) {
                    sum_bap[i][p] += m_ba[l][i][p];
                }
            }
        }
        double[][] sum_abq = new double[n][n];
        for (int l = 0; l < n; ++l) {
            for (int j = 0; j < n; ++j) {
                for (int q = 0; q < n; ++q) {
                    sum_abq[j][q] += m_ab[l][j][q];
                }
            }
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                for (int q = 0; q < n; ++q) {
                    double max_val = 0;
                    for (int p = 0; p < n; ++p) {
                        if (p == j && i != q || i == q && p != j) {
                            continue;
                        }
                        max_val = Math.max(max_val, sum_bap[i][p] - old_m_ba[j][i][p] + W[i][p]);
                    }
                    m_ab[i][j][q] = max_val;
                }
                for (int p = 0; p < n; ++p) {
                    double max_val = 0;
                    for (int q = 0; q < n; ++q) {
                        if (p == j && i != q || i == q && p != j) {
                            continue;
                        }
                        max_val = Math.max(max_val, sum_abq[j][q] - old_m_ab[i][j][q] + W[q][j]);
                    }
                    m_ba[j][i][p] = max_val;
                }
            }
        }
        // Step 4: Calculate overall believes for each node.
        for (int i = 0; i < n; ++i) {
            for (int r = 0; r < n; ++r) {
                b_a[i][r] = W[i][r];
                for (int l = 0; l < n; ++l) {
                    b_a[i][r] += m_ba[l][i][r];
                }
            }
        }
        for (int j = 0; j < n; ++j) {
            for (int r = 0; r < n; ++r) {
                b_b[j][r] = W[r][j];
                for (int l = 0; l < n; ++l) {
                    b_b[j][r] += m_ab[l][j][r];
                }
            }
        }
        // Step 5: Define current matching for the step.
        boolean converged = true;
        for (int i = 0; i < n; ++i) {
            double max_belief = 0.;
            int max_belief_ind = 0;
            for (int j = 0; j < n; ++j) {
                if (b_a[i][j] > max_belief) {
                    max_belief = b_a[i][j];
                    max_belief_ind = j;
                }
            }
            if (matching[i] != max_belief_ind) converged = false;
            matching[i] = max_belief_ind;
        }

        return converged;
    }

    public int[] get_matching() {
        return matching;
    }
}
