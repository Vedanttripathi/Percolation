import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private double []a;
    private int t;
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("arguments cannot be less than or equal to zero");

        a = new double[trials];
        t = trials;
        int row = 0, col = 0;
        double threshold = 0;
        for (int i = 0; i < t; i++)
        {
           Percolation p = new Percolation(n);
            while (!p.percolates())
            {
                row = StdRandom.uniform(1,n + 1);
                col = StdRandom.uniform(1,n + 1);
                p.open(row, col);
            }
            threshold = (double) p.numberOfOpenSites()/(double) (n * n);
            a[i] = threshold;
        }
    }
    public double mean()
    {
        return StdStats.mean(a);
    }
    public double stddev()
    {
        return StdStats.stddev(a);
    }
    public double confidenceLo()
    {
        double x = 0;
        x = mean()-(1.96 * stddev())/Math.sqrt(t);
        return x;
    }
    public double confidenceHi()
    {
        double x = 0;
        x = mean()+(1.96 * stddev())/Math.sqrt(t);
        return x;
    }
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int t = StdIn.readInt();
        PercolationStats ps = new PercolationStats(n, t);
        System.out.println("% java-algs4 PercolationStats "+n+" "+t);
        System.out.println("Mean                    = "+ps.mean());
        System.out.println("Stddev                  = "+ps.stddev());
        System.out.println("95% confidence interval = ["+ps.confidenceLo()+", "+ps.confidenceHi()+"]");
    }
}
