public class AppRunner {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("To run visualization of the Min-Sum algorithm final matching,\n" +
                               "please specify as command line arguments parameters of the graph:\n" +
                               "n - number of nodes in both parts of the graph (so, 2n nodes at all),\n" +
                               "W - maximum possible weight of edge of the graph, so weights will" +
                               "be initialized with random integers from 0 to W, and " +
                               "D - delay in ms between the iteration steps.");
            return;
        }

        int n = Integer.parseInt(args[0]);
        int max_weight = Integer.parseInt(args[1]);
        int delay = Integer.parseInt(args[2]);

        BipartiteGraph bg = new BipartiteGraph(n, max_weight);
//        MinSum minSum = new MinSum(bg.getGraph());
        SimplifiedMinSum minSum = new SimplifiedMinSum(bg.getGraph());
        bg.display();
        boolean converged = false;
        int steps = 0;
        while (!converged) {
//        while (steps < 15) {
            converged = minSum.step(steps);
            bg.highlight_matching(minSum.get_matching());
            ++steps;
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int matching_sum = 0;
        for (int i = 0; i < n; ++i) {
            matching_sum += (int)bg.getGraph().getEdge("a" + (i + 1) + "b" + (minSum.get_matching()[i] + 1))
                    .getAttribute("weight");
        }
        System.out.println("\n*****\n\nMin-sum algorithm have finished after " + (steps-1)
                +" iterations.");
        System.out.println("Weight of the final matching: " + matching_sum + ".");
        //bg.highlight_matching(minSum.get_matching());
//        System.out.print(steps);
        //bg.display();
    }
}
