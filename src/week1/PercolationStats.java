package src.week1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


/**
 * Perform Monte Carlo Simulation (or Percolation experiemnts) and capture the statistics.
 * @author Manas
 */
public class PercolationStats {

    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;


    public PercolationStats(int N, int T) { // perform T independent experiments on an N-by-N grid
        if (N <= 0) {
            throw new IllegalArgumentException("The grid size must be bigger than zero");
        }
        if (T <= 0) {
            throw new IllegalArgumentException("The number of experiments must be bigger than zero");
        }

        double[] percolationThresholds = new double[T]; // store the value of thresholds for each experiment.

        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(N);

            while (!percolation.percolates()) {
                int column;
                int row;

                do {
                    column = 1 + StdRandom.uniform(N);
                    row = 1 + StdRandom.uniform(N);
                } while (percolation.isOpen(row, column)); // if the column and row generated are open, keep generating new col and row.

                percolation.open(row, column);
            }

            percolationThresholds[i] = percolation.numberOfOpenSites() / (double) (N * N);
        }

        mean = StdStats.mean(percolationThresholds);
        stddev = StdStats.stddev(percolationThresholds);
        double confidenceFraction = (1.96 * stddev()) / Math.sqrt(T);
        confidenceLo = mean - confidenceFraction;
        confidenceHi = mean + confidenceFraction;
    }

    public double mean() {  // sample mean of percolation threshold
        return mean;
    }

    public double stddev() {    // sample standard deviation of percolation threshold
        return stddev;
    }

    public double confidenceLo() {  // low  endpoint of 95% confidence interval
        return confidenceLo;
    }

    public double confidenceHi() {   // high endpoint of 95% confidence interval
        return confidenceHi;
    }


    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(N, T);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }

}