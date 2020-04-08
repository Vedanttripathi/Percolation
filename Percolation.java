import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private int n;
    private int vt, vb, c;
    private WeightedQuickUnionUF ob;
    private WeightedQuickUnionUF obj;
    private int per[][];


    public Percolation(int n)
    {
        if (n <= 0)
            throw new IllegalArgumentException("arguments cannot be less than or equal to zero");
        this.n = n;
        vt = n*n;
        vb = n*n+1;
        c = 0;
        per = new int[n][n];
        ob = new WeightedQuickUnionUF(n*n+2);
        obj = new WeightedQuickUnionUF(n*n+1);
    }


    public boolean isOpen(int row, int col)
    {
        if (row < 0 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException("arguments are out of prescribed range");
        return per[row-1][col-1] == 1;
    }


    public void open(int row, int col) {
        if (row < 0 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException("arguments are out of prescribed range");

        int p = 0, q = 0, r = 0, s = 0, t = 0;
        if (!isOpen(row, col)) {
            if (row == 1) {
                p = xyTo1D(row, col);
                q = xyTo1D(row + 1, col);
                if (!ob.connected(p, vt)) {
                    ob.union(p, vt);
                    obj.union(p, vt);
                }
                if (isOpen(row + 1, col) && !ob.connected(p, q)) {
                    ob.union(p, q);
                    obj.union(p, q);
                }
            }

            else if (row == n) {
                p = xyTo1D(row, col);
                q = xyTo1D(row - 1, col);
                if (!ob.connected(p, vb)) {
                    ob.union(p, vb);
                }
                if (isOpen(row - 1, col) && !ob.connected(p, q)) {
                    ob.union(p, q);
                    obj.union(p, q);
                }
            }

            else if (row != 1 && row != n) {
                p = xyTo1D(row -1, col);
                q = xyTo1D(row, col - 1);
                r = xyTo1D(row, col + 1);
                s = xyTo1D(row + 1, col);
                t = xyTo1D(row, col);
                if (isOpen(row - 1, col) && !ob.connected(p, t)) {
                    ob.union(p, t);
                    obj.union(p, t);
                }
                if (col != 1) {
                    if (isOpen(row, col - 1) && !ob.connected(q, t)) {
                        ob.union(q, t);
                        obj.union(q, t);
                    }
                }
                if (col != n ) {
                    if (isOpen(row, col + 1) && !ob.connected(r, t)) {
                        ob.union(r, t);
                        obj.union(r, t);
                    }
                }
                if (isOpen(row + 1, col) && !ob.connected(s, t)) {
                    ob.union(s, t);
                    obj.union(s, t);
                }
            }
            per[row-1][col-1] = 1;
            c++;
        }

    }
    public boolean isFull(int row, int col)
    {
        if (row < 0 || row > n || col < 0 || col > n)
            throw new IllegalArgumentException("arguments are out of prescribed range");
        int p = xyTo1D(row, col);
        return obj.find(p) == obj.find(vt);
    }


    public int numberOfOpenSites()
    {
        return c;
    }


    public boolean percolates()
    {
        return ob.connected(vt, vb);
    }
    private int xyTo1D(int i, int j)
    {
        int row = i-1;
        int col = j-1;
        return (row * n + col);
    }

    public static void main(String[] args) { ; }

}
